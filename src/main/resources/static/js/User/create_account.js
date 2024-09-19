const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

function validateEmail() {
    const emailInput = document.getElementById('email');
    const errorMessage = document.querySelector('.error-message');

    if (!emailPattern.test(emailInput.value)) {
        errorMessage.textContent = "Vui lòng nhập địa chỉ email hợp lệ, ví dụ: diamond@shop.vn";
        errorMessage.style.display = 'block';
        return false;
    }

    errorMessage.style.display = 'none';
    return true;
}

function validateForm() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;
    const email = document.getElementById('email').value;
    const fullName = document.getElementById('fullname').value;
    const gender = document.getElementById('genderSelect').value;
    const address = document.getElementById('address').value;

    if (password === undefined || confirmPassword === undefined || email === undefined || fullName === undefined || gender === undefined) {
        alert("Vui lòng nhập đầy đủ thông tin");
        return false;
    }

    if (password.length < 8) {
        alert("Mật khẩu phải có ít nhất 8 ký tự.");
        return false;
    }

    if (!validateEmail()) {
        return false;
    }

    if (password !== confirmPassword) {
        alert("Mật khẩu không trùng khớp");
        return false;
    }

    return true;
}

function showMessage(message, isError = false) {
    const messageElement = document.getElementById('message');
    if (messageElement) {
        messageElement.textContent = message;
        messageElement.style.color = isError ? 'red' : 'green';
        messageElement.style.display = 'block';
    } else {
        console.warn('Element with id "message" not found. Message:', message);
        alert(message);
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const registerForm = document.getElementById('form-register');
    if (!registerForm) {
        console.error('Form with id "form-register" not found');
        return;
    }

    registerForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const email = document.getElementById('email').value;
        const fullName = document.getElementById('fullname').value;
        const gender = document.getElementById('genderSelect').value;
        const address = document.getElementById('address').value;
        const password = document.getElementById('password').value;
        const role = "Customer";

        // Kiểm tra form hợp lệ
        if (!validateForm()) {
            return;
        }

        // Gửi request đăng ký
        fetch(`${window.base_url}/user-management/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                userName: fullName,
                gender: gender,
                password: password,
                address: address,
                role: role
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Server response:', data);
                if (data.message && data.message.includes("successfully")) {
                    showMessage("Đăng ký thành công!");
                    setTimeout(() => {
                        window.location.href = '/DiamondShopSystem/src/main/resources/templates/login.html';
                    }, 2000);
                } else {
                    showMessage(data.message || "Đăng ký thất bại.", true);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showMessage("Đã xảy ra lỗi. Vui lòng thử lại sau.", true);
            });
    });
});