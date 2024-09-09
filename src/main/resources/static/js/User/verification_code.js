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
            alert('Không tìm thấy email.');
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
                console.error('Lỗi khi xác minh mã:', error);
                alert('Đã xảy ra lỗi khi xác minh mã.');
            });
    });

    let resendCountdown = 30;
    const interval = setInterval(() => {
        resendCountdown--;
        resendText.textContent = `Gửi lại mã (có sau ${resendCountdown} giây)`;

        if (resendCountdown <= 0) {
            clearInterval(interval);
            resendText.innerHTML = '<a href="#" id="resend-link">Gửi lại mã</a>';

            const urlParams = new URLSearchParams(window.location.search);
            const email = urlParams.get('email');

            document.getElementById('resend-link').addEventListener('click', function() {
                // Gửi yêu cầu resend mã
                fetch(`${window.base_url}/forgot-password/send-otp?email=${encodeURIComponent(email)}`, {
                    method: 'POST'
                })
                    .then(response => response.text())
                    .then(data => {
                        alert('Mã xác minh mới đã được gửi!');
                        resendCountdown = 30; // Reset lại đếm ngược
                    })
                    .catch(error => {
                        console.error('Lỗi khi gửi lại mã:', error);
                        alert('Đã xảy ra lỗi khi gửi lại mã.');
                    });
            });
        }
    }, 1000);
});
