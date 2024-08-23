let addedRows = [];

// Function to handle form submission
async function submitForm(event) {
    event.preventDefault(); // Prevent the default form submission action

    const form = document.getElementById('customerForm');
    const formData = new FormData(form);

    try {
        const response = await fetch('http://localhost:8080/user-management/users', {
            method: 'POST',
            body: formData,
            mode: 'cors',
        });

        if (!response.ok) {
            throw new Error('Failed to save data');
        }

        const result = await response.json();
        console.log('Server response:', result);
        alert('Khách hàng đã được thêm thành công.');
        window.location.href = '/templates/Admin/human_resource_management.html';
    } catch (error) {
        console.error('Error saving data:', error);
        alert('Đã xảy ra lỗi khi thêm khách hàng: ' + error.message);
    }
}

// Function to handle form cancellation
function cancelForm(event) {
    event.preventDefault(); // Prevent the default form cancellation action
    if (confirm('Bạn có chắc muốn hủy không?')) {
        window.location.href = '/templates/Admin/human_resource_management.html'; // Change to your desired redirect URL
    }
}

// Attach event listeners to the buttons once the DOM is fully loaded
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('customerForm').addEventListener('submit', submitForm);
    document.getElementById('cancelButton').addEventListener('click', cancelForm);
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');
//
//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });