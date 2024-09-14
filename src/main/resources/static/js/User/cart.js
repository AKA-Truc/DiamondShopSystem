document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('authToken');
    const user = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

    // Lấy danh sách sản phẩm trong giỏ hàng
    fetch(`${window.base_url}/cart-management/cart-items/${user.userId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;
            const tableBody = document.getElementById('list-cart-item');
            tableBody.innerHTML = '';

            if (data.length === 0) {
                const emptyRow = document.createElement('tr');
                emptyRow.innerHTML = `
                    <td colspan="6" style="text-align: center; font-size: 18px; color: #888;">Không Có Gì Trong Giỏ Hàng Của Bạn</td>
                `;
                tableBody.appendChild(emptyRow);
            } else {
                data.forEach(cartItem => {
                    const Row = document.createElement('tr');

                    Row.innerHTML = `
                        <td style="text-align: center;">
                            <input type="checkbox" class="cart-item-checkbox" data-cart-item-id="${cartItem.cartItemId}">
                        </td>
                        <td style="text-align: center">
                            <div class="product-info">
                                <img src="${cartItem.product.imageURL}" alt="Nhẫn Kim Cương" class="product-image">
                                <span>${cartItem.product.productName}</span>
                            </div>
                        </td>
                        <td style="text-align: center" data-product-id="${cartItem.product.productId}">${cartItem.product.productId}</td>
                        <td style="text-align: center">${cartItem.product.category.categoryName}</td>
                        <td style="text-align: center; vertical-align: middle;">
                            <input type="number" value="${cartItem.quantity}" class="quantity-input" data-cart-item-id="${cartItem.cartItemId}">
                        </td>
                        <td style="text-align: center">
                            <button class="remove-btn" style="font-size: 24px; color: black;" onclick="deleteCartItem(${cartItem.cartItemId})">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    `;
                    tableBody.append(Row);
                });
            }
        })
        .catch(error => {
            console.log(error);
        });

    // Checkbox "Chọn tất cả"
    document.getElementById('select-all').addEventListener('change', function () {
        const isChecked = this.checked;
        document.querySelectorAll('.cart-item-checkbox').forEach(checkbox => {
            checkbox.checked = isChecked;
        });
    });
    // Hàm xử lý khi ấn nút "Đặt hàng"
    document.querySelector('.order-btn').addEventListener('click', function () {
        const selectedItems = [];

        document.querySelectorAll('.cart-item-checkbox:checked').forEach(checkbox => {
            const row = checkbox.closest('tr');
            const productId = row.querySelector('[data-product-id]').getAttribute('data-product-id');
            const quantity = row.querySelector('.quantity-input').value;

            // Lưu thông tin sản phẩm được chọn vào mảng orderDetails
            selectedItems.push({
                product: {
                    productId: parseInt(productId)
                },
                quantity: parseInt(quantity)
            });
        });

        if (selectedItems.length > 0) {
            const orderData = {
                user: {
                    userId: user.userId // Lấy userId từ token đã được giải mã
                },
                cart: {
                    cartId: user.userId
                },
                orderDetails: selectedItems,
                startDate: new Date().toISOString().split('T')[0]
            };

            console.log('Thông tin đơn hàng đã chọn:', orderData);

            // Gửi dữ liệu đến server (nếu cần)
            fetch(`${window.base_url}/order-management/orders`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(orderData)
            })
                .then(response => response.json())
                .then(result => {
                    console.log(result);
                    // Xử lý khi đơn hàng thành công
                    alert('Đặt hàng thành công!');
                    window.location.href = "/DiamondShopSystem/src/main/resources/templates/User/products.html";
                })
                .catch(error => {
                    console.log('Có lỗi xảy ra:', error);
                    alert('Có lỗi xảy ra khi đặt hàng.');
                });
        } else {
            alert('Vui lòng chọn ít nhất một sản phẩm để đặt hàng.');
        }
    });

});

// Hàm xóa sản phẩm khỏi giỏ hàng
function deleteCartItem(id) {
    const token = localStorage.getItem('authToken');
    if (confirm("Xác nhận Xóa Sản Phẩm Này Khỏi Giỏ Hàng Của Bạn")) {
        fetch(`${window.base_url}/cart-management/cart-items/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("Xóa sản phẩm thất bại");
                }
            })
            .then(result => {
                console.log(result.message);
                location.reload();
            })
            .catch(error => {
                console.log('Có lỗi xảy ra:', error);
            });
    }
}

// Cập nhật số lượng của tất cả các cart items
function updateQuantityAllCartItem() {
    document.querySelectorAll('.quantity-input').forEach(input => {
        const cartItemId = input.getAttribute('data-cart-item-id');
        const productId = input.closest('tr').querySelector('[data-product-id]').getAttribute('data-product-id');
        const quantity = input.value;

        let formUpdate = {
            quantity: quantity,
            product: {
                productId: productId
            }
        };
        update(cartItemId, formUpdate);
    });
    location.reload();
}

// Hàm update sản phẩm
function update(id, data) {
    const token = localStorage.getItem('authToken');

    fetch(`${window.base_url}/cart-management/cart-items/${id}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .catch(error => console.log(error));
}
