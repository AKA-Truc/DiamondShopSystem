document.addEventListener('DOMContentLoaded', () => {
    const voucherForm = document.getElementById("voucherForm");
    const voucherList = document.getElementById("voucher-List");
    const token = localStorage.getItem('authToken'); // Lấy token từ localStorage

    console.log('Document is ready.');
    console.log('Token:', token);

    function fetchVouchers() {
        fetch('http://localhost:8080/promotion-management/promotions', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,  // Add the token to the header
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Your access is denied');
                }
                return response.json();
            })
            .then(result => {
                console.log(result);
                const data = result.data;

                if (!Array.isArray(data)) {
                    throw new Error("Expected an array but got something else");
                }

                voucherList.innerHTML = '';  // Clear previous data
                data.forEach((voucher, index) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${voucher.promotionName}</td>
                        <td>${voucher.discountPercent}%</td>
                        <td>${voucher.description}</td>
                        <td>${new Date(voucher.startDate).toLocaleString()}</td>
                        <td>${new Date(voucher.endDate).toLocaleString()}</td>
                        <td>
                            <button onclick="editVoucher(${voucher.promotionId})">Edit</button>
                            <button class="delete-btn" data-id="${voucher.promotionId}">Delete</button>
                        </td>
                    `;
                    voucherList.appendChild(row);
                });

                addDeleteEventListeners();
            })
            .catch(error => console.error("Error fetching vouchers:", error));
    }

    // Add event listeners for delete buttons
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
                fetch(`http://localhost:8080/promotion-management/promotions/${voucherIdToDelete}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`,  // Add the token to the header
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

    // Submit new voucher to API
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

        fetch('http://localhost:8080/promotion-management/promotions', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,  // Add token to the header
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(voucherData)
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Your access is denied');
                }
                return response.json();
            })
            .then(data => {
                alert("Voucher added successfully!");
                fetchVouchers();
            })
            .catch(error => console.error("Error adding voucher:", error));
    });

    // Search bar filter
    const searchInput = document.getElementById('search-bar');
    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#voucher-List tr');

        rows.forEach(row => {
            const voucherName = row.querySelector('td:nth-child(2)').textContent.toLowerCase();
            if (voucherName.includes(searchText)) {
                row.style.display = 'table-row';
            } else {
                row.style.display = 'none';
            }
        });
    });

    // Fetch the list of vouchers on page load
    fetchVouchers();
});
const openpopup1Button = document.getElementById('openpopup1');
const closepopup1Button = document.getElementById('closepopup1');
const cancelButton = document.getElementById('cancelButton');
const popup1Overlay = document.getElementById('popup1Overlay');

// Show the popup1
openpopup1Button.addEventListener('click', () => {
    popup1Overlay.style.display = 'flex';
});

// Close the popup1 when "X" is clicked
closepopup1Button.addEventListener('click', () => {
    popup1Overlay.style.display = 'none';
});

// Close the popup1 when "Hủy" button is clicked
cancelButton.addEventListener('click', () => {
    popup1Overlay.style.display = 'none';
});

// Close the popup1 when clicking outside of it
window.addEventListener('click', (event) => {
    if (event.target === popup1Overlay) {
        popup1Overlay.style.display = 'none';
    }
});