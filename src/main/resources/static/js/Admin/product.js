document.addEventListener('DOMContentLoaded', () => {
    console.log('Document is ready.');

    const fetchAndDisplayProducts = () => {
        fetch('http://localhost:8080/api/product/getAllProduct')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Data fetched:', data);

                const products = data.productData;

                if (!Array.isArray(products)) {
                    throw new Error('Expected an array of products');
                }

                const tableBody = document.getElementById('product-table-body');
                tableBody.innerHTML = ''; // Clear previous rows

                products.forEach((product, index) => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${product.Name}</td>
                        <td>${product.Category.Name}</td>
                        <td>${product.Description}</td>
                        <td>${product.Volume}</td>
                        <td>${product.Price}</td>
                        <td>${product.Inventory}</td>
                        <td class="action-buttons">
                            <button class="edit-btn"><ion-icon name="create-outline"></ion-icon></button>
                            <button class="delete-btn" data-id="${product.ProductID}"><ion-icon name="trash-outline"></ion-icon></button>
                        </td>
                    `;

                    tableBody.appendChild(row);
                    // Add click event listener to edit button
                    const editBtn = row.querySelector('.edit-btn');
                    editBtn.addEventListener('click', () => {
                        // Redirect to another page with order ID
                        window.location.href = `../sanpham/chinhsuasp.html?id=${product.ProductID}`;
                    });
                });

                // Add event listeners for delete buttons
                const popup = document.getElementById('popup');
                const yesBtn = document.getElementById('yes-btn');
                const noBtn = document.getElementById('no-btn');
                const deleteBtns = document.querySelectorAll('.delete-btn');
                let currentRow;
                let productIdToDelete;

                deleteBtns.forEach(button => {
                    button.addEventListener('click', function () {
                        popup.style.display = 'flex';
                        currentRow = this.closest('tr');
                        productIdToDelete = this.getAttribute('data-id');
                    });
                });

                yesBtn.addEventListener('click', () => {
                    console.log(productIdToDelete);
                    if (currentRow && productIdToDelete) {
                        fetch(`http://localhost:8080/api/product/deleteProduct/${productIdToDelete}`, {
                            method: 'DELETE'
                        })
                            .then(response => {
                                if (response.ok) {
                                    currentRow.remove();
                                    alert('Product deleted successfully');
                                } else {
                                    alert('Failed to delete product');
                                }
                                popup.style.display = 'none';
                            })
                            .catch(error => {
                                console.error('Error deleting product:', error);
                                popup.style.display = 'none';
                            });
                    }
                });

                noBtn.addEventListener('click', () => {
                    popup.style.display = 'none';
                });
            })
            .catch(error => console.error('Error fetching products:', error));
    };

    fetchAndDisplayProducts();

    // Search functionality
    const searchInput = document.querySelector('.search-bar');
    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#product-table-body tr');

        rows.forEach(row => {
            const productName = row.querySelector('td:nth-child(2)').textContent.toLowerCase();

            if (productName.includes(searchText)) {
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