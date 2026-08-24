const CACHE_NAME = 'num-engine-v3';
const ASSETS_TO_CACHE = [
  './',
  './index.html',
  './style.css',
  './engine.js',
  './grapher.js',
  './A.png'
];

// Install Event: Cache essential files
self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      return cache.addAll(ASSETS_TO_CACHE);
    })
  );
  self.skipWaiting(); // Force the waiting service worker to become the active service worker
});

// Activate Event: Clean up old caches
self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames.map((cache) => {
          if (cache !== CACHE_NAME) {
            return caches.delete(cache);
          }
        })
      );
    })
  );
  self.clients.claim();
});

// Fetch Event: THIS IS MANDATORY FOR CHROME INSTALLABILITY
self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request).then((response) => {
      // Return the cached version if found, otherwise fetch from the network
      return response || fetch(event.request);
    }).catch(() => {
      // Fallback if both cache and network fail
      return caches.match('./index.html');
    })
  );
});