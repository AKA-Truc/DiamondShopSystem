document.addEventListener('DOMContentLoaded', () => {
    const voucherForm = document.getElementById("voucherForm");
    const voucherList = document.getElementById("voucher-List");
    const searchInput = document.getElementById('search-bar');
    const popup1Overlay = document.getElementById('popup1Overlay');
    const token = localStorage.getItem('authToken');
    let isEditing = false;
    let editingVoucherId = null;

    function fetchVouchers() {
        fetch(`${window.base_url}/promotion-management/promotions`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(result => {
                const data = result.data;

                if (!Array.isArray(data)) throw new Error("Expected an array but got something else");

                voucherList.innerHTML = '';
                data.forEach((voucher, index) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${voucher.promotionName}</td>
                    <td>${voucher.discountPercent}%</td>
                    <td>${voucher.description}</td>
                    <td>${new Date(voucher.startDate).toLocaleString()}</td>
                    <td>${new Date(voucher.endDate).toLocaleString()}</td>
                    <td class="action-buttons">
                            <button class="edit-btn" onclick="editVoucher(${voucher.promotionId})"><ion-icon name="create-outline"></ion-icon></button>
                            <button class="delete-btn" data-id="${voucher.promotionId}"><ion-icon name="trash-outline"></ion-icon></button>
                        </td>
                `;
                    voucherList.appendChild(row);
                });
                addDeleteEventListeners();
            })
            .catch(error => console.error("Error fetching vouchers:", error));
    }

    function addOrUpdateVoucher(voucherData) {
        let url = isEditing
            ? `${window.base_url}/promotion-management/promotions/${editingVoucherId}`
            : `${window.base_url}/promotion-management/promotions`;

        let method = isEditing ? 'PUT' : 'POST';

        fetch(url, {
            method: method,
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(voucherData)
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Your access is denied');
                    return;
                }
                return response.json();
            })
            .then(() => {
                alert(isEditing ? "Voucher updated successfully!" : "Voucher added successfully!");
                fetchVouchers();
                popup1Overlay.style.display = 'none';
                resetFormState();
            })
            .catch(error => console.error("Error adding/updating voucher:", error));
    }

    function addDeleteEventListeners() {
        const popup = document.getElementById('popup');
        const yesBtn = document.getElementById('yes-btn');
        const noBtn = document.getElementById('no-btn');
        const deleteBtns = document.querySelectorAll('.delete-btn');
        let currentRow;
        let voucherIdToDelete;

        deleteBtns.forEach(button => {
            button.addEventListener('click', function () {
                popup.style.display = 'flex';
                currentRow = this.closest('tr');
                voucherIdToDelete = this.getAttribute('data-id');
            });
        });

        yesBtn.addEventListener('click', () => {
            if (currentRow && voucherIdToDelete) {
                fetch(`${window.base_url}/promotion-management/promotions/${voucherIdToDelete}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            currentRow.remove();
                            alert('Voucher deleted successfully');
                        } else {
                            alert('Failed to delete voucher');
                        }
                        popup.style.display = 'none';
                    })
                    .catch(error => {
                        console.error('Error deleting voucher:', error);
                        popup.style.display = 'none';
                    });
            }
        });

        noBtn.addEventListener('click', () => {
            popup.style.display = 'none';
        });
    }

    voucherForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const formData = new FormData(voucherForm);
        const voucherData = {
            promotionName: formData.get("Name"),
            discountPercent: formData.get("Percent"),
            startDate: formData.get("StartDate"),
            endDate: formData.get("EXDate"),
            description: formData.get("Describes")
        };
        addOrUpdateVoucher(voucherData);
    });

    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#voucher-List tr');

        rows.forEach(row => {
            const voucherName = row.querySelector('td:nth-child(2)').textContent.toLowerCase();
            row.style.display = voucherName.includes(searchText) ? 'table-row' : 'none';
        });
    });

    window.editVoucher = function(voucherId) {
        isEditing = true;
        editingVoucherId = voucherId;

        fetch(`${window.base_url}/promotion-management/promotions/${voucherId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok && response.status === 401) {
                    alert('You do not have permission');
                    return Promise.reject(new Error('Unauthorized'));
                }
                return response.json();
            })
            .then(voucher => {
                const data = voucher.data;
                document.getElementById("Name").value = data.promotionName;
                document.getElementById("Percent").value = data.discountPercent;
                document.getElementById("StartDate").value = new Date(data.startDate).toISOString().slice(0, 16);
                document.getElementById("EXDate").value = new Date(data.endDate).toISOString().slice(0, 16);
                document.getElementById("Describes").value = data.description;
                popup1Overlay.style.display = 'flex';
            })
            .catch(error => console.error("Error fetching voucher details:", error));
    };

    document.getElementById('openpopup1').addEventListener('click', () => {
        voucherForm.reset();
        popup1Overlay.style.display = 'flex';
        resetFormState();
    });

    document.getElementById('closepopup1').addEventListener('click', () => {
        if (confirm("Xác Nhận Hủy?")) {
            popup1Overlay.style.display = 'none';
        }
    });

    document.getElementById('cancelButton').addEventListener('click', () => {
        if (confirm("Xác Nhận Hủy?")) {
            popup1Overlay.style.display = 'none';
        }
    });

    window.addEventListener('click', (event) => {
        if (event.target === popup1Overlay && confirm("Xác Nhận Hủy?")) {
            popup1Overlay.style.display = 'none';
        }
    });

    function resetFormState() {
        isEditing = false;
        editingVoucherId = null;
    }

    fetchVouchers();
});
