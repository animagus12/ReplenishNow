document.addEventListener('DOMContentLoaded', function() {
    // assets/js/main.js

$(document).ready(function() {
    // Redirect to login.html
    window.location.href = '../pages/login.html';
});

    handleRouting();
});

function handleRouting() {
    const hash = window.location.hash || '#/login';
    loadScript('assets/js/login.js');
    if (hash === '#/product-catalog') {
        loadScript('assets/js/catalog.js');
    } else if (hash === '#/regsiter') {
        loadScript('assets/js/login.js');
    } else if (hash === '#/login') {
        loadScript('assets/js/login.js');
    }

}

function loadScript(src) {
    const script = document.createElement('script');
    script.src = src;
    script.onload = () => console.log(`${src} loaded successfully`);
    document.body.appendChild(script);
}
