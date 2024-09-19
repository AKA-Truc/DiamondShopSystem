document.addEventListener('DOMContentLoaded', function() {
    const sendButton = document.querySelector('.send-button');
    if (sendButton) {
        sendButton.addEventListener('click', function() {
            const emailInput = document.getElementById('email').value;

            // Kiểm tra email hợp lệ
            if (!isValidEmail(emailInput)) {
                alert('Vui lòng nhập địa chỉ email hợp lệ.');
                return;
            }

            // Gửi yêu cầu OTP
            fetch(`${window.base_url}/forgot-password/send-otp?email=${emailInput}`, {
                method: 'POST',
            })
                .then(response => response.text())
                .then(data => {
                    alert(data);
                    window.location.href = `/DiamondShopSystem/src/main/resources/templates/User/verification_code.html?email=${encodeURIComponent(emailInput)}`;
                })
                .catch(error => {
                    console.error('Lỗi khi gửi OTP:', error);
                    alert('Đã xảy ra lỗi khi gửi OTP.');
                });
        });
    }

    // Hàm kiểm tra email hợp lệ
    function isValidEmail(email) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    }
});
