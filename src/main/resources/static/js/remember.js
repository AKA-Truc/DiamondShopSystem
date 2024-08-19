document.addEventListener('DOMContentLoaded', function() {
    const closeButton = document.querySelector('.close-button');
    closeButton.addEventListener('click', function() {
        window.location.href = '../login/login1.html';
    });

    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const emailInput = document.getElementById('email').value;

        // Kiểm tra định dạng email
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailPattern.test(emailInput)) {
            alert("Chưa đúng định dạng, ví dụ: diamond@shop.vn");
        } else {
            window.location.href = 'maxacminh.html';
        }
    });
});
