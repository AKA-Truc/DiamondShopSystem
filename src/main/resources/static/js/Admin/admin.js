document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('unlock_user').addEventListener('click', function(event) {
        event.preventDefault();

        const updateButton = document.getElementById('Update_user');
        const editButton = document.getElementById('unlock_user');

        updateButton.style.display = 'inline-block';
        editButton.style.display = 'none';

        // Bỏ khóa các trường để chỉnh sửa
        document.getElementById('name').disabled = false;
        document.getElementById('address').disabled = false;
        document.getElementById('email1').disabled = false;
        document.getElementById('genderSelect').disabled = false;
    });

    fetchUser();
});

function fetchUser() {
    const token = localStorage.getItem('authToken');
    const user = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

    if (!token) {
        console.error('Auth token not found');
        return;
    }

    fetch(`${window.base_url}/user-management/users/${user.userId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;
            const nameElement = document.getElementById('name');
            const userNameElement = document.getElementById('user-name');
            const addressElement = document.getElementById('address');
            const emailElement = document.getElementById('email1');
            const genderSelectElement = document.getElementById('genderSelect');
            const roleElement = document.getElementById('role');

            if (nameElement) nameElement.value = data.userName || 'Chưa cập nhật';
            if (userNameElement) userNameElement.textContent = data.userName || 'Chưa cập nhật';
            if (addressElement) addressElement.value = data.address || 'Chưa cập nhật';
            if (emailElement) emailElement.value = data.email || 'Chưa cập nhật';
            if (genderSelectElement) genderSelectElement.value = data.gender || 'Khác';
            if (roleElement) roleElement.value = data.role || 'Chưa cập nhật';
        })
        .catch(error => console.log(error));
}

function updateUser() {
    const token = localStorage.getItem('authToken');
    const updateButton = document.getElementById('Update_user');
    const editButton = document.getElementById('unlock_user');
    const userToken = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

    const User = new FormData();
    User.append('user', JSON.stringify({
        email: document.getElementById('email1').value,
        userName: document.getElementById('name').value,
        address: document.getElementById('address').value,
        gender: document.getElementById('genderSelect').value,
        role: document.getElementById('role').value
    }));

    fetch(`${window.base_url}/user-management/users/${userToken.userId}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: User
    })

        .then(response => {
            if (response.ok) {
                document.getElementById('name').disabled = true;
                document.getElementById('address').disabled = true;
                document.getElementById('email1').disabled = true;
                document.getElementById('genderSelect').disabled = true;

                updateButton.style.display = 'none';
                editButton.style.display = 'inline-block';

                fetchUser();
            } else {
                console.error('Error updating user:', response.statusText);
                alert('Có lỗi khi cập nhật thông tin người dùng');
            }
        })
        .catch(error => console.log(error));
}

function openChangePasswordForm() {
    document.getElementById('change-password-form').style.display = 'flex';
}

function closeChangePasswordForm() {
    document.getElementById('change-password-form').style.display = 'none';
}

function submitChangePassword() {
    const token = localStorage.getItem('authToken');
    const currentPassword = document.getElementById('current-password').value.trim();
    const newPassword = document.getElementById('new-password').value.trim();
    const confirmPassword = document.getElementById('confirm-password').value.trim();

    if (!currentPassword || !newPassword || !confirmPassword) {
        alert('All fields are required!');
        return;
    }

    if (newPassword === confirmPassword) {
        const requestBody = {
            oldPassword: String(currentPassword),
            password: String(newPassword),
            retypePassword: String(confirmPassword)
        };


        fetch(`${window.base_url}/forgot-password/change_password`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                return response.json().then(result => ({
                    status: response.status,
                    body: result
                }));
            })
            .then(result => {
                if (result.status === 200) {
                    alert('Đổi mật khẩu thành công!');
                    resetChangePasswordForm();
                } else {
                    alert('Đổi mật khẩu thất bại, chi tiết: ' + (result.body.message || 'Unknown error'));
                }
            })
            .catch(error => {
                console.error('Error occurred during password change:', error);
                alert(`Error: ${error.message}`);
            });
    } else {
        alert('Mât khẩu mới và nhập lại mật khẩu không trùng khớp!');
    }
}


function resetChangePasswordForm() {
    document.getElementById('current-password').value = '';
    document.getElementById('new-password').value = '';
    document.getElementById('confirm-password').value = '';
    closeChangePasswordForm();
}


