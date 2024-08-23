let addedRows = [];

// Function to handle form submission
async function submitForm(event) {
    event.preventDefault(); // Prevent the default form submission action

    const form = document.getElementById('userform');
    const formData = new FormData(form);

    const jsonData = {};
    formData.forEach((value, key) => {
        console.log(`${key}: ${value}`);  // Kiểm tra xem email có giá trị hay không
        jsonData[key] = value;
    });
    console.log('Final JSON Data:', jsonData);

    console.log(jsonData);
    try {
        const response = await fetch('http://localhost:8080/user-management/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonData),
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
    document.getElementById('userform').addEventListener('submit', submitForm);
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