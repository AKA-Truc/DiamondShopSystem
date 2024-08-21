// Function to fetch and populate products in a select dropdown
async function fetchAndPopulateProducts() {
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

        const select = document.createElement('select');
        select.id = 'ProductID';

        data.productData.forEach(item => {
            const option = document.createElement('option');
            option.value = item.ProductID; // Replace item.ProductID with your actual product ID field
            option.textContent = item.Name; // Replace item.Name with your actual product name field
            select.appendChild(option);
        });

        document.body.appendChild(select);
    } catch (error) {
        console.error('Error fetching products:', error);
        alert('Đã xảy ra lỗi khi tải dữ liệu sản phẩm. Vui lòng thử lại sau!');
    }
}

// Function to add a product entry form dynamically
function addProduct() {
    const productList = document.getElementById('product-list');
    if (!productList) {
        console.error('No element with id "product-list" found.');
        return;
    }

    const productForm = document.createElement('div');
    productForm.className = 'product-form';

    // Clone the options from the initial select element
    const optionsHTML = document.getElementById('ProductID').innerHTML;

    productForm.innerHTML = `
      <select class="ProductID">
          ${optionsHTML}
      </select>
      <input type="number" placeholder="Số lượng" class="product-quantity" />
      <button class="remove-btn" onclick="removeProduct(this)">Xóa</button>
  `;

    productList.appendChild(productForm);
}

// Function to remove a product entry form
function removeProduct(button) {
    button.parentElement.remove();
}

// Function to create customer or find existing and then create order
async function createCustomerAndOrder() {
    try {
        const name = document.getElementById('Name').value;
        const phone = document.getElementById('Phone').value;
        const gender = document.querySelector('input[name="Gender"]:checked').value;
        const OrderDate = document.getElementById('OrderDate').value;
        const products = [];

        const productForms = document.querySelectorAll('.product-form');
        productForms.forEach(form => {
            const productId = form.querySelector('.ProductID').value;
            const quantity = form.querySelector('.product-quantity').value;
            products.push({ ProductID: productId, Quantity: quantity });
        });

        // Thử tìm khách hàng hiện có bằng số điện thoại
        let customerData = { customerId: null }; // Mặc định là null
        const responseFindCustomer = await fetch(`http://localhost:8080/api/customer/findCustomerByPhone?Name=${encodeURIComponent(name)}&Phone=${encodeURIComponent(phone)}&Gender=${encodeURIComponent(gender)}`);
        if (responseFindCustomer.ok) {
            customerData = await responseFindCustomer.json();
            console.log('Customer found:', customerData);
        } else {
            console.log('Customer not found, creating new one');
        }

        // Bước 2: Tạo đơn hàng
        const formDataOrder = {
            CustomerID: customerData.customerID, // Giả sử API của bạn trả về customerId
            OrderDate: OrderDate,
            orderDetails: products,
        };
        console.log(formDataOrder);

        const responseCreateOrder = await fetch('http://localhost:8080/api/order/createOrder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formDataOrder),
        });

        if (responseCreateOrder.ok) {
            const orderData = await responseCreateOrder.json();
            console.log('Order created:', orderData);
            alert('Đơn hàng đã được tạo thành công!');

            // Redirect to another page after order creation
            window.location.href = `./detail.html?id=${orderData.OrderID}`; // Change this to the desired page URL
        } else {
            const errorData = await responseCreateOrder.text();
            console.error('Failed to create order:', errorData);
            throw new Error('Failed to create order: ' + errorData);
        }
    } catch (error) {
        console.error('Error creating order:', error);
        alert('Đã xảy ra lỗi khi tạo đơn hàng. Vui lòng thử lại sau!');
    }
}
function cancelEdit() {
    if (confirm('Bạn có chắc chắn muốn hủy?')) {
        const orderId = new URLSearchParams(window.location.search).get('id');
        window.location.href = `../menu/menu.html`;
    }
}
// Function to submit the order
function submitOrder() {
    createCustomerAndOrder(); // Call createCustomerAndOrder function when submit button is clicked
}

// Fetch products on DOMContentLoaded
document.addEventListener('DOMContentLoaded', async () => {
    await fetchAndPopulateProducts();
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//     const accessToken = sessionStorage.getItem('accessToken');

//     if (!accessToken) {
//         window.location.href = '../Login/login.html';
//     }
// });