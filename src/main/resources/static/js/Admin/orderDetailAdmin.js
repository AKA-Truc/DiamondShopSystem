// document.addEventListener('DOMContentLoaded', async function () {
//     const token = localStorage.getItem('authToken');
//     const urlParams = new URLSearchParams(window.location.search);
//     const orderId = urlParams.get('orderId');
//
//     try {
//         // Fetch order details
//         const response = await fetch(`${window.base_url}/order-management/orders/${orderId}`, {
//             method: 'GET',
//             headers: {
//                 'Authorization': `Bearer ${token}`
//             }
//         });
//         const result = await response.json();
//         const data = result.data;
//         console.log(data);
//
//         document.querySelector('h2').textContent = `Order #${data.orderId}`;
//         document.getElementById('item-count').textContent = `You have ${data.orderDetails.length} item(s) in your order`;
//         document.getElementById('name-order').textContent = `${data.user.userName}'s Order`;
//         document.getElementById("totalPrice").textContent = `${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(data.totalPrice)}`;
//         const cartItems = document.getElementById('cart-items');
//
//         // Fetch and display valid promotions
//         const validPromotions = await getAllPromotion();
//         const promotionSelect = document.getElementById('promotion');
//
//         if (promotionSelect) {
//             // Clear previous options
//             promotionSelect.innerHTML = '';
//
//             // Check if the order status is "New"
//             if (data.status === "New") {
//                 const defaultOption = document.createElement('option');
//                 defaultOption.value = '';
//                 defaultOption.textContent = 'Select a promotion';
//                 promotionSelect.appendChild(defaultOption);
//
//                 validPromotions.forEach(promotion => {
//                     const option = document.createElement('option');
//                     option.value = promotion.promotionId;
//                     option.textContent = promotion.promotionName;
//                     promotionSelect.appendChild(option);
//                 });
//
//             } else {
//                 promotionSelect.disabled = true;
//                 if (data.promotion) {
//                     const option = document.createElement('option');
//                     option.value = data.promotion.promotionId;
//                     option.textContent = data.promotion.promotionName;
//                     promotionSelect.appendChild(option);
//                 } else {
//                     const option = document.createElement('option');
//                     option.value = '';
//                     option.textContent = 'No promotion applied';
//                     promotionSelect.appendChild(option);
//                 }
//             }
//         }
//
//         for (const detail of data.orderDetails) {
//             const product = detail.product;
//             const productDetails = await getProductDetail(product.productId);
//
//             const itemDiv = document.createElement('div');
//             itemDiv.classList.add('card', 'mb-3');
//
//             const sizeSelectId = `size-product-${detail.orderDetailId}`;
//             const priceId = `price-product-${detail.orderDetailId}`;
//
//             itemDiv.innerHTML = `
//             <div class="row g-0">
//                 <div class="col-md-4">
//                     <img src="${product.imageURL}" class="img-fluid rounded-start" alt="${product.productName}">
//                 </div>
//                 <div class="col-md-8">
//                     <div class="card-body">
//                         <h5 class="card-title">${product.productName}</h5>
//                         <p class="card-text">Quantity: ${detail.quantity}</p>
//                         <p class="card-text">Category: ${product.category.categoryName}</p>
//                         <p class="card-text">Warranty Period: ${product.warrantyPeriod} months</p>
//                         <p class="card-text">Price: <span id="${priceId}">${detail.totalPrice} VNĐ</span></p>
//                         <!-- Size Selector -->
//                         <div class="form-group">
//                             <label for="${sizeSelectId}">Select Size:</label>
//                             <select id="${sizeSelectId}" class="form-select size-selector">
//                             </select>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//             `;
//             cartItems.appendChild(itemDiv);
//
//             const sizeOfProduct = document.getElementById(sizeSelectId);
//             const priceElement = document.getElementById(priceId);
//
//             if (sizeOfProduct) {
//                 if (data.status === "New") {
//                     document.getElementById('address').disabled = false;
//                     document.getElementById('phone').disabled = false;
//
//
//                     const defaultOption = document.createElement('option');
//                     defaultOption.value = '';
//                     defaultOption.textContent = 'Select a size';
//                     sizeOfProduct.appendChild(defaultOption);
//
//                     productDetails.forEach(detail => {
//                         const option = document.createElement('option');
//                         option.value = detail.size;
//                         option.textContent = detail.size;
//                         sizeOfProduct.appendChild(option);
//                     });
//
//                     sizeOfProduct.addEventListener('change', async function () {
//                         const selectedSize = sizeOfProduct.value;
//                         const selectedDetail = await getProductDetailBySize(product, selectedSize);
//                         console.log(selectedDetail);
//                         if (selectedDetail) {
//                             // Format price in VND
//                             priceElement.textContent = `${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(selectedDetail.sellingPrice * detail.quantity)}`;
//                         }
//                     });
//                 } else {
//                     document.getElementById('address').value = `${data.shippingAddress}`;
//                     document.getElementById('phone').value = `${data.phone}`;
//
//                     sizeOfProduct.disabled = true;
//                     const option = document.createElement('option');
//                     option.value = detail.size;
//                     option.textContent = detail.size;
//                     sizeOfProduct.appendChild(option);
//
//                     const productDetail = await getProductDetailBySize(product, detail.size);
//                     // Format total price in VND
//                     priceElement.textContent = `${new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(productDetail.sellingPrice * detail.quantity)}`;
//                 }
//             }
//         }
//
//         if (data.status === "New") {
//             const buttonContainer = document.createElement('div');
//             buttonContainer.classList.add('button-container');
//
//             const confirmButton = document.createElement('button');
//             confirmButton.textContent = "Xác nhận";
//             confirmButton.classList.add('btn', 'btn-success');
//             confirmButton.onclick = async function () {
//                 try {
//                     const orderDetailsWithSizes = data.orderDetails.map(detail => {
//                         const sizeSelect = document.getElementById(`size-product-${detail.orderDetailId}`);
//                         const selectedSize = sizeSelect ? sizeSelect.value : detail.size;
//
//                         return {
//                             orderDetailId: detail.orderDetailId,
//                             product: {
//                                 productId: detail.product.productId
//                             },
//                             quantity: detail.quantity,
//                             size: selectedSize
//                         };
//                     });
//
//                     const selectedPromotion = document.getElementById('promotion') ? document.getElementById('promotion').value : null;
//                     const address = document.getElementById('address').value;
//                     const phone = document.getElementById('phone').value;
//
//                     console.log(address, phone);
//                     if (phone === null || address === null) {
//                         alert('Please enter a phone number and address');
//                         return false;
//                     }
//
//                     const confirmResponse = await fetch(`${window.base_url}/order-management/orders/${orderId}`, {
//                         method: 'PUT',
//                         headers: {
//                             'Authorization': `Bearer ${token}`,
//                             'Content-Type': 'application/json'
//                         },
//                         body: JSON.stringify({
//                             user: {
//                                 userId: data.user.userId
//                             },
//                             cart: {
//                                 cartId: data.user.userId
//                             },
//                             orderDetails: orderDetailsWithSizes,
//                             promotion: {
//                                 promotionId: selectedPromotion
//                             },
//                             startDate: data.startDate,
//                             phone: phone,
//                             shippingAddress: address,
//                             status: "Đã xác nhận"
//                         })
//                     });
//
//                     if (confirmResponse.ok) {
//                         const result = await confirmResponse.json();
//                         console.log(result);
//                         alert(`Order #${orderId} has been confirmed!`);
//                         location.reload();
//                     } else {
//                         alert(`Failed to confirm order #${orderId}.`);
//                     }
//                 } catch (error) {
//                     console.error('Error confirming the order:', error);
//                     alert('There was an error confirming the order.');
//                 }
//             };
//
//             // Hủy button
//             const cancelButton = document.createElement('button');
//             cancelButton.textContent = "Hủy";
//             cancelButton.classList.add('btn', 'btn-danger');
//             cancelButton.onclick = () => {
//                 window.location.href = "/DiamondShopSystem/src/main/resources/templates/User/info.html";
//             };
//
//             buttonContainer.appendChild(confirmButton);
//             buttonContainer.appendChild(cancelButton);
//
//             document.querySelector('.container').appendChild(buttonContainer);
//         }
//     } catch (error) {
//         console.error('Error fetching order details:', error);
//     }
// });
//
// async function getProductDetail(productId) {
//     try {
//         const response = await fetch(`${window.base_url}/product-management/products/${productId}/productdetails`, {
//             method: 'GET'
//         });
//         const result = await response.json();
//         return result.data;
//     } catch (error) {
//         console.error(`Error fetching product details for productId ${productId}:`, error);
//         return [];
//     }
// }
//
// async function getAllPromotion() {
//     try {
//         const response = await fetch(`${window.base_url}/promotion-management/promotions`, {
//             method: 'GET',
//             headers: {
//                 'Authorization': `Bearer ${localStorage.getItem('authToken')}`
//             }
//         });
//
//         if (response.status === 401) {
//             console.error('Unauthorized request. Please check your token.');
//             return [];
//         }
//
//         const result = await response.json();
//         console.log(result);
//         const currentDate = new Date();
//
//         return result.data.filter(promotion => {
//             const endDate = new Date(promotion.endDate);
//             return endDate >= currentDate;
//         });
//     } catch (error) {
//         console.error('Error fetching promotions:', error);
//         return [];
//     }
// }
//
// async function getProductDetailBySize(product, size) {
//     try {
//         const response = await fetch(`${window.base_url}/product-management/productDetailSize/${size}`, {
//             method: 'POST', // Change to POST
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({
//                 productId: product.productId
//             })
//         });
//
//         if (!response.ok) {
//             throw new Error('Error fetching product detail by size');
//         }
//
//         const result = await response.json();
//         return result.data;
//     } catch (error) {
//         console.error(`Error fetching product detail by size for productId ${product.productId}:`, error);
//         return null;
//     }
// }
//