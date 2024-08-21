async function fetchAndUpdateCustomerDetails(customerId) {
    try {
        const response = await fetch(`http://localhost:8080/api/customer/getCustomer/${customerId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch customer details');
        }
        const data = await response.json();

        console.log('Data from API:', data); // Check API data

        if (data.success && data.Customer) {
            const customer = data.Customer;

            document.getElementById('Name').value = customer.Name || '';
            console.log('Assigned value to Name:', customer.Name || '');

            document.getElementById('Phone').value = customer.Phone || '';
            console.log('Assigned value to Phone:', customer.Phone || '');

            if (customer.Gender === 'Nam') {
                document.getElementById('GenderMale').checked = true;
            } else if (customer.Gender === 'Nữ') {
                document.getElementById('GenderFemale').checked = true;
            }
            console.log('Assigned value to Gender:', customer.Gender || '');
        }
    } catch (error) {
        console.error('Error fetching data from API:', error);
        alert('Failed to fetch customer details');
    }
}

async function handleCustomerFormSubmit(event) {
    event.preventDefault();

    const customerId = new URLSearchParams(window.location.search).get('id');
    const updatedCustomer = {
        Name: document.getElementById('Name').value,
        Phone: document.getElementById('Phone').value,
        Gender: document.querySelector('input[name="Gender"]:checked').value
    };

    try {
        const response = await fetch(`http://localhost:8080/api/customer/updateCustomer/${customerId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedCustomer)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        alert('Customer updated successfully!');
        window.location.href = '../quanly/QLKH.html'; // Change to your redirect URL
    } catch (error) {
        console.error('Error updating customer:', error);
        alert('Failed to update customer');
    }
}

// Function to handle form cancellation
function cancelForm(event) {
    event.preventDefault(); // Prevent the default form cancellation action
    if (confirm('Bạn có chắc muốn hủy không?')) {
        window.location.href = '../quanly/QLKH.html'; // Change to your desired redirect URL
    }
}

// Event listener for DOM content loaded
document.addEventListener('DOMContentLoaded', async function() {
    const urlParams = new URLSearchParams(window.location.search);
    const customerId = urlParams.get('id');

    if (customerId) {
        await fetchAndUpdateCustomerDetails(customerId);
    }

    document.getElementById('customerForm').addEventListener('submit', handleCustomerFormSubmit);

    // Event listener for the "Hủy" button
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