let availableProducts = [];

async function fetchOrderDetails(orderId) {
    try {
        const response = await fetch(`http://localhost:8080/api/order/getOrder/${orderId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch order details');
        }
        const orderData = await response.json();
        console.log('Order data from API:', orderData);

        if (orderData) {
            document.getElementById('Name').textContent = orderData.Customer.Name || '';
            document.getElementById('Phone').textContent = orderData.Customer.Phone || '';
            document.getElementById('Gender').textContent = orderData.Customer.Gender || '';
            if (orderData.OrderDate) {
                const orderDate = new Date(orderData.OrderDate);
                const formattedOrderDate = orderDate.toISOString().slice(0, 16);
                document.getElementById('OrderDate').value = formattedOrderDate;
            } else {
                document.getElementById('OrderDate').value = '';
            }

            const productContainer = document.getElementById('product-list');
            productContainer.innerHTML = ''; // Clear existing product entries

        }
    } catch (error) {
        console.error('Error fetching order details:', error);
        alert('Failed to fetch order details');
    }
}

async function fetchAvailableProducts() {
    try {
        const response = await fetch('http://localhost:8080/api/product/getAllProduct');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();

        // Log the response data to check its structure
        console.log(data);

        // Check the actual structure of the data
        if (!Array.isArray(data.productData)) {
            throw new Error('Expected an array');
        }

        availableProducts = data.productData;
    } catch (error) {
        console.error('Error fetching products:', error);
        alert('Đã xảy ra lỗi khi tải dữ liệu sản phẩm. Vui lòng thử lại sau!');
    }
}

function addProductToForm(product = {}) {
    const productList = document.getElementById('product-list');
    if (!productList) {
        console.error('No element with id "product-list" found.');
        return;
    }

    const productForm = document.createElement('div');
    productForm.className = 'product-form';

    const select = document.createElement('select');
    select.className = 'ProductID';
    availableProducts.forEach(item => {
        const option = document.createElement('option');
        option.value = item.ProductID; // Replace item.ProductID with your actual product ID field
        option.textContent = item.Name; // Replace item.Name with your actual product name field
        select.appendChild(option);
    });

    // Set the selected value if provided
    if (product.ProductID) {
        select.value = product.ProductID;
    }

    productForm.innerHTML = `
        <input type="number" placeholder="Số lượng" class="product-quantity" value="${product.Quantity || ''}" />
        <button class="remove-btn" onclick="removeProduct(this)">Xóa</button>
    `;

    productForm.insertBefore(select, productForm.firstChild);
    productList.appendChild(productForm);
}

function removeProduct(button) {
    const productForm = button.parentElement;
    productForm.remove();
}

async function handleOrderFormSubmit(event) {
    event.preventDefault();

    const orderId = new URLSearchParams(window.location.search).get('id');
    const updatedOrder = {
        OrderDate: document.getElementById('OrderDate').value,
        orderDetails: Array.from(document.querySelectorAll('.product-form')).map(form => ({
            ProductID: form.querySelector('select.ProductID').value,
            Quantity: form.querySelector('.product-quantity').value
        }))
    };
    console.log("update Order: ",updatedOrder);
    try {
        const response = await fetch(`http://localhost:8080/api/order/updateOrder/${orderId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedOrder)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        alert('Order updated successfully!');
        window.location.href = `../order/detail.html?id=${orderId}`; // Change this to your redirection URL
    } catch (error) {
        console.error('Error updating order:', error);
        alert('Failed to update order');
    }
}
function cancelEdit() {
    if (confirm('Bạn có chắc chắn muốn hủy? Những thay đổi chưa lưu sẽ bị mất.')) {
        const orderId = new URLSearchParams(window.location.search).get('id');
        window.location.href = `../menu/menu.html`;
    }
}
document.addEventListener('DOMContentLoaded', async function() {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('id');
    console.log("OrderID: ", orderId);
    if (orderId) {
        await fetchOrderDetails(orderId);
    }

    await fetchAvailableProducts();
    document.getElementById('employeeform').addEventListener('submit', handleOrderFormSubmit);
});

function addProduct() {
    addProductToForm();
}
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');

//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });