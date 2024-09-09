document.getElementById('submit-button').addEventListener('click', function(event) {
    var newPassword = document.getElementById('new-password').value;
    var confirmPassword = document.getElementById('confirm-password').value;

    if (newPassword === confirmPassword) {
        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get('email');

        if (!email) {
            alert('Email không hợp lệ.');
            return;
        }

        const forgotPasswordDTO = {
            password: newPassword,
            retypePassword: confirmPassword
        };

        fetch(`${window.base_url}/forgot-password/forgot_password/${email}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(forgotPasswordDTO)
        })
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    return response.text().then(text => Promise.reject(text));
                }
            })
            .then(data => {
                alert(data);
                document.getElementById('success-message').style.display = 'block';
                document.getElementById('login-button').style.display = 'block';
                document.querySelector('form').style.display = 'none';
            })
            .catch(error => {
                console.error('Lỗi khi đặt lại mật khẩu:', error);
                alert(error);
            });
    } else {
        alert('Mật khẩu mới và xác nhận mật khẩu phải giống nhau.');
    }
});
