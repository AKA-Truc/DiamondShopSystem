document.addEventListener('DOMContentLoaded', () => {
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

function deleteProduct(index){
    const token = localStorage.getItem('authToken');

    fetch(`${window.base_url}/product-management/products/${index}`,{
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if(!response.ok){
                throw new Error("Fail to detele product");
            }
            alert('Delete product succesfully');
            fetchAndDisplayProducts();
        })
        .catch(error => {console.log(error)})
}

function fetchAndDisplayProducts(){
    const token = localStorage.getItem('authToken');
    const tableBody = document.getElementById('product-table-body');

    fetch(`${window.base_url}/product-management/products`,{
        method: 'GET',
        headers:{
            'Authorization': `Bearer ${token}`,
        }
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;

            tableBody.innerHTML = '';

            data.forEach((product, index) => {
                const row = document.createElement('tr');

                row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${product.productName}</td>
                        <td>${product.category.categoryName}</td>
                        <td>${product.warrantyPeriod}</td>
                        <td class="action-buttons">
                            <button class="edit-btn" onclick="editProduct('${product.productId}')"><ion-icon name="create-outline"></ion-icon></button>
                            <button class="delete-btn" onclick="deleteProduct('${product.productId}')" "><ion-icon name="trash-outline"></ion-icon></button>
                        </td>
                    `;
                tableBody.appendChild(row);
            })
        })
}

function editProduct(id){
    localStorage.setItem('edit',id);
    window.location.href = "/DiamondShopSystem/src/main/resources/templates/Admin/edit_product.html";
}

