document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    // Function to fetch and display employees
    const fetchAndDisplayEmployees = () => {
        fetch('http://localhost:8080/api/employee/getAllEmployee')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Data fetched:', data);

                const employees = data.listEmployee;

                if (!Array.isArray(employees)) {
                    throw new Error('Expected an array of employees');
                }

                const tableBody = document.getElementById('employee-table-body');
                tableBody.innerHTML = ''; // Clear previous rows

                employees.forEach((employee, index) => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${employee.Name}</td>
                        <td>${employee.Phone}</td>
                        <td>${employee.Gender}</td>
                        <td>${employee.Address}</td>
                        <td>${employee.Role}</td>
                        <td>${employee.Salary}</td>
                        <td class="action-buttons">
                            <button class="edit-btn"><ion-icon name="create-outline"></ion-icon></button>
                            <button class="delete-btn" data-id="${employee.EmployeeID}"><ion-icon name="trash-outline"></ion-icon></button>
                        </td>
                    `;

                    tableBody.appendChild(row);
                    // Add click event listener to edit button
                    const editBtn = row.querySelector('.edit-btn');
                    editBtn.addEventListener('click', () => {
                        // Redirect to another page with order ID
                        window.location.href = `../Themngdung/chinhsuaNV.html?id=${employee.EmployeeID}`;
                    });
                });

                // Add event listeners for delete buttons
                const popup = document.getElementById('popup');
                const yesBtn = document.getElementById('yes-btn');
                const noBtn = document.getElementById('no-btn');
                const deleteBtns = document.querySelectorAll('.delete-btn');
                let currentRow;
                let employeeIdToDelete;

                deleteBtns.forEach(button => {
                    button.addEventListener('click', function () {
                        popup.style.display = 'flex';
                        currentRow = this.closest('tr');
                        employeeIdToDelete = this.getAttribute('data-id');
                    });
                });

                yesBtn.addEventListener('click', () => {
                    console.log(employeeIdToDelete);
                    if (currentRow && employeeIdToDelete) {
                        fetch(`http://localhost:8080/api/employee/deleteEmployee/${employeeIdToDelete}`, {
                            method: 'DELETE'
                        })
                            .then(response => {
                                if (response.ok) {
                                    currentRow.remove();
                                    alert('Nhân viên đã được xóa thành công');
                                } else {
                                    alert('Không thể xóa nhân viên');
                                }
                                popup.style.display = 'none';
                            })
                            .catch(error => {
                                console.error('Lỗi khi xóa nhân viên:', error);
                                popup.style.display = 'none';
                            });
                    }
                });

                noBtn.addEventListener('click', () => {
                    popup.style.display = 'none';
                });
            })
            .catch(error => console.error('Lỗi khi lấy danh sách nhân viên:', error));
    };

    // Initial fetch and display
    fetchAndDisplayEmployees();

    // Search functionality
    const searchInput = document.querySelector('.search-bar');
    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#employee-table-body tr');

        rows.forEach(row => {
            const employeeName = row.querySelector('td:nth-child(2)').textContent.toLowerCase();

            if (employeeName.includes(searchText)) {
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