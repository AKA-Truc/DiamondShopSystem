//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');
//
//     if (!accessToken) {
//         window.location.href = '../Login/login1.html';
//     }
// });

document.addEventListener('DOMContentLoaded', function() {
    const accessToken = sessionStorage.getItem('accessToken');

    if (!accessToken) {
        alert('Tính năng đang được phát triển');
        window.location.href = 'login1.html';
    }
});

