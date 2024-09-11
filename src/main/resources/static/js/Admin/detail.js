document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    const searchInput = document.querySelector('.search-bar input'); // Input tìm kiếm

    // Đảm bảo base_url được định nghĩa
    window.base_url = window.base_url || 'http://localhost:8080';

    displayOrderDone();

    // Lọc theo tên khách hàng khi nhập dữ liệu vào ô tìm kiếm
    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#order-list tr');

        rows.forEach(row => {
            const customerName = row.querySelector('td:nth-child(3)').textContent.toLowerCase();
            row.style.display = customerName.includes(searchText) ? 'table-row' : 'none';
        });
    });

    function displayOrderDone() {
        const token = localStorage.getItem('authToken');

        if (!token) {
            console.error('Auth token not found');
            return;
        }

        fetch(`${window.base_url}/order-management/Bill`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch orders');
                }
                return response.json();
            })
            .then(result => {
                const dataList = result.data;
                console.log(dataList);

                if (!Array.isArray(dataList)) {
                    throw new Error('Expected an array of orders');
                }

                const orderList = document.getElementById('order-list');
                if (!orderList) {
                    throw new Error('Order list element not found');
                }

                orderList.innerHTML = '';

                dataList.forEach((data, index) => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td class="editable">${index + 1}</td>
                        <td class="editable">${data.orderId}</td>
                        <td class="editable">${data.user.userName}</td>
                        <td class="editable">${new Date(data.startDate).toISOString().slice(0, 10)}</td>
                        <td class="editable">${data.status}</td>
                        <td class="editable">${data.promotion.promotionName || 'Không có'}</td>
                        <td class="editable">${data.totalPrice}</td>
                        <td class="action-buttons">
                            <button class="edit-btn" onclick="showOrder(${data.orderId})">
                                <ion-icon name="eye-outline"></ion-icon>
                            </button>
                        </td>
                    `;

                    orderList.appendChild(row);
                });
                document.querySelectorAll('.edit-btn').forEach(button => {
                    button.addEventListener('click', (event) => {
                        const orderId = event.target.closest('button').getAttribute('data-order-id');
                        window.location.href = `/DiamondShopSystem/src/main/resources/templates/User/orderDetailAdmin.html?orderId=${orderId}`;
                    });
                });
            })
            .catch(error => {
                console.error('Error fetching orders:', error);
            });
    }
});
