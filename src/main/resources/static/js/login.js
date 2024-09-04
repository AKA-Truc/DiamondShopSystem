function handleCredentialResponse(response) {
    fetch('http://localhost:8080/user-management/GoogleLogin', {
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
        fetch('http://localhost:8080/user-management/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email, password: password })
        })
            .then(response => {
                if (response.ok) {
                    // Xử lý khi đăng nhập thành công
                    window.location.href = '/DiamondShopSystem/src/main/resources/templates/User/about_us.html';
                } else {
                    return response.text().then(text => { throw new Error(text) });
                }
            })
            .catch(error => {
                console.error("Đăng nhập thất bại:", error);
                alert("Đăng nhập thất bại. Vui lòng thử lại.");
            });
    });
});
