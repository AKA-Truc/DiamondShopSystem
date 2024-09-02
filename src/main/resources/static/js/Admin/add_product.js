let addedRows = [];

// Hàm xử lý gửi form
async function submitForm(event) {
    event.preventDefault(); // Ngăn chặn hành động mặc định của form

    const form = document.getElementById('myForm');
    const formData = new FormData(form);

    try {
        // Gửi dữ liệu form đến server
        const response = await fetch('http://localhost:8080/api/product/createProduct', {
            method: 'POST',
            body: formData,
            mode: 'cors',
        });

        // Kiểm tra phản hồi từ server
        if (!response.ok) {
            throw new Error('Failed to save data');
        }

        const result = await response.json();
        console.log('Server response:', result);
        alert('Đã thêm sản phẩm.');
        window.location.href = "../sanpham/sp.html"; // Chuyển hướng sau khi thành công
    } catch (error) {
        console.error('Error saving data:', error);
        alert('Đã xảy ra lỗi khi thêm sản phẩm: ' + error.message);
    }
}

function cancelForm(event) {
    event.preventDefault(); // Ngăn chặn hành động mặc định của nút hủy

    // Hiển thị hộp thoại xác nhận
    if (confirm('Bạn có chắc muốn hủy không?')) {
        // Nếu người dùng bấm "OK", chuyển hướng đến trang sản phẩm
        window.location.href = "product.html";
    }
    // Nếu người dùng bấm "Cancel", không làm gì cả và ở lại trang hiện tại
}


// Tải dữ liệu danh mục từ API khi DOM đã tải xong
document.addEventListener('DOMContentLoaded', async () => {
    try {
        const response = await fetch('http://localhost:8080/api/category/getAllcategory');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();

        // Kiểm tra dữ liệu trả về
        console.log(data);

        // Đảm bảo rằng listCategory là một mảng
        if (!Array.isArray(data.listCategory)) {
            throw new Error('Expected an array');
        }

        const select = document.getElementById('CategoryID');
        data.listCategory.forEach(item => {
            const option = document.createElement('option');
            option.value = item.CategoryID;
            option.textContent = item.Name;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching roles:', error);
    }
});

function addInputFields(buttonElement) {
    const parentTd = buttonElement.parentElement;

    // Tạo phần tử input mới cho tên kim cương và số lượng
    const newDiamondInput = document.createElement('input');
    newDiamondInput.type = 'text';
    newDiamondInput.className = 'table-control item-control';
    newDiamondInput.placeholder = 'Nhập tên kim cương';

    const newQuantityInput = document.createElement('input');
    newQuantityInput.type = 'text';
    newQuantityInput.className = 'table-control item-control quantity-input';
    newQuantityInput.placeholder = 'Nhập số lượng';

    // Thêm các input mới vào TD hiện tại (phía dưới các input cũ)
    parentTd.insertBefore(newDiamondInput, buttonElement);
    parentTd.insertBefore(newQuantityInput, buttonElement);
}

// Các hàm khác được giữ nguyên

//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');
//
//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });
