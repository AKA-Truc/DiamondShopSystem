document.addEventListener('DOMContentLoaded', () => {
    const categoryButtons = document.querySelectorAll('.category-btn');

    const fetchProductsByCategory = (keyword) => {
        fetch(`http://localhost:8080/product-management/products/category/${keyword}`)
            .then(response => response.json())
            .then(responseData => {
                if (!responseData || !responseData.data || !Array.isArray(responseData.data)) {
                    console.error('Expected an array of products but received:', responseData);
                    throw new Error('Expected an array of products');
                }

                const products = responseData.data;
                const productList = document.getElementById('product-list');

                // Clear existing products
                productList.innerHTML = '';

                products.forEach(product => {
                    const listItem = document.createElement('li');
                    listItem.innerHTML = `
                        <div class="product-item">
                            <div class="product-top">
                                <a href="#" class="product-thumb">
                                    <div class="card">
                                        <img src="${product.imageURL || 'https://via.placeholder.com/150'}" alt="font">
                                        <img src="${product.subImageURL || 'https://via.placeholder.com/150'}" alt="back">
                                    </div>
                                </a>
                                <a href="#" class="buy-now">Xem chi tiết</a>
                            </div>
                            <div class="product-info">
                                <a href="#" class="product-name">${product.productName || 'Tên sản phẩm'}</a>
                                <div class="product-price" id="price-${product.productId}"></div>
                            </div>
                        </div>
                    `;
                    productList.appendChild(listItem);

                    // Gọi hàm minPrice để lấy giá tối thiểu và hiển thị
                    fetchMinPrice(product.productId);
                });
            })
            .catch(error => {
                console.error('Error fetching products:', error);
            });
    };

    const fetchMinPrice = (id) => {
        fetch(`http://localhost:8080/product-management/productdetails/min/${id}`)
            .then(response => response.json())
            .then(responseData => {
                const priceElement = document.getElementById(`price-${id}`);
                if (priceElement && responseData.data && responseData.data.hasOwnProperty('sellingPrice')) {
                    const formattedPrice = responseData.data.sellingPrice.toLocaleString('vi-VN', {
                        style: 'currency',
                        currency: 'VND',
                    }).replace('₫', 'vnđ');

                    priceElement.innerHTML = formattedPrice;
                } else {
                    priceElement.innerHTML = 'Giá không có sẵn';
                }
            })
            .catch(error => {
                console.error('Error fetching product detail:', error);
            });
    };

    categoryButtons.forEach(button => {
        button.addEventListener('click', () => {
            const category = button.getAttribute('data-category');
            fetchProductsByCategory(category);
        });
    });

    fetchProductsByCategory('Bông Tai');
});
