document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    // Function to fetch and display customers
    const fetchAndDisplayCustomers = () => {
        fetch('http://localhost:8080/api/customer/getAllCustomer')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Data fetched:', data);

                // Extract the list of customers from the response data
                const customers = data.listCustomer;
                console.log(customers);

                if (!Array.isArray(customers)) {
                    throw new Error('Expected an array of customers');
                }

                const tableBody = document.getElementById('customer-table-body');
                tableBody.innerHTML = ''; // Clear previous rows

                customers.forEach((customer, index) => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${customer.Name}</td>
                        <td>${customer.Phone}</td>
                        <td>${customer.Gender}</td>
                        <td class="action-buttons">
                            <button class="edit-btn"><ion-icon name="create-outline"></ion-icon></button>
                            <button class="delete-btn" data-id="${customer.CustomerID}"><ion-icon name="trash-outline"></ion-icon></button>
                        </td>
                    `;

                    tableBody.appendChild(row);
                    // Add click event listener to edit button
                    const editBtn = row.querySelector('.edit-btn');
                    editBtn.addEventListener('click', () => {
                        // Redirect to another page with order ID
                        window.location.href = `../Themngdung/chinhsuaKH.html?id=${customer.CustomerID}`;
                    });
                });

                // Add event listeners for delete buttons
                const popup = document.getElementById('popup');
                const yesBtn = document.getElementById('yes-btn');
                const noBtn = document.getElementById('no-btn');
                const deleteBtns = document.querySelectorAll('.delete-btn');
                let currentRow;
                let customerIdToDelete;

                deleteBtns.forEach(button => {
                    button.addEventListener('click', function () {
                        popup.style.display = 'flex';
                        currentRow = this.closest('tr');
                        customerIdToDelete = this.getAttribute('data-id');
                    });
                });

                yesBtn.addEventListener('click', () => {
                    console.log(customerIdToDelete);
                    if (currentRow && customerIdToDelete) {
                        fetch(`http://localhost:8080/api/customer/deleteCustomer/${customerIdToDelete}`, {
                            method: 'DELETE'
                        })
                            .then(response => {
                                if (response.ok) {
                                    currentRow.remove();
                                    alert('Khách hàng đã được xóa thành công');
                                } else {
                                    alert('Không thể xóa khách hàng');
                                }
                                popup.style.display = 'none';
                            })
                            .catch(error => {
                                console.error('Lỗi khi xóa khách hàng:', error);
                                popup.style.display = 'none';
                            });
                    }
                });

                noBtn.addEventListener('click', () => {
                    popup.style.display = 'none';
                });
            })
            .catch(error => console.error('Lỗi khi lấy danh sách khách hàng:', error));
    };

    // Initial fetch and display
    fetchAndDisplayCustomers();

    // Search functionality
    const searchInput = document.querySelector('.search-bar');
    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#customer-table-body tr');

        rows.forEach(row => {
            const customerName = row.querySelector('td:nth-child(2)').textContent.toLowerCase();

            if (customerName.includes(searchText)) {
                row.style.display = 'table-row';
            } else {
                row.style.display = 'none';
            }
        });
    });
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');

//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });