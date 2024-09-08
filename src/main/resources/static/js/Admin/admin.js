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
            document.getElementById('name').value = data.userName;
            document.getElementById('user-name').textContent = data.userName;
            document.getElementById('address').value = data.address;
            document.getElementById('email1').value = data.email;
            document.getElementById('genderSelect').value = data.gender;
            document.getElementById('role').value = data.role;

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