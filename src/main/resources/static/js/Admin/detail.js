'use strict';

const fetchOrderDetailsAndUpdateHTML = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('id');

    try {
        const orderResponse = await fetch(`http://localhost:8080/api/order/getOrder/${orderId}`);
        if (!orderResponse.ok) {
            throw new Error('Failed to fetch order details');
        }
        const order = await orderResponse.json();

        // Log the order object to check its structure
        console.log(order);

        // Update HTML elements with order details
        if (order.Customer) {
            document.getElementById('customerName').innerText = order.Customer.Name || 'N/A';
            document.getElementById('customerPhone').innerText = order.Customer.Phone || 'N/A';
        }
        document.getElementById('orderID').innerText = order.OrderID || 'N/A';
        document.getElementById('orderDate').innerText = order.OrderDate ? new Date(order.OrderDate).toLocaleString() : 'N/A';
        document.getElementById('orderStatus').innerText = order.Status || 'N/A';

        // Example: Updating a product table (adjust based on your actual table structure)
        const productTableBody = document.getElementById('product-table-body');
        productTableBody.innerHTML = ''; // Clear existing content

        if (order.OrderDetail && Array.isArray(order.OrderDetail)) {
            order.OrderDetail.forEach(detail => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${detail.Product?.Name || 'N/A'}</td>
                    <td>${detail.Product?.Price.toLocaleString() || 'N/A'}</td>
                    <td>${detail.Quantity || 'N/A'}</td>
                `;
                productTableBody.appendChild(row);
            });
        }

    } catch (error) {
        console.error('Error fetching or updating order details:', error);
        alert('Failed to fetch or update order details');
    }
};

const fetchVouchersAndUpdateSelect = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('id');

    try {
        // Fetch order details to get the OrderDate
        const orderResponse = await fetch(`http://localhost:8080/api/order/getOrder/${orderId}`);
        if (!orderResponse.ok) {
            throw new Error('Failed to fetch order details');
        }
        const order = await orderResponse.json();
        const orderDate = new Date(order.OrderDate); // Order date

        // Fetch all vouchers with EXDate
        const voucherResponse = await fetch('http://localhost:8080/api/voucher/getAllVouchers'); // Adjust URL if needed
        if (!voucherResponse.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await voucherResponse.json();

        // Filter vouchers based on EXDate > OrderDate
        const validVouchers = data.listVoucher.filter(item => {
            const exDate = new Date(item.EXDate); // EXDate of the voucher
            return exDate > orderDate;
        });

        // Log filtered vouchers
        console.log('Valid vouchers:', validVouchers);

        const select = document.getElementById('VoucherID');
        select.innerHTML = ''; // Clear existing options

        validVouchers.forEach(item => {
            const option = document.createElement('option');
            option.value = item.VoucherID; // Adjust field based on actual data
            option.textContent = `${item.Name} - ${item.Describes}`; // Adjust field based on actual data
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching or updating vouchers:', error);
    }
};

document.addEventListener('DOMContentLoaded', () => {
    fetchOrderDetailsAndUpdateHTML();
    fetchVouchersAndUpdateSelect();

    document.getElementById('confirm-btn').addEventListener('click', async () => {
        const orderId = document.getElementById('orderID').innerText.trim();
        const voucherId = document.getElementById('VoucherID').value.trim();

        try {
            // Thực hiện tạo hóa đơn bằng cách gọi API createInvoice
            const formInvoice = {
                OrderID: orderId,
                VoucherID: voucherId,
            };

            const invoiceResponse = await fetch('http://localhost:8080/api/invoice/createInvoice', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formInvoice)
            });

            if (!invoiceResponse.ok) {
                throw new Error('Không thể tạo hóa đơn: ' + (await invoiceResponse.text()));
            }

            const invoice = await invoiceResponse.json();
            console.log('Chi tiết hóa đơn:', invoice);

            // Xác nhận đơn hàng sau khi tạo hóa đơn thành công
            const confirmResponse = await fetch(`http://localhost:8080/api/order/confirmOrder/${orderId}`, {
                method: 'PUT'
            });

            if (!confirmResponse.ok) {
                throw new Error('Không thể xác nhận đơn hàng: ' + (await confirmResponse.text()));
            }

            // Chuyển hướng người dùng đến trang bill.html sau khi tạo hóa đơn thành công
            window.location.href = `bill.html?InvoiceID=${invoice.invoice.InvoiceID}`;

        } catch (error) {
            console.error('Lỗi khi xác nhận đơn hàng:', error);
            alert('Đã xảy ra lỗi khi xác nhận đơn hàng: ' + error.message);
        }
    });

    document.getElementById('back-btn').addEventListener('click', () => {
        // Construct the new URL
        const newUrl = `../menu/menu.html`;

        // Redirect to the new page
        window.location.href = newUrl;
    });
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');

//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });