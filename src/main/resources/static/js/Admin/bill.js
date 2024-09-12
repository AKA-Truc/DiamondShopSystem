'use strict';

const fetchOrderDetailsAndUpdateHTML = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const InvoiceID = urlParams.get('InvoiceID');

    if (InvoiceID === undefined) {
        alert("Invoice ID is missing");
    }

    let totalAmount = 0;

    try {
        const invoiceResponse = await fetch(`http://localhost:8080/api/invoice/getInvoice/${InvoiceID}`);

        if (!invoiceResponse.ok) {
            throw new Error('Không thể tạo hóa đơn: ' + (await invoiceResponse.text()));
        }

        const invoice = await invoiceResponse.json();

        // Cập nhật các phần tử HTML với chi tiết đơn hàng
        if (invoice.Customer) {
            document.getElementById('customerName').innerText = invoice.Customer.Name || 'Chưa cập nhật';
            document.getElementById('customerPhone').innerText = invoice.Customer.Phone || 'Chưa cập nhật';
        }
        document.getElementById('InvoiceID').innerText = invoice.InvoiceID || 'Chưa cập nhật';
        document.getElementById('InvoiceDate').innerText = invoice.InvoiceDate ? new Date(invoice.InvoiceDate).toLocaleString() : 'Chưa cập nhật';
        document.getElementById('orderStatus').innerText = invoice.Status || 'Đã Xác Nhận';

        // Cập nhật bảng sản phẩm với chi tiết đơn hàng
        const productTableBody = document.getElementById('product-table-body');
        productTableBody.innerHTML = ''; // Xóa nội dung hiện tại

        if (invoice.Order && invoice.Order.OrderDetail && Array.isArray(invoice.Order.OrderDetail)) {
            invoice.Order.OrderDetail.forEach(detail => {
                console.log(detail);
                const productTotal = detail.Quantity * detail.Product.Price;
                totalAmount += productTotal;

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${detail.Product?.Name || 'Chưa cập nhật'}</td>
                    <td>${detail.Product?.Price.toLocaleString() || 'Chưa cập nhật'}</td>
                    <td>${detail.Quantity || 'Chưa cập nhật'}</td>
                    <td>${productTotal.toLocaleString() || 'Chưa cập nhật'}</td>
                `;
                productTableBody.appendChild(row);
            });
        }
        document.getElementById('totalPaymentAmount').innerText = invoice.Totalcost.toLocaleString();

        // Cập nhật tổng cộng
        document.getElementById('totalAmount').innerText = totalAmount.toLocaleString();

        // Lấy và áp dụng giảm giá từ mã giảm giá nếu có voucherId
        if (invoice.VoucherID) {
            const voucherResponse = await fetch(`http://localhost:8080/api/voucher/getVoucher/${invoice.VoucherID}`);
            if (!voucherResponse.ok) {
                throw new Error('Không thể lấy chi tiết mã giảm giá');
            }
            const voucher = await voucherResponse.json();
            console.log('Chi tiết mã giảm giá:', voucher);

            // Xử lý dữ liệu giảm giá
            const discountPercent = voucher.voucher.Percent !== undefined ? voucher.voucher.Percent : 0;
            const discountAmount = (discountPercent / 100) * totalAmount;
            const finalDiscountAmount = Math.min(discountAmount, voucher.voucher.Maxcost);

            document.getElementById('discountAmount').innerText = finalDiscountAmount.toLocaleString();

            // Hiển thị tên mã giảm giá đã áp dụng
            document.getElementById('voucherName').innerText = `${voucher.voucher.Name} - ${voucher.voucher.Describes} - Giảm tối đa ${voucher.voucher.Maxcost.toLocaleString()}`;
        } else {
            // Xử lý trường hợp không có voucherId
            document.getElementById('voucherName').innerText = "Không Sử Dụng";
            document.getElementById('discountAmount').innerText = '0';
        }
    } catch (error) {
        console.error('Lỗi khi lấy hoặc cập nhật chi tiết đơn hàng:', error);
        alert('Không thể lấy hoặc cập nhật chi tiết đơn hàng');
    }
};

// Gọi hàm fetchOrderDetailsAndUpdateHTML khi trang được tải
document.addEventListener('DOMContentLoaded', fetchOrderDetailsAndUpdateHTML);

// Xử lý sự kiện khi người dùng nhấn vào nút Xác Nhận Đơn Hàng
document.getElementById('confirmButton').addEventListener('click', async function () {
    try {
        // Chuyển hướng người dùng về trang chủ hoặc trang khác
        window.location.href = '../order/order.html'; // Thay đổi đường dẫn theo trang bạn muốn chuyển hướng đến
    } catch (error) {
        console.error('Lỗi khi xác nhận đơn hàng:', error);
        alert('Đã xảy ra lỗi khi xác nhận đơn hàng');
    }
});


//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');

//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });