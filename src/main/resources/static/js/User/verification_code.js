document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('verification-form');
    const resendText = document.getElementById('resend-text');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get('email');
        const verificationCode = document.getElementById('code').value;

        console.log(email);
        if (!email) {
            alert('Email not found.');
            return;
        }

        // Gửi mã xác minh qua API
        fetch(`${window.base_url}/forgot-password/verify-otp?email=${email}&otp=${verificationCode}`, {
            method: 'POST'
        })
            .then(response => {
                if (response.status === 401) {
                    throw new Error('Unauthorized');
                }
                return response.text();
            })
            .then(data => {
                alert(data);
                if (data.includes("OTP verified successfully")) {
                    window.location.href = `/DiamondShopSystem/src/main/resources/templates/User/resetpass.html?email=${encodeURIComponent(email)}`;
                }
            })
            .catch(error => {
                console.error('Error verifying code:', error);
                alert('An error occurred while verifying the code.');
            });
    });

    let resendCountdown = 30;
    const interval = setInterval(() => {
        resendCountdown--;
        resendText.textContent = `Resend code (Available after ${resendCountdown} s)`;

        if (resendCountdown <= 0) {
            clearInterval(interval);
            resendText.innerHTML = '<a href="#" id="resend-link">Resend code</a>';

            const urlParams = new URLSearchParams(window.location.search);
            const email = urlParams.get('email');

            document.getElementById('resend-link').addEventListener('click', function() {
                // Gửi yêu cầu resend mã
                fetch(`${window.base_url}/forgot-password/send-otp?email=${encodeURIComponent(email)}`, {
                    method: 'POST'
                })
                    .then(response => response.text())
                    .then(data => {
                        alert('New verification code has been sent!');
                        resendCountdown = 30; // Reset lại đếm ngược
                    })
                    .catch(error => {
                        console.error('Error verifying code', error);
                        alert('An error occurred while verifying the code.');
                    });
            });
        }
    }, 1000);
});
