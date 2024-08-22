let addedRows = [];

async function submitForm(event) {
    event.preventDefault();

    const form = document.getElementById('myForm');
    const formData = new FormData(form);

    try {
        const response = await fetch('http://localhost:8080/api/product/createProduct', {
            method: 'POST',
            body: formData,
            mode: 'cors',
        });

        if (!response.ok) {
            throw new Error('Failed to save data');
        }

        const result = await response.json();
        console.log('Server response:', result);
        alert('Đã thêm sản phẩm.');
        window.location.href = "../sanpham/sp.html";
    } catch (error) {
        console.error('Error saving data:', error);
        alert('Đã xảy ra lỗi khi thêm sản phẩm: ' + error.message);
    }
}

function cancelForm(event) {
    event.preventDefault();
    if (confirm('Bạn có chắc muốn hủy không?')) {
        const formElements = document.querySelectorAll('.wrapper input[type="text"], .wrapper input[type="number"]');
        formElements.forEach(element => {
            element.value = '';
        });

        const fileInput = document.getElementById('image');
        if (fileInput) fileInput.value = '';

        // Xóa các hàng đã thêm
        addedRows.forEach(row => {
            row.remove();
        });
        // Làm trống danh sách các hàng đã thêm
        addedRows = [];
        window.location.href = "../sanpham/sp.html";
    }
}

// Gắn sự kiện vào các nút khi DOM đã tải xong
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('myForm').addEventListener('submit', submitForm);
    document.querySelector('.cancel').addEventListener('click', cancelForm);
});

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
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');
//
//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });