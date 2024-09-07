document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    const searchInput = document.querySelector('.search-bar input'); // Input tìm kiếm

    // Gọi hàm displayOrderNotDone khi DOM đã được tải
    displayOrderNotDone();

    // Lọc theo tên khách hàng khi nhập dữ liệu vào ô tìm kiếm
    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#invoice-list tr');

        rows.forEach(row => {
            const customerName = row.querySelector('td:nth-child(3)').textContent.toLowerCase();
            row.style.display = customerName.includes(searchText) ? 'table-row' : 'none';
        });
    });

    function displayOrderNotDone() {
        const token = localStorage.getItem('authToken');

        // Kiểm tra xem token có tồn tại không
        if (!token) {
            console.error('Auth token not found');
            return;
        }

        // Gửi yêu cầu để lấy danh sách hóa đơn chưa hoàn thành
        fetch(`${window.base_url}/order-management/NotDone`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                // Kiểm tra nếu phản hồi không phải là JSON hợp lệ
                if (!response.ok) {
                    throw new Error('Failed to fetch orders');
                }
                return response.json();
            })
            .then(result => {
                const dataList = result.data;
                console.log(dataList);

                // Kiểm tra xem dữ liệu nhận được có phải là một mảng không
                if (!Array.isArray(dataList)) {
                    throw new Error('Expected an array of orders');
                }

                const invoiceList = document.getElementById('invoice-list');
                if (!invoiceList) {
                    throw new Error('Invoice list element not found');
                }

                // Xóa các dòng cũ trong danh sách
                invoiceList.innerHTML = '';

                // Duyệt qua danh sách hóa đơn và tạo các hàng mới
                dataList.forEach((data, index) => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td class="editable">${index + 1}</td>
                        <td class="editable">${data.orderId}</td>
                        <td class="editable">${data.user.userName}</td>
                        <td class="editable">${new Date(data.startDate).toISOString().slice(0, 10)}</td>
                        <td class="editable">${data.promotion.promotionName || 'Không có'}</td>
                        <td class="editable">${data.totalPrice}</td>
                        <td class="action-buttons">
                            <button class="edit-btn" onclick="showOrder(${data.orderId})">
                                <ion-icon name="eye-outline"></ion-icon>
                            </button>
                        </td>
                    `;

                    // Thêm hàng mới vào danh sách
                    invoiceList.appendChild(row);
                });
            })
            .catch(error => {
                // Hiển thị lỗi nếu có
                console.error(error);
            });
    }
});
