document.addEventListener('DOMContentLoaded', () => {
    const customerForm = document.getElementById("user-form");
    const customerTableBody = document.getElementById("customer-table-body");
    const searchInput = document.querySelector('.search-bar');
    const popup1Overlay = document.getElementById('popup1Overlay');
    const token = localStorage.getItem('authToken');
    let isEditing = false;
    let editingUserId = null;

    function fetchCustomers() {
        fetch(`${window.base_url}/user-management/users`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(result => {
                const data = result.data;

                if (!Array.isArray(data)) {
                    throw new Error("Expected an array but got something else");
                }

                customerTableBody.innerHTML = '';
                data.forEach((user, index) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                <td>${index + 1}</td>
                <td>${user.userName}</td>
                <td>${user.email}</td>
                <td>${user.gender}</td>
                <td>${user.role}</td>
                <td class="action-buttons">
                    <button class="edit-btn" onclick="editCustomer('${user.userId}')"><ion-icon name="create-outline"></ion-icon></button>
                    <button class="delete-btn" data-id="${user.userId}"><ion-icon name="trash-outline"></ion-icon></button>
                </td>
            `;
                    customerTableBody.appendChild(row);
                });

                addEditEventListeners();
                addDeleteEventListeners();
            })
            .catch(error => console.error("Error fetching users:", error));
    }

    function addCustomer(customerData) {
        fetch(`${window.base_url}/user-management/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(customerData)
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Your access is denied');
                    return;
                }
                return response.json();
            })
            .then(data => {
                alert("Customer added successfully!");
                fetchCustomers();
                popup1Overlay.style.display = 'none';
                resetFormState();
            })
            .catch(error => console.error("Error adding customer:", error));
    }

    function updateCustomer(customerData) {
        fetch(`${window.base_url}/user-management/users/${editingUserId}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
            body: customerData
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Your access is denied');
                    return;
                }
                return response.json();
            })
            .then(data => {
                alert("Customer updated successfully!");
                fetchCustomers();
                popup1Overlay.style.display = 'none';
                resetFormState();
            })
            .catch(error => console.error("Error updating customer:", error));
    }

    function updateUser() {
        const formData = new FormData(customerForm);

        if (isEditing) {
            const user = new FormData();
            user.append('user', JSON.stringify({
                userName: formData.get("userName"),
                email: formData.get("email"),
                address: formData.get("address"),
                role: formData.get("Role"),
                gender: formData.get("gender")
            }))
            updateCustomer(user);
        } else {
            const customerData = {
                userName: formData.get("userName"),
                email: formData.get("email"),
                password: formData.get('password'),
                address: formData.get("address"),
                role: formData.get("Role"),
                gender: formData.get("gender")
            };
            addCustomer(customerData);
        }
    }

    function addDeleteEventListeners() {
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
            if (currentRow && customerIdToDelete) {
                fetch(`${window.base_url}/user-management/users/${customerIdToDelete}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            currentRow.remove();
                            alert('Customer deleted successfully');
                        } else {
                            alert('Failed to delete customer');
                        }
                        popup.style.display = 'none';
                    })
                    .catch(error => {
                        console.error('Error deleting customer:', error);
                        popup.style.display = 'none';
                    });
            }
        });

        noBtn.addEventListener('click', () => {
            popup.style.display = 'none';
        });
    }

    function addEditEventListeners() {
        const editButtons = document.querySelectorAll('button[onclick^="editCustomer"]');
        editButtons.forEach(button => {
            button.addEventListener('click', function () {
                const userId = this.getAttribute('onclick').match(/'(.*?)'/)[1];
                editCustomer(userId);
            });
        });
    }

    customerForm.addEventListener("submit", (e) => {
        e.preventDefault();
        updateUser();
    });

    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#customer-table-body tr');

        rows.forEach(row => {
            const customerName = row.querySelector('td:nth-child(2)').textContent.toLowerCase();
            row.style.display = customerName.includes(searchText) ? 'table-row' : 'none';
        });
    });

    window.editCustomer = function(userId) {
        removeAdminOption();
        isEditing = true;
        editingUserId = userId;

        fetch(`${window.base_url}/user-management/users/${userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    if (response.status === 401) {
                        alert('You do not have access rights');
                    }
                    return Promise.reject(new Error('Invalid response'));
                }
                return response.json();
            })
            .then(user => {
                const data = user.data;

                const passwordField = document.getElementById("password");
                if (passwordField) {
                    passwordField.value = data.password;
                }


                if(data.role === "Admin"){
                    const roleSelect = document.getElementById('role');
                    const admin = document.createElement('option');
                    admin.value = data.role;
                    admin.textContent = data.role;
                    roleSelect.append(admin);
                    roleSelect.disabled = true;
                    document.getElementById("userName").disabled = true;
                    document.getElementById("email").disabled = true;
                    document.getElementById("address").disabled = true;
                    document.getElementById("role").disabled = true;
                    document.getElementById("password").disabled = true;
                    document.getElementById("gender").disabled = true;
                    document.getElementById("gender1").disabled = true;
                }
                document.getElementById("userName").value = data.userName;
                document.getElementById("email").value = data.email;
                document.getElementById("address").value = data.address;
                document.getElementById("role").value = data.role;

                const genderMale = document.getElementById("gender");
                const genderFemale = document.getElementById("gender1");
                if (data.gender === "Nam") {
                    genderMale.checked = true;
                } else if (data.gender === "Nữ") {
                    genderFemale.checked = true;
                }

                popup1Overlay.style.display = 'flex';
            })
            .catch(error => console.error("Error fetching customer details:", error));
    };

    function removeAdminOption() {
        const roleSelect = document.getElementById('role');
        const adminOption = roleSelect.querySelector('option[value="Admin"]');
        if (adminOption) {
            adminOption.remove();
        }
        roleSelect.disabled =false;
        document.getElementById("userName").disabled = false;
        document.getElementById("email").disabled = false;
        document.getElementById("address").disabled = false;
        document.getElementById("role").disabled = false;
        document.getElementById("password").disabled = false;
        document.getElementById("gender").disabled = false;
        document.getElementById("gender1").disabled = false;
    }

    document.getElementById('openpopup1').addEventListener('click', () => {
        customerForm.reset();
        popup1Overlay.style.display = 'flex';
        resetFormState();
        removeAdminOption();
    });

    document.getElementById('closepopup1').addEventListener('click', () => {
        if (confirm("Xác Nhận Hủy?")) {
            popup1Overlay.style.display = 'none';
            removeAdminOption();
        }
    });

    document.getElementById('cancelButton').addEventListener('click', () => {
        if (confirm("Xác Nhận Hủy?")) {
            popup1Overlay.style.display = 'none';
            removeAdminOption();
        }
    });

    window.addEventListener('click', (event) => {
        if (event.target === popup1Overlay) {
            if (confirm("Xác Nhận Hủy?")) {
                popup1Overlay.style.display = 'none';
                removeAdminOption();
            }
        }
    });

    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordField = document.getElementById('password');
        const eyeIcon = this.querySelector('ion-icon');

        // Toggle the type attribute
        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            eyeIcon.name = 'eye-outline';
        } else {
            passwordField.type = 'password';
            eyeIcon.name = 'eye-off-outline';
        }
    });

    function resetFormState() {
        isEditing = false;
        editingUserId = null;
        customerForm.reset();

        const passwordField = document.getElementById("password");
        if (passwordField) {
            passwordField.style.display = '';
            passwordField.removeAttribute('disabled'); // Enable the field back
            passwordField.setAttribute('required', 'required');
        }
    }

    fetchCustomers();
});
