document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    // Function to fetch and display vouchers
    const fetchAndDisplayVouchers = () => {
        fetch('http://localhost:8080/promotion-management/promotions')
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
                        window.location.href = `templates/Admin/edit_discount.html?id=${voucher.VoucherID}`;
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
                        fetch(`http://localhost:8080/promotion-management/promotions/${voucherIdToDelete}`, {
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

    // Initial fetch and display
    fetchAndDisplayVouchers();

    // Search functionality
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