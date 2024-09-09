document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('authToken');
    const productId = localStorage.getItem('productId');
    let sizeNow;

    if (productId) {
        fetch(`${window.base_url}/product-management/products/${productId}/productdetails`, {
            method: 'GET'
        })
            .then(response => response.json())
            .then(result => {
                const data = result.data;

                const sizeButtonsContainer = document.querySelector('.size-buttons');
                sizeButtonsContainer.innerHTML = '<h3>Chọn Kích Thước</h3>'; // Clear existing buttons

                data.forEach((sizeDetail, index) => {
                    const button = document.createElement('button');
                    button.className = 'size-button';
                    button.id = `size_${index + 1}_button`;
                    button.textContent = `${sizeDetail.size || 'N/A'}`;
                    button.addEventListener('click', () => {
                        updatePrice(sizeDetail);
                        sizeDetail.selectedSize = sizeDetail.size;
                    });
                    sizeButtonsContainer.appendChild(button);
                });

                console.log(data);

                const product = data[0].product;
                // Gán giá trị productName vào trường HTML
                document.getElementById('product_name').textContent = product.productName || "Không Xác Định";
                document.getElementById('category').textContent = product.category.categoryName || "Không Xác Định";
                document.getElementById('img').src = product.imageURL || "/static/images/default.png";
                document.getElementById('imgsub').src = product.subImageURL || "/static/images/default.png";

            })
            .catch(error => {
                console.error('Error fetching product details:', error);
            });
    } else {
        console.log('No productId found in localStorage');
    }

    // // Xử lý sự kiện khi nhấn vào nút "THÊM VÀO GIỎ HÀNG"
    // document.getElementById('add-to-cart').addEventListener('click', () => {
    //     if (!sizeNow) {
    //         alert('Vui lòng chọn kích thước trước khi thêm vào giỏ hàng.');
    //         return;
    //     }
    //
    //     fetch(`${window.base_url}/cart-management`, {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json',
    //             'Authorization': `Bearer ${token}`
    //         },
    //         body:
    //     })
    //         .then(response => response.json())
    //         .then(result => {
    //             if (result.success) {
    //                 alert('Sản phẩm đã được thêm vào giỏ hàng.');
    //             } else {
    //                 alert('Đã xảy ra lỗi khi thêm sản phẩm vào giỏ hàng.');
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error adding product to cart:', error);
    //         });
    // });

    function updatePrice(sizeDetail) {
        sizeNow = sizeDetail.size;
        // Giới thiệu giá
        document.querySelector('.current-price').textContent = sizeDetail.sellingPrice.toLocaleString('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).replace('₫', 'vnđ') || "Không Xác Định";

        // Gán giá trị vào trường HTML
        document.getElementById('setiing').textContent = sizeDetail.setting.material || "Không Xác Định";
    }

});

