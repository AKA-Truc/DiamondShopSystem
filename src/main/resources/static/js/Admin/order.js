document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    const searchInput = document.querySelector('.search-bar input');

    displayOrderNotDone();

    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#invoice-list tr');

        rows.forEach(row => {
            const customerName = row.querySelector('td:nth-child(3)').textContent.toLowerCase();
            row.style.display = customerName.includes(searchText) ? 'table-row' : 'none';
        });
    });

    // Fetch and display orders that are not done
    function displayOrderNotDone() {
        const token = localStorage.getItem('authToken');

        if (!token) {
            console.error('Auth token not found');
            return;
        }

        fetch(`${window.base_url}/order-management/NotDone`, {
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
                if (!Array.isArray(dataList)) {
                    throw new Error('Expected an array of orders');
                }

                const invoiceList = document.getElementById('invoice-list');
                if (!invoiceList) {
                    throw new Error('Invoice list element not found');
                }

                // Clear current list
                invoiceList.innerHTML = '';

                // Create table rows dynamically
                dataList.forEach((data, index) => {
                    const row = document.createElement('tr');
                    const statusClass = getStatusClass(data.status);

                    row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${data.orderId}</td>
                    <td>${data.user.userName}</td>
                    <td>${new Date(data.startDate).toISOString().slice(0, 10)}</td>
                    <td>
                        <select class="status-select ${statusClass}" data-order-id="${data.orderId}">
                            <option value="Chưa xác nhận" ${data.status === 'New' ? 'selected' : ''}>Chưa xác nhận</option>
                            <option value="Đã xác nhận" ${data.status === 'Đã xác nhận' ? 'selected' : ''}>Đã xác nhận</option>
                            <option value="Đang giao" ${data.status === 'Đang giao' ? 'selected' : ''}>Đang giao</option>
                            <option value="Đã giao" ${data.status === 'Đã giao' ? 'selected' : ''}>Đã giao</option>
                        </select>
                    </td>
                    `;

                    invoiceList.appendChild(row);

                    const selectElement = row.querySelector('.status-select');

                    // Apply styles to the select element and its options
                    applySelectStyles(selectElement);

                    selectElement.addEventListener('change', (event) => {
                        const orderId = event.target.getAttribute('data-order-id');
                        const newStatus = event.target.value;
                        console.log(orderId, newStatus);
                        const newStatusClass = getStatusClass(newStatus);

                        // Update class and styles dynamically
                        event.target.className = `status-select ${newStatusClass}`;
                        applySelectStyles(event.target);
                        updateOrderStatus(orderId, newStatus);
                    });
                });
            })
            .catch(error => {
                console.error('Error fetching orders:', error);
            });
    }


    function applySelectStyles(selectElement) {
        const options = selectElement.options;

        for (let i = 0; i < options.length; i++) {
            const option = options[i];

            switch (option.value) {
                case 'Chưa xác nhận':
                    option.style.backgroundColor = '#f3de89';
                    option.style.color = '#333';
                    break;
                case 'Đã xác nhận':
                    option.style.backgroundColor = '#7abc96';
                    option.style.color = '#fff'; // White text
                    break;
                case 'Đang giao':
                    option.style.backgroundColor = '#6ab1ca';
                    option.style.color = '#fff';
                    break;
                case 'Đã giao':
                    option.style.backgroundColor = '#87a9df';
                    option.style.color = '#fff';
                    break;
                default:
                    option.style.backgroundColor = '';
                    option.style.color = '';
            }
        }
    }


    function updateOrderStatus(orderId, newStatus) {
        const token = localStorage.getItem('authToken');

        if (!token) {
            console.error('Auth token not found');
            return;
        }

        fetch(`${window.base_url}/order-management/orders/${orderId}?status=${newStatus}`, {
            method: 'PATCH',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to update order status');
                }
                alert('Order status updated successfully');
            })
            .catch(error => {
                console.error('Error updating order status:', error);
                alert(`Error: ${error.message}`);
            });
    }


    // Get status class for <select>
    function getStatusClass(status) {
        switch (status) {
            case 'New': return 'status-chua-xac-nhan';
            case 'Đã xác nhận': return 'status-da-xac-nhan';
            case 'Đang giao': return 'status-dang-giao';
            case 'Đã giao': return 'status-da-giao';
            default: return '';
        }
    }
});

