function validateEmail() {
    const emailInput = document.getElementById('email');
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const errorMessage = document.querySelector('.error-message');

    if (!emailPattern.test(emailInput.value)) {
        errorMessage.textContent = "Vui lòng nhập địa chỉ email hợp lệ, ví dụ: diamond@shop.vn";
        errorMessage.style.display = 'block';
        return false;
    }

    errorMessage.style.display = 'none';
    return true;
}

document.querySelector('form').addEventListener('submit', function(event) {
    if (!validateForm()) {
        event.preventDefault();
    }
});