document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    let deleteOrderId; // Biến lưu trữ ID đơn hàng để xóa
    let currentRow; // Biến lưu trữ dòng hiện tại của đơn hàng

    const fetchAndDisplayOrders = () => {
        fetch('http://localhost:8080/api/order/getAllOrder')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Data fetched:', data);

                const orders = data;

                if (!Array.isArray(orders)) {
                    throw new Error('Expected an array of orders');
                }

                const orderList = document.getElementById('order-List');
                orderList.innerHTML = ''; // Xóa các hàng trước

                orders.forEach((order, index) => {
                    if (order.Status === "Chưa Xác Nhận") {
                        const row = document.createElement('tr');

                        row.innerHTML = `
                            <td class="editable">${index + 1}</td>
                            <td class="editable">${order.OrderID}</td>
                            <td class="editable">${order.Customer.Name}</td>
                            <td class="editable">${order.OrderDate}</td>
                            <td class="editable">${order.Status}</td>
                            <td class="action-buttons">
                                <button class="view-btn"><ion-icon name="eye"></ion-icon></button>
                                <button class="edit-btn"><ion-icon name="create-outline"></button>
                                <button class="delete-btn" data-id="${order.OrderID}"><ion-icon name="trash-outline"></ion-icon></button>
                            </td>
                        `;

                        orderList.appendChild(row);

                        // Sự kiện khi nhấn nút view
                        const viewBtn = row.querySelector('.view-btn');
                        viewBtn.addEventListener('click', () => {
                            // Chuyển hướng đến trang chi tiết đơn hàng với ID tương ứng
                            window.location.href = `../order/detail.html?id=${order.OrderID}`;
                        });
                        // Sự kiện khi nhấn nút edit
                        const editBtn = row.querySelector('.edit-btn');
                        editBtn.addEventListener('click', () => {
                            // Chuyển hướng đến trang chi tiết đơn hàng với ID tương ứng
                            window.location.href = `../order/chinhsuaorder.html?id=${order.OrderID}`;
                        });
                        // Sự kiện khi nhấn nút delete
                        const deleteBtn = row.querySelector('.delete-btn');
                        deleteBtn.addEventListener('click', () => {
                            // Lưu ID đơn hàng và hiển thị popup
                            deleteOrderId = order.OrderID;
                            currentRow = row;
                            showPopup();
                        });
                    }
                });
            })
            .catch(error => console.error('Error fetching orders:', error));
    };

    // Hàm hiển thị popup
    const showPopup = () => {
        const popup = document.getElementById('popup');
        popup.style.display = 'flex';
    };

    // Event listener cho nút OK trong popup
    const popupOk = document.getElementById('popupOk');
    if (popupOk) {
        popupOk.addEventListener('click', async () => {
            if (deleteOrderId) {
                try {
                    const response = await fetch(`http://localhost:8080/api/order/deleteOrder/${deleteOrderId}`, {
                        method: 'DELETE'
                    });

                    if (!response.ok) {
                        throw new Error('Failed to delete order');
                    }

                    // Xóa dòng từ bảng khi xóa thành công
                    if (currentRow) {
                        currentRow.remove();
                    }

                    console.log('Order deleted successfully');
                } catch (error) {
                    console.error('Error deleting order:', error);
                    // Xử lý lỗi hoặc hiển thị thông báo lỗi
                    alert('Failed to delete order');
                } finally {
                    // Đóng popup sau khi thực hiện xóa
                    const popup = document.getElementById('popup');
                    popup.style.display = 'none';
                }
            }
        });
    } else {
        console.error('OK button not found in popup');
    }

    // Event listener cho nút Cancel trong popup
    const popupCancel = document.getElementById('popupCancel');
    if (popupCancel) {
        popupCancel.addEventListener('click', () => {
            // Đóng popup mà không làm gì thêm
            const popup = document.getElementById('popup');
            popup.style.display = 'none';
        });
    } else {
        console.error('Cancel button not found in popup');
    }

    // Gọi hàm fetch và hiển thị đơn hàng khi trang được tải lần đầu
    fetchAndDisplayOrders();
});

//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');

//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('logoutBtn').addEventListener('click', function(event) {
        event.preventDefault(); // Ngăn chặn hành động mặc định của thẻ <a>

        // Chuyển hướng đến trang logout.js
        window.location.href = '../Login/logout.js';
    });
});