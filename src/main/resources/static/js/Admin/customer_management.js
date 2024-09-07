document.addEventListener('DOMContentLoaded', () => {
    const customerForm = document.getElementById("user-form");
    const customerTableBody = document.getElementById("customer-table-body");
    const searchInput = document.querySelector('.search-bar');
    const popup1Overlay = document.getElementById('popup1Overlay');
    const token = localStorage.getItem('authToken');
    let isEditing = false;
    let editingUserId = null;

    console.log('Token is: ', token);

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
                console.log("Fetched users data:", result);
                const data = result.data;

                if (!Array.isArray(data)) {
                    throw new Error("Expected an array but got something else");
                }

                const customerTableBody = document.getElementById('customer-table-body');
                customerTableBody.innerHTML = '';
                data.forEach((user, index) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                <td>${index + 1}</td>
                <td>${user.userName}</td>
                <td>${user.email}</td>
                <td>${user.gender}</td>
                <td>${user.role}</td>
                <td>
                    <button onclick="editCustomer('${user.userId}')">Edit</button>
                    <button class="delete-btn" data-id="${user.userId}">Delete</button>
                </td>
            `;
                    customerTableBody.appendChild(row);
                });

                addEditEventListeners();
                addDeleteEventListeners();
            })
            .catch(error => console.error("Error fetching users:", error));
    }

    function addOrUpdateCustomer(customerData) {
        let url;
        let method;

        if (isEditing === true) {
            url = `${window.base_url}/user-management/users/${editingUserId}`;
            method = 'PUT';
            if (!customerData.password) {
                delete customerData.password;
            }
        } else {
            url = `${window.base_url}/user-management/register`;
            method = 'POST';
        }

        fetch(url, {
            method: method,
            headers: {
                'Authorization': `Bearer ${token}`,
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
                alert(isEditing ? "Customer updated successfully!" : "Customer added successfully!");
                fetchCustomers();
                popup1Overlay.style.display = 'none';
                resetFormState();
            })
            .catch(error => console.error("Error adding/updating customer:", error));
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
        const formData = new FormData(customerForm);
        const customerData = {
            userName: formData.get("userName"),
            email: formData.get("email"),
            password: formData.get("password"),
            address: formData.get("address"),
            role: formData.get("Role"),
            gender: formData.get("gender")
        };
        addOrUpdateCustomer(customerData);
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
        isEditing = true;
        editingUserId = userId;
        console.log(isEditing, editingUserId);

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

                const passwordField = document.getElementById("password");
                if (passwordField) {
                    passwordField.value = data.password;
                    passwordField.disabled = false;
                }
                popup1Overlay.style.display = 'flex';
            })
            .catch(error => console.error("Error fetching customer details:", error));
    };


    document.getElementById('openpopup1').addEventListener('click', () => {
        customerForm.reset();
        popup1Overlay.style.display = 'flex';
        resetFormState();
    });

    document.getElementById('closepopup1').addEventListener('click', () => {
        popup1Overlay.style.display = 'none';
    });

    document.getElementById('cancelButton').addEventListener('click', () => {
        popup1Overlay.style.display = 'none';
    });

    window.addEventListener('click', (event) => {
        if (event.target === popup1Overlay) {
            popup1Overlay.style.display = 'none';
        }
    });

    // Toggle Password Visibility with Eye Icon
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
