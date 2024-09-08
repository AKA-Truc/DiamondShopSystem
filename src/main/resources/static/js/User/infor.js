document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('unlock_user').addEventListener('click', function (event) {
        event.preventDefault();

        const Capnhat = document.getElementById('Update_user');
        const Chinhsua = document.getElementById('unlock_user');

        Capnhat.style.display = 'inline-block';
        Chinhsua.style.display = 'none';

        document.getElementById('name').disabled = false;
        document.getElementById('address').disabled = false;
        // document.getElementById('email1').disabled = false;
        document.getElementById('genderSelect').disabled = false;
    });

    fetchUser();
});
function fetchUser(){
    const token = localStorage.getItem('authToken');
    const user = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

    // Kiểm tra xem token có tồn tại không
    if (!token) {
        console.error('Auth token not found');
        return;
    }

    fetch(`${window.base_url}/user-management/users/${user.userId}`,{
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(result=>{

            const data = result.data;

            // Kiểm tra sự tồn tại của các phần tử trước khi cập nhật
            const nameElement = document.getElementById('name');
            const userNameElement = document.getElementById('user-name');
            const addressElement = document.getElementById('address');
            const emailElement = document.getElementById('email1');
            const genderSelectElement = document.getElementById('genderSelect');
            const roleElement = document.getElementById('role');

            if (nameElement) {
                nameElement.value = data.userName || 'Chưa cập nhật';
            }
            if (userNameElement) {
                userNameElement.textContent = data.userName || 'Chưa cập nhật';
            }
            if (addressElement) {
                addressElement.value = data.address || 'Chưa cập nhật';
            }
            if (emailElement) {
                emailElement.value = data.email || 'Chưa cập nhật';
            }
            if (genderSelectElement) {
                genderSelectElement.value = data.gender || 'Khác';
            }
            if (roleElement) {
                roleElement.value = data.role || 'Chưa cập nhật';
            }

        })
        .catch(error => {console.log(error)})
}

function updateUser(){
    const token = localStorage.getItem('authToken');
    const Capnhat = document.getElementById('Update_user');
    const Chinhsua = document.getElementById('unlock_user');
    const user = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

    const UserForm = {
        email: document.getElementById('email1').value,
        userName: document.getElementById('name').value,
        address: document.getElementById('address').value,
        gender: document.getElementById('genderSelect').value,
        role: document.getElementById('role').value
    }

    const formData = new FormData();

    formData.append('user', JSON.stringify(UserForm));

    Capnhat.style.display = "none";
    Chinhsua.style.display = "inline-block";

    fetch(`${window.base_url}/user-management/users/${user.userId}`,{
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
        body: formData
    })
        .then(response => {
            document.getElementById('name').disabled = true;
            document.getElementById('address').disabled = true;
            document.getElementById('email1').disabled = true;
            document.getElementById('genderSelect').disabled = true;

            fetchUser();
        })
        .catch(error => {console.log(error)})
}