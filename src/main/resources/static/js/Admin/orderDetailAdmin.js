document.addEventListener('DOMContentLoaded', async function () {
    const token = localStorage.getItem('authToken');
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('orderId');

    try {
        // Fetch order details
        const response = await fetch(`${window.base_url}/order-management/orders/${orderId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Error fetching order details');
        }

        const result = await response.json();
        const data = result.data;

        console.log(data);

        // Update order details on the page
        document.getElementById('phone').value = data.phone;
        document.getElementById('address').value = data.shippingAddress;
        document.getElementById('item-count').textContent = `Have ${data.orderDetails.length} item(s) in order`;
        document.getElementById('name-yourOrder').textContent = `${data.user.userName}'s Order`;
        document.getElementById("totalPrice").textContent = `${new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(data.totalPrice)}`;

        if (data.typePayment) {
            console.log(data.typePayment);
            const radioButton = document.querySelector(`input[name="typePayment"][value="${data.typePayment}"]`);
            if (radioButton) {
                console.log("aaaa");
                radioButton.checked = true;
                document.querySelectorAll('input[name="typePayment"]').forEach(radio => {
                    radio.disabled = true;
                });
            }
        }


        const cartItems = document.getElementById('product-list');

        // Handle promotions if available
        const promotionSelect = document.getElementById('promotion');
        if (promotionSelect && data.promotion) {
            promotionSelect.innerHTML = ''; // Clear previous options
            const option = document.createElement('option');
            option.value = data.promotion.promotionId;
            option.textContent = data.promotion.promotionName;
            promotionSelect.appendChild(option);
        }

        // Clear any previous product list items
        cartItems.innerHTML = '';

        // Loop through orderDetails to display product information
        for (const detail of data.orderDetails) {
            const product = detail.product;

            // Fetch product detail by size
            const productDetail = await getProductDetailBySize(product, detail.size);

            // If productDetail is null, skip this product
            if (!productDetail) {
                console.error(`Skipping productId ${product.productId} due to missing product detail by size.`);
                continue;
            }

            // Create a new card for each product
            const itemDiv = document.createElement('div');
            itemDiv.classList.add('card', 'mb-3');

            itemDiv.innerHTML = `
            <div class="row g-0">
                <div class="col-md-4">
                    <img src="${product.imageURL}" class="img-fluid rounded-start" alt="${product.productName}">
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <h5 class="card-title">${product.productName}</h5>
                        <p class="card-text">Quantity: ${detail.quantity}</p>
                        <p class="card-text">Category: ${product.category.categoryName}</p>
                        <p class="card-text">Warranty Period: ${product.warrantyPeriod} months</p>
                        <p class="card-text">Price: <span id="price-product-${detail.orderDetailId}">
                            ${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(detail.quantity * productDetail.sellingPrice)}
                        </span></p>
                        <div class="form-group">
                            <label for="sizeSelectId">Select Size:</label>
                            <input id="sizeSelectId" class="form-select size-selector" value="${detail.size}" disabled />
                        </div>
                    </div>
                </div>
            </div>
            `;
            cartItems.appendChild(itemDiv);
        }
    } catch (error) {
        console.error('Error fetching order details:', error);
    }
});

async function getProductDetailBySize(product, size) {
    try {
        const response = await fetch(`${window.base_url}/product-management/productDetailSize/${size}`, {
            method: 'POST', // Change to POST if needed
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                productId: product.productId
            })
        });

        if (!response.ok) {
            throw new Error('Error fetching product detail by size');
        }

        const result = await response.json();
        return result.data;
    } catch (error) {
        console.error(`Error fetching product detail by size for productId ${product.productId}:`, error);
        return null; // Return null on error to prevent further issues
    }
}
