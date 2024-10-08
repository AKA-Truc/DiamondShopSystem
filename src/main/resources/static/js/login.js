function handleCredentialResponse(response) {
    fetch(`${window.base_url}/user-management/GoogleLogin`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ token: response.credential })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.text().then(text => { throw new Error(text) });
            }
        })
        .then(data => {
            // Store token in localStorage
            localStorage.setItem('authToken', data.token);

            checkRole();
        })
        .catch(error => {
            console.error("Failed to log in with Google:", error);
            // Optionally, show an error message to the user
            alert("Đăng nhập bằng Google thất bại. Vui lòng thử lại.");
        });
}
document.addEventListener('DOMContentLoaded', function () {

    if (localStorage.getItem('authToken')) {
        checkRole();
    }

    const loginForm = document.getElementById('loginForm');
    loginForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const email = document.querySelector('#email').value;
        const password = document.querySelector('#password').value;

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert("Vui lòng nhập đúng định dạng email.");
            return;
        }

        // Thực hiện yêu cầu fetch nếu email và mật khẩu hợp lệ
        fetch(`${window.base_url}/user-management/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email, password: password })
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .then(data => {
                // Lưu token vào localStorage
                localStorage.setItem('authToken', data.token);

                // Decode JWT to get roles
                checkRole();
            })
            .catch(error => {
                console.error("Đăng nhập thất bại:", error);
                alert("Đăng nhập thất bại. Vui lòng thử lại.");
            });
    });

});

function checkRole(){
    // Decode JWT to get roles
    const token = localStorage.getItem('authToken');
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));  // Decode the payload part of the token
    const roles = tokenPayload.roles; // Assuming the role array is in the 'roles' field
    const role = roles[0];  // Assuming the first role is the relevant one
    console.log(role);

    if (role === 'ROLE_CUSTOMER') {
        window.location.href = '/DiamondShopSystem/src/main/resources/templates/User/about_us.html';
    } else {
        window.location.href = '/DiamondShopSystem/src/main/resources/templates/Admin/admin.html';
    }
}
