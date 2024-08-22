async function fetchCategories() {
    try {
        const response = await fetch('http://localhost:8080/api/category/getAllcategory');
        if (!response.ok) {
            throw new Error('Failed to fetch categories');
        }
        const data = await response.json();

        // Log the raw response to see its structure
        console.log('Raw categories response:', data);

        // Extract categories from the response structure
        return data.listCategory || [];
    } catch (error) {
        console.error('Error fetching categories:', error);
        return [];
    }
}

async function fetchAndUpdateProductDetails(productId) {
    try {
        const [categories, productResponse] = await Promise.all([
            fetchCategories(),
            fetch(`http://localhost:8080/api/product/getProduct/${productId}`)
        ]);

        if (!productResponse.ok) {
            throw new Error('Failed to fetch product details');
        }
        const productData = await productResponse.json();

        console.log('Categories from API:', categories);
        console.log('Product data from API:', productData);

        // Populate the CategoryID select element with categories
        const categorySelect = document.getElementById('CategoryID');
        categorySelect.innerHTML = ''; // Clear any existing options

        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.CategoryID;
            option.textContent = category.Name;
            categorySelect.appendChild(option);
        });

        if (productData && productData.success && productData.productData) {
            const product = productData.productData;

            document.getElementById('Name').value = product.Name || '';
            console.log('Name:', document.getElementById('Name').value);

            document.getElementById('CategoryID').value = product.CategoryID || '';
            console.log('CategoryID:', document.getElementById('CategoryID').value);

            document.getElementById('Brand').value = product.Brand || '';
            console.log('Brand:', document.getElementById('Brand').value);

            document.getElementById('Price').value = product.Price || '';
            console.log('Price:', document.getElementById('Price').value);

            document.getElementById('Inventory').value = product.Inventory || '';
            console.log('Inventory:', document.getElementById('Inventory').value);

            document.getElementById('Volume').value = product.Volume || '';
            console.log('Volume:', document.getElementById('Volume').value);

            document.getElementById('Description').value = product.Description || '';
            console.log('Description:', document.getElementById('Description').value);

            const productImageElement = document.getElementById('productImage');
            console.log("Link:", productImageElement);
            const imagePath = product.image.replace(/\\/g, '/'); // Thay thế dấu gạch chéo ngược bằng dấu gạch chéo
            productImageElement.src = `http://localhost:8080/${imagePath}`;
        }
    } catch (error) {
        console.error('Error fetching data from API:', error);
        alert('Failed to fetch product details');
    }
}


// Function to handle product form submission
async function handleProductFormSubmit(event) {
    event.preventDefault();

    const productId = new URLSearchParams(window.location.search).get('id');
    const formData = new FormData();
    formData.append('image', document.getElementById('image').files[0]); // Chỉ lấy file đầu tiên nếu người dùng chọn nhiều tệp

    // Các trường thông tin khác
    formData.append('Name', document.getElementById('Name').value);
    formData.append('CategoryID', document.getElementById('CategoryID').value);
    formData.append('Brand', document.getElementById('Brand').value);
    formData.append('Price', document.getElementById('Price').value);
    formData.append('Inventory', document.getElementById('Inventory').value);
    formData.append('Volume', document.getElementById('Volume').value);
    formData.append('Description', document.getElementById('Description').value);

    try {
        const response = await fetch(`http://localhost:8080/api/product/updateProduct/${productId}`, {
            method: 'PUT',
            body: formData
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        alert('Product updated successfully!');
        window.location.href = '../sanpham/sp.html';
    } catch (error) {
        console.error('Error updating product:', error);
        alert('Failed to update product');
    }
}

function handleCancel() {
    if (confirm('Bạn có chắc muốn hủy không?')) {
        window.location.href = '../sanpham/sp.html';
    }
}
// Event listener for DOM content loaded
document.addEventListener('DOMContentLoaded', async function() {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');
    console.log("ProductID: ", productId);
    if (productId) {
        await fetchAndUpdateProductDetails(productId);
    }

    document.getElementById('myForm').addEventListener('submit', handleProductFormSubmit);
    document.querySelector('.cancel').addEventListener('click', handleCancel);
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');
//
//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });