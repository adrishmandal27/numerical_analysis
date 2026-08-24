let currentGraph = null;

function renderGraph(fStr, foundRoot) {
    // Destroy the old graph if it exists so they don't stack
    if (currentGraph) {
        currentGraph.remove();
    }
    
    // Clear the placeholder text
    document.getElementById('graph-container').innerHTML = '';

    let sketch = function(p) {
        let f = new Function('x', 'return ' + fStr);
        let container = document.getElementById('graph-container');
        
        // Define the scale (20 pixels = 1 unit on the graph)
        let scaleFactor = 20; 

        p.setup = function() {
            p.createCanvas(container.offsetWidth, container.offsetHeight);
        };
        
        p.draw = function() {
            p.background('#020617'); // Matches Tailwind slate-950
            p.translate(p.width / 2, p.height / 2); // Move origin (0,0) to center
            
            // 1. Draw Cartesian Axes
            p.stroke(255, 30);
            p.strokeWeight(1);
            p.line(-p.width / 2, 0, p.width / 2, 0); // X-axis
            p.line(0, -p.height / 2, 0, p.height / 2); // Y-axis
            
            // 2. Plot the Equation Curve
            p.stroke('#38bdf8'); // Tailwind sky-400
            p.strokeWeight(2);
            p.noFill();
            p.beginShape();
            for (let px = -p.width / 2; px < p.width / 2; px++) {
                let x = px / scaleFactor; // Convert pixel to math coordinate
                let y = f(x);
                let py = -y * scaleFactor; // Convert math coordinate back to pixel (invert Y)
                
                // Prevent drawing off-screen infinity lines
                if(py > -p.height && py < p.height) {
                    p.vertex(px, py);
                }
            }
            p.endShape();
            
            // 3. Highlight the Found Root
            if (foundRoot !== undefined && !isNaN(foundRoot)) {
                let rx = foundRoot * scaleFactor;
                
                // Draw the glowing dot
                p.fill('#34d399'); // Tailwind emerald-400
                p.noStroke();
                p.circle(rx, 0, 10);
                
                // Add the text label
                p.fill(255);
                p.textSize(12);
                p.textAlign(p.LEFT, p.BOTTOM);
                p.text(`Root: ${foundRoot.toFixed(4)}`, rx + 8, -5);
            }
            
            p.noLoop(); // Stop the loop since it's a static image
        };
        
        // Automatically resize the canvas if the user resizes their browser window
        p.windowResized = function() {
            if(container.offsetWidth > 0) {
                p.resizeCanvas(container.offsetWidth, container.offsetHeight);
                p.redraw();
            }
        };
    };
    
    currentGraph = new p5(sketch, 'graph-container');
}