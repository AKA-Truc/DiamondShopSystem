document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');


    const fetchAndDisplayVouchers = () => {
        fetch('http://localhost:8080/api/voucher/getAllVouchers')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Data fetched:', data);

                const vouchers = data.listVoucher;

                if (!Array.isArray(vouchers)) {
                    throw new Error('Expected an array of vouchers');
                }

                const voucherList = document.getElementById('voucher-List');
                voucherList.innerHTML = ''; // Clear previous rows

                vouchers.forEach((voucher, index) => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${voucher.Name}</td>
                        <td>${voucher.Percent}</td>
                        <td>${voucher.Describes}</td>
                        <td>${voucher.Mincost}</td>
                        <td>${voucher.Maxcost}</td>
                        <td>${voucher.EXDate}</td>
                        <td class="action-buttons">
                            <button class="edit-btn"><ion-icon name="create-outline"></ion-icon></button>
                            <button class="delete-btn" data-id="${voucher.VoucherID }"><ion-icon name="trash-outline"></ion-icon></button>
                        </td>
                    `;

                    voucherList.appendChild(row);
                    // Add click event listener to edit button
                    const editBtn = row.querySelector('.edit-btn');
                    editBtn.addEventListener('click', () => {
                        // Redirect to another page with order ID
                        window.location.href = `../magiamgia/chinhsuamagg.html?id=${voucher.VoucherID}`;
                    });
                });

                // Add event listeners for delete buttons
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
                    console.log(voucherIdToDelete);
                    if (currentRow && voucherIdToDelete) {
                        fetch(`http://localhost:8080/api/voucher/deleteVoucher/${voucherIdToDelete}`, {
                            method: 'DELETE'
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
            })
            .catch(error => console.error('Error fetching vouchers:', error));
    };


    fetchAndDisplayVouchers();


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
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');
//
//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });


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