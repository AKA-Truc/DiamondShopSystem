document.addEventListener('DOMContentLoaded', () => {
    const customerForm = document.getElementById("user-form");
    const customerTableBody = document.getElementById("customer-table-body");
    const searchInput = document.querySelector('.search-bar');
    const popup1Overlay = document.getElementById('popup1Overlay');
    const token = localStorage.getItem('authToken');
    let isEditing = false;
    let editingUserId = null;

    const fetchCustomers = () => {
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
                if (!Array.isArray(data)) throw new Error("Kết quả không phải là mảng");

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
            .catch(error => console.error("Lỗi khi lấy thông tin:", error));
    };

    const addCustomer = (customerData) => {
        console.log(customerData.password.length.toString());
        if (customerData.password.length.toString() < "8") {
            alert("Mật khẩu tối thiểu 8 ký tự");
            return;
        }
        fetch(`${window.base_url}/user-management/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(customerData)
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Bạn không có quyền truy cập');
                    return;
                }
                return response.json();
            })
            .then(() => {
                alert("Thêm người dùng thành công!");
                fetchCustomers();
                popup1Overlay.style.display = 'none';
                resetFormState();
            })
            .catch(error => console.error("Lỗi khi thêm người dùng:", error));
    };

    const updateCustomer = (customerData) => {
        fetch(`${window.base_url}/user-management/users/${editingUserId}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
            body: customerData
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Bạn không có quyền truy cập');
                    return;
                }
                return response.json();
            })
            .then(() => {
                alert("Cập nhật người dùng thành công!");
                fetchCustomers();
                popup1Overlay.style.display = 'none';
                resetFormState();
            })
            .catch(error => console.error("Lỗi khi cập nhật người dùng:", error));
    };

    const updateUser = () => {
        const formData = new FormData(customerForm);
        const userData = {
            userName: formData.get("userName"),
            email: formData.get("email"),
            address: formData.get("address"),
            role: formData.get("Role"),
            gender: formData.get("gender")
        };

        if (isEditing) {
            const user = new FormData();
            user.append('user', JSON.stringify(userData));
            updateCustomer(user);
        } else {
            userData.password = formData.get('password');
            addCustomer(userData);
        }
    };

    const addDeleteEventListeners = () => {
        const popup = document.getElementById('popup');
        const yesBtn = document.getElementById('yes-btn');
        const noBtn = document.getElementById('no-btn');
        const deleteBtns = document.querySelectorAll('.delete-btn');
        let currentRow;
        let customerIdToDelete;

        deleteBtns.forEach(button => {
            button.addEventListener('click', () => {
                popup.style.display = 'flex';
                currentRow = button.closest('tr');
                customerIdToDelete = button.getAttribute('data-id');
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
                            alert('Xóa người dùng thành công!');
                        } else {
                            alert('Lỗi khi xóa người dùng');
                        }
                        popup.style.display = 'none';
                    })
                    .catch(error => {
                        console.error('Lỗi khi xóa người dùng:', error);
                        popup.style.display = 'none';
                    });
            }
        });

        noBtn.addEventListener('click', () => {
            popup.style.display = 'none';
        });
    };

    const addEditEventListeners = () => {
        const editButtons = document.querySelectorAll('button[onclick^="editCustomer"]');
        editButtons.forEach(button => {
            button.addEventListener('click', function () {
                const userId = this.getAttribute('onclick').match(/'(.*?)'/)[1];
                editCustomer(userId);
            });
        });
    };

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
                        alert('Bạn không có quyền truy cập');
                    }
                    return Promise.reject(new Error('Lỗi phản hồi'));
                }
                return response.json();
            })
            .then(user => {
                const data = user.data;

                document.getElementById("userName").value = data.userName;
                document.getElementById("email").value = data.email;
                document.getElementById("address").value = data.address;
                document.getElementById("role").value = data.role;
                document.getElementById("password").value = data.password;
                document.getElementById("password").disabled = true;

                const genderMale = document.getElementById("gender");
                const genderFemale = document.getElementById("gender1");
                if (data.gender === "Nam") {
                    genderMale.checked = true;
                } else if (data.gender === "Nữ") {
                    genderFemale.checked = true;
                }

                if (data.role === "Admin") {
                    disableAdminFields(data.role);
                }

                popup1Overlay.style.display = 'flex';
            })
            .catch(error => console.error("Lỗi khi lấy thông tin người dùng:", error));
    };

    const disableAdminFields = (role) => {
        const roleSelect = document.getElementById('role');
        const admin = document.createElement('option');
        admin.value = role;
        admin.textContent = role;
        roleSelect.append(admin);
        roleSelect.disabled = true;

        ["userName", "email", "address", "password", "gender", "gender1"].forEach(id => {
            document.getElementById(id).disabled = true;
        });
    };

    const removeAdminOption = () => {
        const roleSelect = document.getElementById('role');
        const adminOption = roleSelect.querySelector('option[value="Admin"]');
        if (adminOption) adminOption.remove();

        ["userName", "email", "address", "password", "gender", "gender1"].forEach(id => {
            document.getElementById(id).disabled = false;
        });
    };

    const resetFormState = () => {
        isEditing = false;
        editingUserId = null;
        customerForm.reset();
    };

    document.getElementById('openpopup1').addEventListener('click', () => {
        resetFormState();
        popup1Overlay.style.display = 'flex';
    });

    document.getElementById('closepopup1').addEventListener('click', () => {
        if (confirm("Xác nhận hủy?")) {
            popup1Overlay.style.display = 'none';
            removeAdminOption();
        }
    });

    document.getElementById('cancelButton').addEventListener('click', () => {
        if (confirm("Xác nhận hủy?")) {
            popup1Overlay.style.display = 'none';
            removeAdminOption();
        }
    });

    window.addEventListener('click', (event) => {
        if (event.target === popup1Overlay && confirm("Xác nhận hủy?")) {
            popup1Overlay.style.display = 'none';
            removeAdminOption();
        }
    });

    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordField = document.getElementById('password');
        const eyeIcon = this.querySelector('ion-icon');
        passwordField.type = passwordField.type === 'password' ? 'text' : 'password';
        eyeIcon.name = passwordField.type === 'password' ? 'eye-off-outline' : 'eye-outline';
    });

    fetchCustomers();
});
