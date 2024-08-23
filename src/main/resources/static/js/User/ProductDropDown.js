document.addEventListener('DOMContentLoaded', () => {
    //danh mục các link ảnh
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
        // Cập nhật tiêu đề khi cập nhật loại hàng
        const contentProduct = document.getElementById('content-product');
        if (contentProduct) {
            contentProduct.textContent = title;
        } else {
            console.error('Element with ID content-product not found');
        }

        // thay đổi hình ảnh khi thay đổi loại hàng
        const wrapperImage = document.querySelector('.wrapper img');
        if (wrapperImage) {
            const formattedCategory = category.toUpperCase();
            wrapperImage.src = categoryImages[formattedCategory] || categoryImages['DEFAULT'];

            //console.log(wrapperImage);
            //cập nhật lại tên loại hàng để đưa vào link
            if(category === "None"){
                categoryIndex = "Kim Cương";
            }
            else {
                categoryIndex = category;
            }
            //console.log(categoryIndex);
        } else {
            console.error('Wrapper image element not found');
        }

        // product-list là nơi in ra sản phẩm
        const productList = document.getElementById('product-list');
        productList.innerHTML = '';

        // Lấy giá trị min và max
        const priceSelector = document.getElementById('price-selector');
        let minPrice, maxPrice;
        const selectedPrice = priceSelector.value;

        // nếu là none thì min = 0 và max là giá trị lớn nhất của kiểu dữ liệu (giá trị mặc định)
        if (selectedPrice === "None") {
            minPrice = 0;
            maxPrice = Number.MAX_SAFE_INTEGER;
        } else {
            const [min, max] = selectedPrice.split('-');
            minPrice = parseFloat(min);
            maxPrice = max ? parseFloat(max) : Number.MAX_SAFE_INTEGER;
        }

        // Fetch API với các giá trị đã xử lý ở bên trên
        fetch(`http://localhost:8080/product-management/products/category/${categoryIndex}/${minPrice}/${maxPrice}`)
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
                                    <a href="/static/templates/User/detail.html/${product.productId}" class="buy-now">Xem chi tiết</a>
                                </div>
                                <div class="product-info">
                                    <a href="#" class="product-name">${product.productName || 'Tên sản phẩm'}</a>
                                    <div class="product-price" id="price-${product.productId}"></div>
                                </div>
                            </div>
                        `;
                        productList.appendChild(listItem);

                        // Tìm giá nhỏ nhất
                        fetchMinPrice(product.productId);
                    });
                }
            })
            .catch(error => {
                console.error('Error fetching products:', error);
                const errorMessage = document.createElement('li');
                errorMessage.textContent = "Không Tìm Thấy Sản Phẩm";
                productList.appendChild(errorMessage);
            });
    };

    //fetch để tìm giá nhỏ nhất của 1 sản phẩm nào đó
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

    //cập nhật giá trị của loại hàng khi thay đổi
    const categorySelector = document.getElementById('category-selector');
    categorySelector.addEventListener('change', (event) => {
        const selectedOption = event.target.options[event.target.selectedIndex];
        const category = selectedOption.value;
        const title = selectedOption.getAttribute('data-title');
        updateContentAndFetchProducts(category, title);
    });

    //Cập nhật giá trị max min khi thay đổi
    const priceSelector = document.getElementById('price-selector');
    priceSelector.addEventListener('change', () => {
        const selectedOption = categorySelector.options[categorySelector.selectedIndex];
        const category = selectedOption.value;
        const title = selectedOption.getAttribute('data-title');
        updateContentAndFetchProducts(category, title);
    });

    //mặc định giá trị none và min = 0, max là giá trị lớn nhất của kiểu dữ liệu
    updateContentAndFetchProducts("None", 'Trang sức kim cương tự nhiên');
});
