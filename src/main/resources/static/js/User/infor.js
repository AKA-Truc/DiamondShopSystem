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

document.addEventListener('DOMContentLoaded', () => {
    fetchOrders();
});

function fetchOrders() {
    const token = localStorage.getItem('authToken');
    const user = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

    fetch(`${window.base_url}/order-management/orders/user/${user.userId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log('Fetched orders:', data);
            displayOrders(data.data); // Access the 'data' field from the response
        })
        .catch(error => {
            console.error('Error fetching orders:', error);
            alert('Failed to load orders.');
        });
}

function displayOrders(orders) {
    const orderList = document.querySelector('.order-list');
    orderList.innerHTML = ''; // Clear any existing orders

    orders.forEach(order => {
        // Create a new order item element
        const orderItem = document.createElement('div');
        orderItem.className = 'order-item';
        orderItem.setAttribute('data-status', order.status);

        // Populate order details
        orderItem.innerHTML = `
            <div class="order-header">
                <div class="order-id">Mã đơn: ${order.orderId}</div>
                <div class="order-status">Trạng thái: ${getStatusText(order.status)}</div>
            </div>
            <div class="order-body">
                ${order.orderDetails.map(detail => `
                    <div class="product-item">
                        <img src="${detail.product.imageURL}" alt="Product Image">
                        <div class="product-info">
                            <div class="product-name">${detail.product.productName}</div>
                            <div class="product-quantity">Số lượng: ${detail.quantity}</div>
                        </div>
                    </div>
                `).join('')}
            </div>
            <div class="order-footer">
                ${order.status === 'Đã giao' ? '<button class="btn-reorder">Mua lại</button>' : ''}
                ${order.status === 'New' ? '<button class="btn-cancel">Hủy đơn</button><button class="btn-detail">Xác nhận</button>' : ''}
                ${order.status === 'Đã xác nhận' ? `<span class="order-total">Tổng tiền: ${formatCurrency(order.totalPrice)}</span>` : ''}
            </div>
        `;

        orderList.appendChild(orderItem);
    });
}

function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => {
        if (section.id === sectionId) {
            section.style.display = 'block'; // Show the selected section
        } else {
            section.style.display = 'none'; // Hide other sections
        }
    });
}


function getStatusText(status) {
    switch (status) {
        case 'New': return 'Chờ xác nhận';
        case 'Đã xác nhận': return 'Đã xác nhận';
        case 'Đang vận chuyển': return 'Đang vận chuyển';
        case 'Đã giao': return 'Đã giao';
        default: return 'Không xác định';
    }
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
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
                    alert('Password changed successfully!');
                    resetChangePasswordForm();
                } else {
                    alert('Password change failed: ' + (result.body.message || 'Unknown error'));
                }
            })
            .catch(error => {
                console.error('Error occurred during password change:', error);
                alert(`Error: ${error.message}`);
            });
    } else {
        alert('New password and confirmation do not match!');
    }
}


function resetChangePasswordForm() {
    document.getElementById('current-password').value = '';
    document.getElementById('new-password').value = '';
    document.getElementById('confirm-password').value = '';
    closeChangePasswordForm();
}

