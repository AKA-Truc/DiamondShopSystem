
async function login() {
    const phone = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;

    if (!phone || !password) {
        showMessage('Vui lòng nhập số điện thoại và mật khẩu.', 'error');
        return;
    }

    const formData = {
        Phone: phone,
        Password: password,
        Role: role
    };

    try {
        const response = await fetch('http://localhost:8080/api/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        const data = await response.json();
        console.log(data);

        if (!response.ok) {
            throw new Error(data.message || 'Đăng nhập không thành công');
        }

        // Lưu vào sessionStorage
        sessionStorage.setItem('accessToken', data.accessToken);

        // Hiển thị thông báo thành công
        showMessage('Đăng nhập thành công!', 'success');

        // Chuyển hướng người dùng đến trang menu.html sau 1 giây
        setTimeout(() => {
            window.location.href = '../menu/menu.html';
        }, 1000); // 1000 milliseconds = 1 second
    } catch (error) {
        console.error(error.message);
        showMessage(error.message, 'error');
    }
}

function showMessage(message, type) {
    const popupOverlay = document.getElementById('popup');
    const messageContent = document.getElementById('message-content');

    messageContent.textContent = message;
    popupOverlay.style.display = 'flex'; // Hiển thị popup
}

function closeMessage() {
    const popupOverlay = document.getElementById('popup');
    popupOverlay.style.display = 'none'; // Ẩn popup khi ấn OK
}

document.getElementById('login-button').addEventListener('click', login);
