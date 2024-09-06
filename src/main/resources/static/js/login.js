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
                // If the login is successful, redirect to the desired page
                window.location.href = '/DiamondShopSystem/src/main/resources/templates/User/about_us.html';
            } else {
                // Handle error cases (e.g., invalid token, server error)
                return response.text().then(text => { throw new Error(text) });
            }
        })
        .catch(error => {
            console.error("Failed to log in with Google:", error);
            // Optionally, show an error message to the user
            alert("Đăng nhập bằng Google thất bại. Vui lòng thử lại.");
        });
}
document.addEventListener('DOMContentLoaded', function () {

    const loginForm = document.getElementById('loginForm');
    loginForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const email = document.querySelector('#email').value;
        const password = document.querySelector('#password').value;

        // Thực hiện yêu cầu fetch
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
                localStorage.setItem('authToken', data.token);

                const typeUser = document.getElementById('type-user');
                if (typeUser.checked) {
                    if (typeUser.nextSibling.textContent.trim() === 'Admin') {
                        window.location.href = '/DiamondShopSystem/src/main/resources/templates/Admin/admin.html';
                    } else {
                        window.location.href = '/DiamondShopSystem/src/main/resources/templates/User/about_us.html';
                    }
                }
            })
            .catch(error => {
                console.error("Đăng nhập thất bại:", error);
                alert("Đăng nhập thất bại. Vui lòng thử lại.");
            });
    });
});

