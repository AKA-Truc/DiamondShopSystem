document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('authToken');
    const user = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

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
            console.log(data);

            const tableBody = document.getElementById('list-cart-item');
            tableBody.innerHTML = '';

            if (data.length === 0) {
                // Nếu không có sản phẩm trong giỏ hàng, hiển thị thông báo
                const emptyRow = document.createElement('tr');
                emptyRow.innerHTML = `
                    <td colspan="5" style="text-align: center; font-size: 18px; color: #888;">Không Có Gì Trong Giỏ Hàng Của Bạn</td>
                `;
                tableBody.appendChild(emptyRow);
            } else {
                data.forEach(cartItem => {
                    const Row = document.createElement('tr');

                    Row.innerHTML = `
                        <td><button class="remove-btn" style="font-size: 50px" onclick="deleteCartItem(${cartItem.cartItemId})">×</button></td>
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
                    `;
                    tableBody.append(Row);
                });
            }
        })
        .catch(error => {
            console.log(error);
        });
});


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
                    return response.json(); // Parse JSON nếu phản hồi thành công
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
    } else {
        location.reload();
    }
}


