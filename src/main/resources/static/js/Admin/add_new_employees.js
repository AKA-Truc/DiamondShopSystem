document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('employeeform');
    const cancelBtn = form.querySelector('.cancel');

    // Xử lý khi submit form
    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // Ngăn chặn hành động mặc định của form

        const formData = new FormData(form);
        console.log(formData);

        try {
            const response = await fetch('http://localhost:8080/api/employee/createEmployee', {
                method: 'POST',
                body: formData,
                mode: 'cors',
            });

            if (!response.ok) {
                throw new Error('Failed to save data');
            }

            const result = await response.json();
            console.log('Server response:', result);

            // Hiển thị thông báo thành công
            alert('Nhân viên đã được thêm thành công.');
            window.location.href = '../quanly/QLNS.html'; // Change to the URL you want to redirect to after successful submission
        } catch (error) {
            console.error('Error saving data:', error);
            alert('Đã xảy ra lỗi khi thêm Nhân Viên: ' + error.message);
        }
    });

    // Xử lý khi click nút Hủy
    cancelBtn.addEventListener('click', (event) => {
        event.preventDefault(); // Ngăn chặn hành động mặc định của nút Hủy

        if (confirm('Bạn có chắc muốn hủy không?')) {
            form.reset(); // Xóa các giá trị nhập trong form
            window.location.href = '../quanly/QLNS.html'; // Change to the URL you want to redirect to after cancellation
        }
    });
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');
//
//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });