document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('unlock_user').addEventListener('click', function (event) {
        event.preventDefault();

        const Capnhat = document.getElementById('Update_user');
        const Chinhsua = document.getElementById('unlock_user');

        Capnhat.style.display = 'flex';
        Chinhsua.style.display = 'none';

        document.getElementById('last-name').disabled = false;
        document.getElementById('address').disabled = false;
        document.getElementById('email1').disabled = false;
        document.getElementById('first-name').disabled = false;
        document.getElementById('phone').disabled = false;
    });
});