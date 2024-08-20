document.addEventListener('DOMContentLoaded', function() {
    const closeButton = document.querySelector('.close-button');
    const resendText = document.getElementById('resend-text');
    const submitButton = document.getElementById('submit-button');

    closeButton.addEventListener('click', function() {
        window.location.href = '../login/quenmk.html';
    });

    function startCountdown() {
        let timeLeft = 30;

        const countdown = setInterval(() => {
            if (timeLeft <= 0) {
                clearInterval(countdown);
                resendText.innerHTML = '<span id="resend-link" style="cursor: pointer; color: #007bff;">Gửi lại mã</span>';
                const resendLink = document.getElementById('resend-link');
                resendLink.addEventListener('click', function() {
                    startCountdown(); //đếm ngược khi nhấn Gửi lại mã
                });
            } else {
                resendText.innerHTML = `<span>Gửi lại mã (có sau ${timeLeft--} giây)</span>`;
            }
        }, 1000);
    }

    // đếm ngược khi f5
    startCountdown();
});
