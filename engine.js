// Menu Tab Switching
function switchTab(tabId) {
    document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('tab-active'));
    document.getElementById(tabId).classList.add('tab-active');
    
    // Save the active tab to the browser's local storage
    localStorage.setItem('activeNumericalTab', tabId);
    
    // Automatically close sidepanel when a tool is selected (if you have a closeNav function)
    if (typeof closeNav === 'function') closeNav(); 
}

// Restore the active tab when the page reloads
document.addEventListener('DOMContentLoaded', () => {
    // Check memory for a saved tab. If memory is empty, default to 'home'
    const savedTab = localStorage.getItem('activeNumericalTab') || 'home';
    
    // Check if the element actually exists before trying to switch to it
    if (document.getElementById(savedTab)) {
        document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('tab-active'));
        document.getElementById(savedTab).classList.add('tab-active');
    }
});

// 1. ROOT FINDING
function runRootFinding() {
    const method = document.getElementById('rootMethod').value;
    const fStr = document.getElementById('rootFunc').value;
    let a = parseFloat(document.getElementById('rootA').value);
    let b = parseFloat(document.getElementById('rootB').value);
    const resultBox = document.getElementById('rootResultBox');
    const table = document.getElementById('rootTable');

    try {
        // 🛡️ SECURITY PATCH: Prevent Code Injection (XSS)
        const safeRegex = /^([0-9x\+\-\*\/\(\)\.,]|Math\.(sin|cos|tan|pow|sqrt|exp|log|abs|PI|E))+$/;
        if (!safeRegex.test(fStr.replace(/\s+/g, ''))) {
            throw new Error("Security Alert: XSS attempt blocked. Only mathematical functions are allowed.");
        }

        const f = new Function('x', 'return ' + fStr);
        table.innerHTML = `<tr class="bg-slate-900/80"><th class="px-3 py-2 text-white">Iter</th><th class="px-3 py-2 text-white">x_k</th><th class="px-3 py-2 text-white">f(x_k)</th><th class="px-3 py-2 text-white">Error</th></tr>`;
        
        let c = a, iter = 0, maxIter = 50, tol = 1e-6;
        if (method === 'bisection' || method === 'falsi') {
            if (f(a) * f(b) >= 0) throw new Error("f(a) and f(b) must have opposite signs.");
            for (iter = 0; iter < maxIter; iter++) {
                c = (method === 'bisection') ? (a + b) / 2 : (a * f(b) - b * f(a)) / (f(b) - f(a));
                let fc = f(c), err = Math.abs(b - a);
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">${iter+1}</td><td class="px-3 py-2 font-bold text-amber-300">${c.toFixed(6)}</td><td class="px-3 py-2">${fc.toExponential(3)}</td><td class="px-3 py-2">${err.toExponential(3)}</td></tr>`;
                if (Math.abs(fc) < tol || err < tol) break;
                if (f(a) * fc < 0) b = c; else a = c;
            }
        } else if (method === 'newton') {
            c = a;
            for (iter = 0; iter < maxIter; iter++) {
                let fc = f(c), dfc = (f(c + 1e-5) - f(c - 1e-5)) / 2e-5; 
                if (Math.abs(dfc) < 1e-12) throw new Error("Derivative approached zero. Division by zero prevented.");
                let nextC = c - fc / dfc, err = Math.abs(nextC - c); c = nextC;
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">${iter+1}</td><td class="px-3 py-2 font-bold text-amber-300">${c.toFixed(6)}</td><td class="px-3 py-2">${f(c).toExponential(3)}</td><td class="px-3 py-2">${err.toExponential(3)}</td></tr>`;
                if (err < tol) break;
            }
        } else if (method === 'secant') {
            let x0 = a, x1 = b;
            for (iter = 0; iter < maxIter; iter++) {
                let fx0 = f(x0), fx1 = f(x1);
                if (Math.abs(fx1 - fx0) < 1e-12) throw new Error("Denominator approached zero. Method fails.");
                c = x1 - fx1 * (x1 - x0) / (fx1 - fx0);
                let err = Math.abs(c - x1);
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">${iter+1}</td><td class="px-3 py-2 font-bold text-amber-300">${c.toFixed(6)}</td><td class="px-3 py-2">${f(c).toExponential(3)}</td><td class="px-3 py-2">${err.toExponential(3)}</td></tr>`;
                if (err < tol) break;
                x0 = x1; x1 = c;
            }
        }
        
        resultBox.innerHTML = `<span class="text-emerald-400">Root Found:</span> ${c.toFixed(6)} <span class="text-slate-500 text-xs ml-2">(${iter+1} iterations)</span>`;
        
        // 📥 TRIGGER THE P5.JS GRAPH HERE 
        if (typeof renderGraph === 'function') renderGraph(fStr, c);
        
    } catch (e) { 
        resultBox.innerHTML = `<span class="text-rose-500 font-bold">🚨 [Error] ${e.message}</span>`; 
    }
}

// 2. INTERPOLATION
function runInterpolation() {
    const method = document.getElementById('interpMethod').value;
    const x = document.getElementById('interpX').value.split(',').map(n => parseFloat(n.trim()));
    const y = document.getElementById('interpY').value.split(',').map(n => parseFloat(n.trim()));
    const target = parseFloat(document.getElementById('interpTarget').value);
    const resultBox = document.getElementById('interpResultBox');
    const table = document.getElementById('interpTable');

    // Custom Algebraic Expander: Multiplies out (x-r1)(x-r2)... and groups like terms
    function getExpandedPolynomial(xArr, yArr) {
        let n = xArr.length;
        let finalPoly = new Array(n).fill(0);
        
        for (let i = 0; i < n; i++) {
            let term = [1];
            let denom = 1;
            for (let j = 0; j < n; j++) {
                if (i !== j) {
                    let root = xArr[j];
                    let newTerm = new Array(term.length + 1).fill(0);
                    for (let k = 0; k < term.length; k++) {
                        newTerm[k] -= root * term[k];
                        newTerm[k+1] += term[k];
                    }
                    term = newTerm;
                    denom *= (xArr[i] - xArr[j]);
                }
            }
            let scalar = yArr[i] / denom;
            for (let k = 0; k < term.length; k++) {
                finalPoly[k] += term[k] * scalar; 
            }
        }

        let eqStr = "";
        for (let k = n - 1; k >= 0; k--) {
            let coef = finalPoly[k];
            if (Math.abs(coef) < 1e-7) continue; 
            
            let absCoef = Math.abs(coef).toFixed(4);
            let termStr = "";
            
            if (k > 1) termStr = "x^" + k;
            else if (k === 1) termStr = "x";
            
            if (absCoef !== "1.0000" || k === 0) {
                termStr = absCoef + termStr;
            }

            if (eqStr === "") {
                eqStr += (coef < 0 ? "-" : "") + termStr;
            } else {
                eqStr += (coef < 0 ? " - " : " + ") + termStr;
            }
        }
        return eqStr === "" ? "0.0000" : eqStr;
    }

    try {
        if (x.length !== y.length) throw new Error("X and Y arrays must match in length.");
        if (new Set(x).size !== x.length) throw new Error("Duplicate X values detected. Division by zero prevented.");
        
        let result = 0, n = x.length;
        
        table.innerHTML = `<tr class="bg-slate-900/80"><th class="px-3 py-2 text-white">Step</th><th class="px-3 py-2 text-white">Detail</th><th class="px-3 py-2 text-white">Accumulated Value</th></tr>`;
        
        if (method === 'lagrange') {
            for (let i = 0; i < n; i++) {
                let l_i = 1;
                for (let j = 0; j < n; j++) if (i !== j) l_i *= (target - x[j]) / (x[i] - x[j]);
                result += l_i * y[i];
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">Term ${i}</td><td class="px-3 py-2 text-slate-400">L_${i} = ${l_i.toFixed(4)}</td><td class="px-3 py-2 text-sky-300">${result.toFixed(5)}</td></tr>`;
            }
        } else if (method === 'forward' || method === 'backward') {
            let diff = Array.from({length: n}, () => Array(n).fill(0));
            for(let i=0; i<n; i++) diff[i][0] = y[i];
            
            if (method === 'forward') {
                for(let j=1; j<n; j++) for(let i=0; i<n-j; i++) diff[i][j] = diff[i+1][j-1] - diff[i][j-1];
                let u = (target - x[0]) / (x[1] - x[0]);
                result = diff[0][0]; let uTerm = 1, fact = 1;
                for(let i=1; i<n; i++) { 
                    uTerm *= (u - (i - 1)); fact *= i; result += (uTerm * diff[0][i]) / fact; 
                    table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">Δ^${i}</td><td class="px-3 py-2 text-slate-400">Val: ${diff[0][i].toFixed(4)}</td><td class="px-3 py-2 text-sky-300">${result.toFixed(5)}</td></tr>`; 
                }
            } else {
                for(let j=1; j<n; j++) for(let i=n-1; i>=j; i--) diff[i][j] = diff[i][j-1] - diff[i-1][j-1];
                let v = (target - x[n-1]) / (x[1] - x[0]);
                result = diff[n-1][0]; let vTerm = 1, fact = 1;
                for(let i=1; i<n; i++) { 
                    vTerm *= (v + (i - 1)); fact *= i; result += (vTerm * diff[n-1][i]) / fact; 
                    table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">∇^${i}</td><td class="px-3 py-2 text-slate-400">Val: ${diff[n-1][i].toFixed(4)}</td><td class="px-3 py-2 text-sky-300">${result.toFixed(5)}</td></tr>`; 
                }
            }
        } else if (method === 'divided') {
            let divDiff = Array.from({length: n}, () => Array(n).fill(0));
            for(let i=0; i<n; i++) divDiff[i][0] = y[i];
            for(let j=1; j<n; j++) for(let i=0; i<n-j; i++) divDiff[i][j] = (divDiff[i+1][j-1] - divDiff[i][j-1]) / (x[i+j] - x[i]);
            
            result = divDiff[0][0]; let xTerm = 1;
            for(let i=1; i<n; i++) { 
                xTerm *= (target - x[i-1]); result += xTerm * divDiff[0][i]; 
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">Order ${i}</td><td class="px-3 py-2 text-slate-400">Diff: ${divDiff[0][i].toFixed(4)}</td><td class="px-3 py-2 text-sky-300">${result.toFixed(5)}</td></tr>`; 
            }
        }
        
        let standardEquation = getExpandedPolynomial(x, y);
        
        resultBox.innerHTML = `
            <div class="mb-2"><span class="text-amber-400">Interpolated f(${target}):</span> <span class="font-bold text-white">${result.toFixed(6)}</span></div>
            <div class="text-xs text-slate-300 break-words mt-2 border-t border-slate-800 pt-3 font-mono leading-relaxed">
                <span class="text-sky-400 uppercase font-bold tracking-wider">Polynomial Constructed:</span><br> 
                F(x) ≈ ${standardEquation}
            </div>
        `;
    } catch (e) { 
        resultBox.innerHTML = `<span class="text-rose-500 font-bold">🚨 [Error] ${e.message}</span>`; 
    }
}

// 3. DIFFERENTIATION
function runDifferentiation() {
    const resultBox = document.getElementById('diffResultBox'), diffTable = document.getElementById('diffTable');
    try {
        const x = document.getElementById('diffX').value.split(',').map(n => parseFloat(n.trim()));
        const y = document.getElementById('diffY').value.split(',').map(n => parseFloat(n.trim()));
        const pos = parseInt(document.getElementById('diffIndex').value), n = x.length;
        
        if (n < 2) throw new Error("At least 2 points are required.");
        if (pos < 0 || pos >= n) throw new Error(`Invalid index. Must be between 0 and ${n - 1}.`);
        
        let h = x[1] - x[0];
        if (Math.abs(h) < 1e-12) throw new Error("X coordinates must have a non-zero gap.");

        let firstDeriv = 0.0, secondDeriv = 0.0;
        let diffTab = Array.from({length: n}, () => Array(n).fill(0)), isForward = pos <= n / 2;

        if (isForward) {
            for (let i = 0; i < n; i++) diffTab[i][0] = y[i];
            for (let j = 1; j < n; j++) for (let i = 0; i < n - j; i++) diffTab[i][j] = diffTab[i + 1][j - 1] - diffTab[i][j - 1];
            for (let j = 1; j < n - pos; j++) firstDeriv += Math.pow(-1, j - 1) * diffTab[pos][j] / j;
            for (let j = 2; j < n - pos; j++) { let t = 0.0; for (let i = 1; i < j; i++) t += 1.0 / i; secondDeriv += Math.pow(-1, j) * diffTab[pos][j] * (2.0 / j * t); }
        } else {
            for (let i = 0; i < n; i++) diffTab[i][0] = y[i];
            for (let j = 1; j < n; j++) for (let i = n - 1; i >= j; i--) diffTab[i][j] = diffTab[i][j - 1] - diffTab[i - 1][j - 1];
            for (let j = 1; j <= pos; j++) firstDeriv += diffTab[pos][j] / j;
            for (let j = 2; j <= pos; j++) { let t = 0.0; for (let i = 1; i < j; i++) t += 1.0 / i; secondDeriv += diffTab[pos][j] * (2.0 / j * t); }
        }
        firstDeriv /= h; secondDeriv /= (h * h);

        resultBox.innerHTML = `
            <div class="text-rose-400 mb-2 font-bold">Target X: ${x[pos]} (Index: ${pos})</div>
            <div class="mb-1"><span class="text-emerald-400">First Derivative (f'):</span> ${firstDeriv.toFixed(6)}</div>
            <div><span class="text-amber-400">Second Derivative (f''):</span> ${secondDeriv.toFixed(6)}</div>
        `;
        let tHTML = `<thead><tr class="border-b border-slate-800 bg-slate-900/80"><th class="px-3 py-2 text-sky-400">X</th><th class="px-3 py-2 text-amber-400">Y</th>`;
        for (let i = 1; i < n; i++) tHTML += `<th class="px-3 py-2 text-white">${isForward ? 'Δ' : '∇'}^${i}</th>`;
        tHTML += `</tr></thead><tbody class="divide-y divide-slate-800/50">`;
        for (let i = 0; i < n; i++) {
            tHTML += `<tr class="hover:bg-slate-800/40"><td class="px-3 py-2">${x[i]}</td><td class="px-3 py-2 font-semibold text-slate-300">${y[i]}</td>`;
            for (let j = 1; j < n; j++) tHTML += `<td class="px-3 py-2 text-slate-400">${(isForward ? i < n - j : i >= j) ? diffTab[i][j].toFixed(4) : ''}</td>`;
            tHTML += `</tr>`;
        }
        diffTable.innerHTML = tHTML + `</tbody>`;
    } catch (e) { resultBox.innerHTML = `<span class="text-rose-500 font-bold">🚨 [Error] ${e.message}</span>`; }
}

// 4. INTEGRATION
function runIntegration() {
    const method = document.getElementById('integMethod').value;
    const fStr = document.getElementById('integFunc').value;
    const a = parseFloat(document.getElementById('integA').value);
    const b = parseFloat(document.getElementById('integB').value);
    const n = parseInt(document.getElementById('integN').value);
    const resultBox = document.getElementById('integResultBox');
    const table = document.getElementById('integTable');

    try {
        // 🛡️ SECURITY PATCH: Prevent Code Injection (XSS)
        const safeRegex = /^([0-9x\+\-\*\/\(\)\.,]|Math\.(sin|cos|tan|pow|sqrt|exp|log|abs|PI|E))+$/;
        if (!safeRegex.test(fStr.replace(/\s+/g, ''))) {
            throw new Error("Security Alert: XSS attempt blocked. Only mathematical functions are allowed.");
        }
        if (n <= 0 || isNaN(n)) throw new Error("Subintervals (n) must be a positive integer.");

        const f = new Function('x', 'return ' + fStr);
        table.innerHTML = `<tr class="bg-slate-900/80"><th class="px-3 py-2 text-white">i</th><th class="px-3 py-2 text-white">x_i</th><th class="px-3 py-2 text-white">f(x_i)</th><th class="px-3 py-2 text-white">Weight</th></tr>`;

        let h = (b - a) / n;
        let result = 0;

        if (method === 'trapezoidal') {
            for (let i = 0; i <= n; i++) {
                let xi = a + i * h;
                let fi = f(xi);
                let weight = (i === 0 || i === n) ? 1 : 2;
                result += weight * fi;
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">${i}</td><td class="px-3 py-2 text-amber-300">${xi.toFixed(4)}</td><td class="px-3 py-2">${fi.toFixed(6)}</td><td class="px-3 py-2">× ${weight}</td></tr>`;
            }
            result *= (h / 2);
            
        } else if (method === 'simpson13') {
            if (n % 2 !== 0) throw new Error("Simpson's 1/3 rule requires an EVEN number of intervals (n).");
            for (let i = 0; i <= n; i++) {
                let xi = a + i * h;
                let fi = f(xi);
                let weight = (i === 0 || i === n) ? 1 : (i % 2 === 0 ? 2 : 4);
                result += weight * fi;
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">${i}</td><td class="px-3 py-2 text-amber-300">${xi.toFixed(4)}</td><td class="px-3 py-2">${fi.toFixed(6)}</td><td class="px-3 py-2">× ${weight}</td></tr>`;
            }
            result *= (h / 3);
            
        } else if (method === 'simpson38') {
            if (n % 3 !== 0) throw new Error("Simpson's 3/8 rule requires 'n' to be a multiple of 3.");
            for (let i = 0; i <= n; i++) {
                let xi = a + i * h;
                let fi = f(xi);
                let weight = (i === 0 || i === n) ? 1 : (i % 3 === 0 ? 2 : 3);
                result += weight * fi;
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">${i}</td><td class="px-3 py-2 text-amber-300">${xi.toFixed(4)}</td><td class="px-3 py-2">${fi.toFixed(6)}</td><td class="px-3 py-2">× ${weight}</td></tr>`;
            }
            result *= (3 * h / 8);
            
        } else if (method === 'gaussleg') {
            table.innerHTML += `<tr class="border-b border-slate-800"><td colspan="4" class="px-3 py-2 text-slate-400 text-center">Using 2-Point Gauss-Legendre Quadrature</td></tr>`;
            let c1 = -1 / Math.sqrt(3), c2 = 1 / Math.sqrt(3);
            
            for (let i = 0; i < n; i++) {
                let subA = a + i * h, subB = subA + h;
                let mid = (subA + subB) / 2, diff = (subB - subA) / 2;
                
                let val1 = f(mid + diff * c1), val2 = f(mid + diff * c2); 
                result += diff * (val1 + val2);
                
                table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">Int ${i+1}</td><td class="px-3 py-2 text-amber-300">Gauss Pts</td><td class="px-3 py-2 text-purple-300">${val1.toFixed(3)}, ${val2.toFixed(3)}</td><td class="px-3 py-2">w = 1</td></tr>`;
            }
        }

        resultBox.innerHTML = `
            <div class="mb-2"><span class="text-purple-400 uppercase font-bold tracking-wider">Definite Integral:</span></div>
            <div class="font-mono text-xl font-bold text-white">≈ ${result.toFixed(6)}</div>
        `;
        
    } catch (e) {
        resultBox.innerHTML = `<span class="text-rose-500 font-bold">🚨 [Error] ${e.message}</span>`;
    }
}

// 5. MATRIX
function runMatrix() {
    const method = document.getElementById('matrixMethod').value, rawText = document.getElementById('matrixInput').value.trim().split('\n');
    const resultBox = document.getElementById('matrixResultBox'), table = document.getElementById('matrixTable');
    try {
        let A = [], B = [];
        rawText.forEach(row => { if(row.trim() === '') return; let cols = row.split(',').map(n => parseFloat(n.trim())); B.push(cols.pop()); A.push(cols); });
        let len = B.length, X = new Array(len).fill(0);
        
        // Check for zeros on the main diagonal
        for (let i = 0; i < len; i++) {
            if (Math.abs(A[i][i]) < 1e-12) throw new Error(`Zero detected on main diagonal at row ${i+1}. Rearrange your matrix.`);
        }

        table.innerHTML = `<tr class="bg-slate-900/80"><th class="px-3 py-2 text-white">Iter</th><th class="px-3 py-2 text-white">Vector X</th><th class="px-3 py-2 text-white">Max Error</th></tr>`;
        
        for (let k = 0; k < 25; k++) {
            let maxErr = 0, oldX = [...X]; 
            for (let i = 0; i < len; i++) {
                let sum = B[i];
                for (let j = 0; j < len; j++) if (i !== j) sum -= A[i][j] * ((method === 'gauss_jacobi') ? oldX[j] : X[j]);
                X[i] = sum / A[i][i]; maxErr = Math.max(maxErr, Math.abs(X[i] - oldX[i]));
            }
            table.innerHTML += `<tr class="border-b border-slate-800 hover:bg-slate-800/40"><td class="px-3 py-2">${k+1}</td><td class="px-3 py-2 font-bold text-emerald-300">[${X.map(v => v.toFixed(4)).join(', ')}]</td><td class="px-3 py-2">${maxErr.toExponential(3)}</td></tr>`;
            if (maxErr < 1e-5) break;
        }
        resultBox.innerHTML = `<span class="text-emerald-400">Solution Vector X:</span> [ ${X.map(v => v.toFixed(5)).join(',  ')} ]`;
    } catch (e) { resultBox.innerHTML = `<span class="text-rose-500 font-bold">🚨 [Error] ${e.message}</span>`; }
}


function exportTableToCSV(tableId, filename) {
    let csv = [];
    let table = document.getElementById(tableId); 
    
    // Check if table exists and has rows inside it
    if (!table || table.querySelectorAll("tr").length === 0) {
        alert("No iteration data found to export! Please execute an algorithm first.");
        return;
    }

    let rows = table.querySelectorAll("tr");
    
    for (let i = 0; i < rows.length; i++) {
        let row = [], cols = rows[i].querySelectorAll("td, th");
        
        for (let j = 0; j < cols.length; j++) {
            let cellData = cols[j].innerText.replace(/"/g, '""');
            row.push('"' + cellData + '"');
        }
        csv.push(row.join(",")); 
    }

    downloadCSV(csv.join("\n"), filename);
}

function downloadCSV(csv, filename) {
    let csvFile = new Blob([csv], {type: "text/csv"});
    let downloadLink = document.createElement("a");
    downloadLink.download = filename;
    downloadLink.href = window.URL.createObjectURL(csvFile);
    downloadLink.style.display = "none";
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}
// Force hard refresh: Unregister Service Worker, clear cache, and reload
function hardRefreshApp() {
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.getRegistrations().then((registrations) => {
            for (let registration of registrations) {
                registration.unregister();
            }
        });
    }
    
    if ('caches' in window) {
        caches.keys().then((names) => {
            for (let name of names) {
                caches.delete(name);
            }
        });
    }

    setTimeout(() => {
        window.location.reload(true);
    }, 500);
}