document.addEventListener('DOMContentLoaded', () => {
    // Danh Mục Hình Ảnh
    const categoryImages = {
        "NONE": "/static/images/TrangSucKC.png",
        "NHẪN KIM CƯƠNG": "/static/images/NHAN-KIM-CUONG-TU-NHIEN.png",
        "NHẪN KIM CƯƠNG NAM": "/static/images/Nhẫn_Nam.png",
        "NHẪN KIM CƯƠNG NỮ": "/static/images/NHAN-KIM-CUONG-NU-qq4mh7dqwwqfbwiobx4vm2j4kgef7s87on8dkal568 1.png",
        "BÔNG TAI KIM CƯƠNG": "/static/images/BONG-TAI-KIM-CUONG-TU-NHIEN-qq4mgmpaqjy48hcpoo7337qzhz8cify49svp07fsz4%201.png",
        "LẮC/VÒNG KIM CƯƠNG": "/static/images/VONG-TAY-KCTN-qq4mhewgfl0pws7r40dw60mtbjdcxd22dog9ei9zsg%201.png",
        "MẶT DÂY CHUYỀN KIM CƯƠNG": "/static/images/DAY-CHUYEN-KIM-CUONG-TU-NHIEN-qq4mgv5ug29p4z0fb9uq7nm4ug2nfpvpayr2bp39f4%201.png",
        "DEFAULT": "/static/images/default-image.jpg"
    };

    const updateContentAndFetchProducts = (category, title) => {
        //console.log("category: ",category);

        // Cap Nhat Tieu De (h2 id = content-product>
        const contentProduct = document.getElementById('content-product');
        if (contentProduct) {
            contentProduct.textContent = title;
        } else {
            console.error('Element with ID content-product not found');
        }

        const wrapperImage = document.querySelector('.wrapper img');
        if (wrapperImage) {
            const formattedCategory = category.toUpperCase(); // Chuyển category thành chữ in hoa
            wrapperImage.src = categoryImages[formattedCategory] || categoryImages['DEFAULT'];

            console.log(wrapperImage);
            if(category == "None"){
                categoryIndex = "Kim Cương";
            }
            else {
                categoryIndex = category;
            }
            //console.log(categoryIndex);
        } else {
            console.error('Wrapper image element not found');
        }

        const productList = document.getElementById('product-list');

        productList.innerHTML = '';

        // fetch API
        fetch(`http://localhost:8080/product-management/products/category/${categoryIndex}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(responseData => {
                if (!responseData || !responseData.data || !Array.isArray(responseData.data)) {
                    console.error('Expected an array of products but received:', responseData);
                    throw new Error('Expected an array of products');
                }

                const products = responseData.data;

                if (products.length === 0) {
                    const emptyMessage = document.createElement('li');
                    emptyMessage.textContent = 'Không có sản phẩm nào trong danh mục này.';
                    productList.appendChild(emptyMessage);
                } else {
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

                        // Tim Min price
                        fetchMinPrice(product.productId);
                    });
                }
            })
            .catch(error => {
                console.error('Error fetching products:', error);
                const errorMessage = document.createElement('li');
                productList.appendChild(errorMessage);
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

    // event select
    const categorySelector = document.getElementById('category-selector');
    categorySelector.addEventListener('change', (event) => {
        const selectedOption = event.target.options[event.target.selectedIndex];
        const category = selectedOption.value;
        const title = selectedOption.getAttribute('data-title');
        updateContentAndFetchProducts(category, title);
    });

    // fetch default
    updateContentAndFetchProducts("None", 'Trang sức kim cương tự nhiên');
});
