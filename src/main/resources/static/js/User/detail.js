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
                sizeButtonsContainer.innerHTML = '<h3>Chọn Kích Thước (Giá sẽ thay đổi theo size)</h3>'; // Clear existing buttons

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
                document.getElementById('category').textContent = product.category.categoryName || "Không Xác Định";
                document.getElementById('img').src = product.imageURL || "/static/images/default.png";
                document.getElementById('imgsub').src = product.subImageURL || "/static/images/default.png";

                // Tự động chọn kích thước đầu tiên
                if (data.length > 0) {
                    document.getElementById('size_1_button').click();
                }

            })
            .catch(error => {
                console.error('Error fetching product details:', error);
                alert("Sản Phẩm Hiện Chưa Được Cập Nhật");
                window.location.href = "/DiamondShopSystem/src/main/resources/templates/User/products.html";
            });
    } else {
        console.log('No productId found in localStorage');
    }

    function updatePrice(sizeDetail) {
        sizeNow = sizeDetail.size;

        document.getElementById('product_name').textContent =
            sizeDetail.product.productName + " ( size hiện tại: " + sizeNow + " ) "|| "Không Xác Định";

        document.getElementById('warranty').textContent = sizeDetail.product.warrantyPeriod;
        document.getElementById('inventory').textContent = sizeDetail.inventory;
        document.getElementById('labor').textContent = sizeDetail.laborCost;
        // Hiển thị giá sản phẩm
        document.querySelector('.current-price').textContent = sizeDetail.sellingPrice.toLocaleString('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).replace('₫', 'vnđ') || "Không Xác Định";

        document.getElementById('setiing').textContent = sizeDetail.setting.material || "Không Xác Định";

        const diamonds = sizeDetail.diamondDetails;
        const table = document.querySelector('table');

        document.querySelectorAll('.sub-diamond').forEach(row => row.remove());

        diamonds.forEach(diamondIndex => {
            const product = diamondIndex.diamond.product
            document.getElementById('name').textContent = product.warrantyPeriod;
            console.log(diamondIndex.diamond.product.warrantyPeriod);
            if (diamondIndex.typeDiamond === "1") {

                document.getElementById('carat').textContent = diamondIndex.diamond.carat;
            } else if (diamondIndex.typeDiamond === "0") {
                let subDiamondRow = document.createElement('tr');
                subDiamondRow.classList.add('sub-diamond');
                let subDiamondName = document.createElement('td');
                subDiamondName.textContent = 'Tên Kim Cương Phụ';
                let subDiamondValue = document.createElement('td');
                subDiamondValue.textContent = diamondIndex.diamond.diamondName || "Không xác định";
                subDiamondRow.appendChild(subDiamondName);
                subDiamondRow.appendChild(subDiamondValue);

                let subQuantityRow = document.createElement('tr');
                subQuantityRow.classList.add('sub-diamond');
                let subQuantityLabel = document.createElement('td');
                subQuantityLabel.textContent = 'Số Lượng Kim Cương Phụ';
                let subQuantityValue = document.createElement('td');
                subQuantityValue.textContent = diamondIndex.quantity || "Không xác định";
                subQuantityRow.appendChild(subQuantityLabel);
                subQuantityRow.appendChild(subQuantityValue);

                masterRow.after(subDiamondRow, subQuantityRow);
                masterRow = subQuantityRow;
            }
        });
    }

    document.getElementById('add-to-cart').addEventListener('click', () => {
        if (!sizeNow) {
            alert('Vui lòng chọn kích thước trước khi thêm vào giỏ hàng.');
            return;
        }
        const formItem = {
            quantity: 1,
            product: {
                productId: productId
            }
        }
        console.log(formItem);

        fetch(`${window.base_url}/cart-management/cart-items`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(formItem)
        })
            .then(response => response.json()
            )
            .then(result => {
                if(result.message === "Item added successfully"){
                    alert(result.message);
                    window.location.href = "/DiamondShopSystem/src/main/resources/templates/User/cart.html";
                }
                else {
                    alert(result.message);
                    location.reload();
                }
            })
            .catch(error => {
                console.error('Error adding product to cart:', error);
            });
    });
});