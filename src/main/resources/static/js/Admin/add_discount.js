// Biến lưu trữ các hàng được thêm vào danh sách
let addedRows = [];

// Hàm xử lý khi người dùng gửi form
async function submitForm(event) {
    event.preventDefault(); // Ngăn chặn hành động mặc định của form

    const form = document.getElementById('voucherForm');
    const formData = new FormData(form);
    console.log(formData.get('EXDate'));

    try {
        const response = await fetch('http://localhost:8080/api/voucher/createNewVoucher', {
            method: 'POST',
            body: formData,
            mode: 'cors',
        });

        if (!response.ok) {
            throw new Error('Failed to save data');
        }

        const result = await response.json();
        console.log('Server response:', result);
        alert('Mã giảm giá đã được thêm.');
        window.location.href = '../magiamgia/mgg.html';
    } catch (error) {
        console.error('Error saving data:', error);
        alert('Đã xảy ra lỗi khi thêm mã giảm giá: ' + error.message);
    }
}

// Hàm xử lý khi người dùng hủy form
function cancelForm(event) {
    event.preventDefault(); // Ngăn chặn hành động mặc định của form
    if (confirm('Bạn có chắc chắn muốn hủy?')) {
        // Xóa giá trị nhập liệu trong các input
        const formElements = document.querySelectorAll('.wrapper input[type="text"], .wrapper input[type="number"], .wrapper input[type="date"]');
        formElements.forEach(element => {
            element.value = '';
        });

        // Xóa các hàng đã thêm vào danh sách
        addedRows.forEach(row => {
            row.remove();
        });

        // Xóa danh sách các hàng đã thêm
        addedRows = [];

        window.location.href = '../magiamgia/mgg.html';
    }
}

// Đính kèm sự kiện cho nút Hủy khi tài liệu HTML đã tải hoàn thành
document.addEventListener('DOMContentLoaded', () => {
    const cancelButton = document.querySelector('.cancel');
    if (cancelButton) {
        cancelButton.addEventListener('click', cancelForm);
    }

    // Đính kèm sự kiện cho form khi người dùng gửi
    document.getElementById('voucherForm').addEventListener('submit', submitForm);
});
