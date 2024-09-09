document.addEventListener('DOMContentLoaded', function() {
    // Sự kiện khi người dùng nhấn nút chỉnh sửa
    document.getElementById('unlock_user').addEventListener('click', function(event) {
        event.preventDefault();

        // Hiển thị nút cập nhật và ẩn nút chỉnh sửa
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

    // Tải thông tin người dùng khi trang được tải
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
    const user = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

    const UserForm = {
        email: document.getElementById('email1').value,
        userName: document.getElementById('name').value,
        address: document.getElementById('address').value,
        gender: document.getElementById('genderSelect').value,
        role: document.getElementById('role').value
    };

    fetch(`${window.base_url}/user-management/users/${user.userId}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(UserForm)
    })
        .then(response => {
            if (response.ok) {
                document.getElementById('name').disabled = true;
                document.getElementById('address').disabled = true;
                document.getElementById('email1').disabled = true;
                document.getElementById('genderSelect').disabled = true;

                // Ẩn nút cập nhật và hiển thị nút chỉnh sửa
                updateButton.style.display = 'none';
                editButton.style.display = 'inline-block';

                // Tải lại thông tin người dùng
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
    const currentPassword = document.getElementById('current-password').value;
    const newPassword = document.getElementById('new-password').value;
    const confirmPassword = document.getElementById('confirm-password').value;

    if (newPassword === confirmPassword) {
        fetch(`${window.base_url}/forgot-password/change_password`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                oldPassword: currentPassword,
                password: newPassword,
                retypePassword: confirmPassword
            })
        })
            .then(response => response.text())
            .then(result => {
                if (result.status === 200) {
                    alert('Đổi mật khẩu thành công');
                } else {
                    alert('Đổi mật khẩu không thành công');
                }
                resetChangePasswordForm();
            })
            .catch(error => {
                console.error('Lỗi khi đổi mật khẩu:', error);
                alert('Đã xảy ra lỗi khi đổi mật khẩu');
            });
    } else {
        alert('Mật khẩu mới và xác nhận mật khẩu không khớp');
    }
}

function resetChangePasswordForm() {
    document.getElementById('current-password').value = '';
    document.getElementById('new-password').value = '';
    document.getElementById('confirm-password').value = '';
    closeChangePasswordForm();

}
