document.addEventListener('DOMContentLoaded', () => {
    const fetch_Female_Diamond = () => {
        const keyword = "Nhẫn%20Nam";
        fetch(`http://localhost:8080/product-management/products/category/${keyword}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(responseData => {
                console.log("Raw response data fetched: ", responseData);

                // Kiểm tra cấu trúc dữ liệu trả về
                if (!responseData || !responseData.data || !Array.isArray(responseData.data)) {
                    console.error('Expected an object with a "data" array but received:', responseData);
                    throw new Error('Expected an array of products');
                }

                const products = responseData.data;

                let productList = document.getElementById('product-list');

                if (!productList) {
                    productList = document.createElement('ul');
                    productList.id = 'product-list';
                    document.body.appendChild(productList);
                }


                products.forEach(product => {
                    const listItem = document.createElement('li');
                    listItem.innerHTML = `
                        <div class="product-item">
                            <div class="product-top">
                                <a href="#" class="product-thumb">
                                    <div class="card">
                                        <img src="/static/images/${product.imageURL || 'default.jpg'}" alt="font">
                                        <img src="/static/images/${product.subImageURL || 'default.jpg'}" alt="back">
                                    </div>
                                </a>
                                <a href="#" class="buy-now">Xem chi tiết</a>
                            </div>
                            <div class="product-info">
                                <a href="#" class="product-name">${product.productName || 'Tên sản phẩm'}</a>
                                <a href="#" class="product-cart">${product.productId || 'Mã sản phẩm'}</a>
                                <div class="product-price">
                                    <!-- Thêm giá nếu có thông tin giá trong sản phẩm -->
                                    <!-- <del>${product.oldPrice || '0 đ'}</del> ${product.newPrice || '0 đ'} -->
                                </div>
                            </div>
                        </div>
                    `;
                    productList.appendChild(listItem);
                });
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }

    fetch_Female_Diamond();
});
