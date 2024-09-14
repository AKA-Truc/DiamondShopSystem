document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('authToken');
    const isedit = localStorage.getItem('edit');
    const addButton = document.getElementById('add-container');
    const detailButton = document.getElementById('detail-container');
    const checkAllBox = document.querySelector('.table-checkAll');
    const closeModalBtn = document.getElementById('close-modal');
    const tableBodyadd = document.getElementById('body-add');
    const tableBodydetail = document.getElementById('body-detail');
    const modal = document.getElementById('diamond-modal');
    const searchInput = document.getElementById('diamond-search');
    const diamondList = document.getElementById('diamond-list');
    const searchButton = document.getElementById('search-diamond-btn');
    let currentDiamondSelect = null;



    if(!isedit){
        window.location.href = "/DiamondShopSystem/src/main/resources/templates/Admin/product.html";
    }else {
        displayProduct();
    }

    document.getElementById('update-product').addEventListener('click',handleSubmit)
    document.getElementById('add-productdetail').addEventListener('click',addDetail)


    function displayProduct() {
        fetchProduct().then(data => {
            console.log(data);

            // Hiển thị thông tin sản phẩm
            document.getElementById('previewImg').src = data.imageURL;
            document.getElementById('previewImg1').src = data.subImageURL;
            document.getElementById('Name').value = data.productName;
            document.getElementById('loaiSanPham').value = data.category.categoryId;
            document.getElementById('Warranty').value = data.warrantyPeriod;

            const productId = localStorage.getItem('edit');

            // Fetch product details
            fetch(`${window.base_url}/product-management/products/${productId}/productdetails`, { method: 'GET' })
                .then(response => response.json())
                .then(result => {
                    const productDetails = result.data;

                    if (productDetails.length > 0) {
                        // Lấy bảng và reset nội dung bảng
                        const tableDetail = document.getElementById('body-detail');
                        tableDetail.innerHTML = ''; // Xóa các hàng cũ nếu có

                        // Tạo các hàng mới cho từng product detail
                        productDetails.forEach(detail => {
                            const newRow = document.createElement('tr');
                            newRow.innerHTML = `
                            <td>
                                <select class="table-control item-control list-setting">
                                    <option value="${detail.setting.settingId}">${detail.setting.material}</option>
                                </select>
                            </td>
                            <td>
                                <div class="item-container" id="diamond-select-${detail.setting.settingId}">
                                </div>
                            </td>
                            <td><input type="number" class="table-control item-control" placeholder="Nhập size" value="${detail.size}" required></td>
                            <td><input type="number" class="table-control item-control" placeholder="Nhập tỉ lệ áp giá" value="${detail.markupRate}" required></td>
                            <td><input type="number" class="table-control item-control" placeholder="Nhập tiền gia công" value="${detail.laborCost}" required></td>
                            <td><input type="number" class="table-control item-control" placeholder="Nhập số lượng tồn kho" value="${detail.inventory}" required></td>
                            <td>
                                <ul class="table-action">
                                    <li class="table-action-control row-delete"><i class="fa-solid fa-trash" aria-hidden="true"></i></li>
                                </ul>
                            </td>
                        `;

                            // Thêm sự kiện vào nút xóa và nút thêm kim cương
                            localStorage.setItem('editdetail', detail.productDetailId);
                            newRow.querySelector('.row-delete').addEventListener('click', handleDeleteDetail);
                            // Gọi hàm fetchSetting để điền các tùy chọn cho select (nếu cần)
                            fetchSetting(token, newRow.querySelector('.list-setting'));

                            const diamondSelectContainer = newRow.querySelector(`#diamond-select-${detail.setting.settingId}`);
                            detail.diamondDetails.forEach(diamondDetail => {
                                const diamondDiv = document.createElement('div');
                                diamondDiv.innerHTML = `
                                <input class="table-control item-control list-diamond" disabled value="${diamondDetail.diamond.giacode}" data-diamond-id="${diamondDetail.diamond.diamondId}"" disabled>
                                <select class="table-control item-control type-diamond" disabled>
                                    <<option value="0" ${diamondDetail.typeDiamond === '0' ? 'selected' : ''}>Kim Cương Chủ (1 viên)</option>
                                    <option value="1" ${diamondDetail.typeDiamond === '1' ? 'selected' : ''}>Kim Cương Phụ</option>
                                </select>
                                <input type="number" class="table-control item-control quantity-input" value="${diamondDetail.quantity}" disabled placeholder="Số lượng">
                            `;
                                diamondSelectContainer.appendChild(diamondDiv);

                            });

                            // Thêm hàng mới vào bảng
                            tableDetail.appendChild(newRow);
                        });
                    } else {
                        console.log('No product details found.');
                    }
                })
                .catch(err => console.error('Error fetching product details:', err));
        }).catch(err => {
            console.error('Error displaying product:', err);
        });
    }

    fetchCategory(token);

    if (checkAllBox) {
        checkAllBox.addEventListener('change', handleCheckAllChange);
    }

    if (addButton) {
        addButton.addEventListener('click', handleAddButtonClickAdd);
    }

    if (detailButton){
        detailButton.addEventListener("click", handleAddButtonClickDetail);
    }

    if (searchButton) {
        searchButton.addEventListener('click', handleSearchButtonClick);
    }

    initializeExistingRows();

    function handleAddButtonClickAdd() {
        const newRow = createTableRowAdd();
        tableBodyadd.appendChild(newRow);
    }
    function handleAddButtonClickDetail() {
        const newRow = createTableRowDetail();
        tableBodydetail.appendChild(newRow);
    }

    function createTableRowAdd() {
        const newRow = document.createElement('tr');
        newRow.innerHTML = `
            <td class="text-center check-col"><input type="checkbox" class="table-checkbox"></td>
            <td>
                <select class="table-control item-control list-setting"></select>
            </td>
            <td>
                <div class="item-container">
                    <div id = "diamond-select"></div>
                </div>
                <span class="toggle-quantity">+</span>
            </td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập size" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập tỉ lệ áp giá" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập tiền gia công" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập số lượng tồn kho" required></td>
            <td>
                <ul class="table-action">
                    <li class="table-action-control row-delete"><i class="fa-solid fa-trash" aria-hidden="true"></i></li>
                </ul>
            </td>
        `;

        newRow.querySelector('.row-delete').addEventListener('click', handleDeleteRow);
        newRow.querySelector('.toggle-quantity').addEventListener('click', handleToggleQuantity);
        fetchSetting(token, newRow.querySelector('.list-setting'));

        return newRow;
    }
    function createTableRowDetail() {
        const newRow = document.createElement('tr');
        newRow.innerHTML = `
            <td>
                <select class="table-control item-control list-setting"></select>
            </td>
            <td>
                <div class="item-container">
                    <div id = "diamond-select"></div>
                </div>
                <button class="toggle-quantity">+</button>
            </td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập size" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập tỉ lệ áp giá" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập tiền gia công" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập số lượng tồn kho" required></td>
            <td>
                <ul class="table-action">
                    <li class="table-action-control row-save "><i class="fa-solid fa-check" aria-hidden="true"></i></li>
                    <li class="table-action-control row-delete"><i class="fa-solid fa-trash" aria-hidden="true"></i></li>
                </ul>
            </td>
            `;

        newRow.querySelector('.row-delete').addEventListener('click', handleDeleteRow);
        newRow.querySelector('.toggle-quantity').addEventListener('click', handleToggleQuantity);
        fetchSetting(token, newRow.querySelector('.list-setting'));

        return newRow;
    }

    function handleCheckAllChange(e) {
        const isChecked = e.target.checked;
        tableBodyadd.querySelectorAll('.table-checkbox').forEach(checkbox => {
            checkbox.checked = isChecked;
        });
    }

    function handleToggleQuantity(e) {
        currentDiamondSelect = e.target.previousElementSibling;
        modal.style.display = 'block';
        searchInput.focus();
        fetchDiamondList();
    }

    closeModalBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    function fetchDiamondList() {
        const giaCode = searchInput.value.trim();
        if (!giaCode) {
            return;
        }

        fetch(`${window.base_url}/diamond-management/diamondGIACODE/${giaCode}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text);
                    });
                }
                return response.json();
            })
            .then(result => {
                const data = result.data;
                diamondList.innerHTML = '';

                if (!data) {
                    diamondList.innerHTML = 'Không có kim cương hợp lệ.';
                } else {
                    // Create a list item with all the required details
                    const listItem = document.createElement('li');
                    listItem.innerHTML = `
                <span><strong>GIA Code:</strong> ${data.giacode}</span>
                <span><strong>Hình Dạng:</strong> ${data.shape}</span>
                <span><strong>Màu:</strong> ${data.color}</span>
                <span><strong>Giác Cắt:</strong> ${data.cut}</span>
                <span><strong>Khối Lượng:</strong> ${data.carat}</span>
                <span><strong>Kích Thước:</strong> ${data.measurement}</span>
                <span><strong>Độ Tinh Khiết:</strong> ${data.clarity}</span>
                <button>Chọn</button>
            `;

                    listItem.dataset.diamondId = data.diamondId;

                    // Add event listener to the "Chọn" button
                    listItem.querySelector('button').addEventListener('click', () => {
                        selectDiamond(data);
                        document.getElementById('diamond-modal').style.display = 'none';
                        searchInput.value = '';
                        diamondList.innerHTML = '';
                    });

                    diamondList.appendChild(listItem);
                }
            })
            .catch(error => {
                console.error('Error fetching diamonds:', error);
                diamondList.innerHTML = 'Lỗi khi lấy thông tin kim cương.';
            });
    }

    function selectDiamond(diamond) {
        const newFieldContainer = document.createElement("div");
        newFieldContainer.classList.add("item-container");
        newFieldContainer.innerHTML = `
        <input class="table-control item-control list-diamond" value="${diamond.giacode}" data-diamond-id="${diamond.diamondId}"" disabled>
        <select class="table-control item-control type-diamond">
            <option value="1">Kim Cương Phụ</option>
            <option value="0">Kim Cương Chủ (1 viên)</option>
        </select>
        <input type="number" class="table-control item-control quantity-input" placeholder="Nhập số lượng">
        <button class="remove-item-btn">-</button> <!-- Nút xóa thêm vào đây -->
    `;

        // Chèn `newFieldContainer` vào vị trí hiện tại của `currentDiamondSelect`
        currentDiamondSelect.parentNode.insertBefore(newFieldContainer, currentDiamondSelect.nextElementSibling);

        const typeDiamondSelect = newFieldContainer.querySelector('.type-diamond');

        // Event khi thay đổi loại kim cương
        typeDiamondSelect.addEventListener('change', () => {
            updateDiamondTypes(typeDiamondSelect);
        });

        // Khởi tạo sự kiện cho các select đã có sẵn
        updateDiamondTypes(typeDiamondSelect);

        // Thêm sự kiện cho nút xóa
        newFieldContainer.querySelector('.remove-item-btn').addEventListener('click', () => {
            newFieldContainer.remove();
        });
    }

    function updateDiamondTypes(currentSelect) {
        const allTypeSelects = document.querySelectorAll('.type-diamond');
        const row = currentSelect.closest('tr');
        const diamonds = row.querySelectorAll('.list-diamond');
        const quantities = row.querySelectorAll('.quantity-input');
        const diamondTypes = row.querySelectorAll('.type-diamond');
        let mainDiamondSelected = false;

        // Check if there's at least one "Kim Cương Chủ" in the current row
        diamondTypes.forEach((select, index) => {
            const quantityInput = quantities[index];

            if (select.value === '0') {
                mainDiamondSelected = true;
                quantityInput.value = '1';
                quantityInput.disabled = true;
            } else {
                quantityInput.disabled = false;
            }
        });

        // Update all type select elements in the current row based on the presence of a main diamond
        diamondTypes.forEach(select => {
            const option = select.querySelector('option[value="0"]');
            if (mainDiamondSelected && select.value !== '0') {
                option.disabled = true; // Disable the "Kim Cương Chủ" option if there's already one in the row
            } else {
                option.disabled = false; // Enable the "Kim Cương Chủ" option if no main diamond is selected
            }
        });

        // Update the row validity based on the presence of a main diamond
        updateRowValidity(row, mainDiamondSelected);
    }

    function updateRowValidity(row, hasMainDiamond) {
        // Find the action controls in the row
        const actionControls = row.querySelectorAll('.row-save, .row-delete');
        if (!hasMainDiamond) {
            // Disable action controls if there's no main diamond
            actionControls.forEach(control => control.classList.add('hidden'));
            row.classList.add('invalid');
        } else {
            // Enable action controls if there's at least one main diamond
            actionControls.forEach(control => control.classList.remove('hidden'));
            row.classList.remove('invalid');
        }
    }

    searchInput.addEventListener('input', (event) => {
        const filter = event.target.value.toLowerCase();
        const items = diamondList.querySelectorAll('li');

        items.forEach(item => {
            const text = item.textContent.toLowerCase();
            item.style.display = text.includes(filter) ? '' : 'none';
        });
    });

    function handleSearchButtonClick() {
        fetchDiamondList();
    }

    function handleDeleteRow(e) {
        if (confirm('Bạn có chắc chắn muốn xóa dòng này không?')) {
            e.target.closest('tr').remove();
        }
    }

    function handleDeleteDetail(e) {
        if (confirm('Bạn có chắc chắn muốn xóa chi tiết sản phẩm này không?')) {
            fetch(`${window.base_url}/product-management/productdetails/${localStorage.getItem('editdetail')}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(result => {
                    alert("Xóa Thành Công");
                    location.reload();
                })
                .catch(err => alert(err))
        }
    }

    function initializeExistingRows() {
        document.querySelectorAll('.row-delete').forEach(btn => {
            btn.addEventListener('click', handleDeleteRow);
        });

        document.querySelectorAll('.toggle-quantity').forEach(btn => {
            btn.addEventListener('click', handleToggleQuantity);
        });

        // Update diamond types for all rows
        document.querySelectorAll('table.h-table tbody tr').forEach(row => {
            const typeSelects = row.querySelectorAll('.type-diamond');
            typeSelects.forEach(select => {
                select.addEventListener('change', () => {
                    updateDiamondTypes(select);
                });
            });
            updateDiamondTypes(typeSelects[0]); // Initialize validation for each row
        });
    }

    function handleSubmit(e) {
        e.preventDefault();

        const productName = document.getElementById('Name').value;
        const warrantyPeriod = document.getElementById('Warranty').value;
        const categoryId = document.getElementById('loaiSanPham').value;

        if (!productName || !warrantyPeriod || !categoryId) {
            alert("Vui lòng nhập đầy đủ thông tin sản phẩm.");
            return;
        }

        const imageURL = document.getElementById('productImage').files[0];
        const subImageURL = document.getElementById('productImage1').files[0];

        const product = {
            productName,
            warrantyPeriod,
            category: {
                categoryId
            }
        };

        console.log(product);

        const formData = new FormData();
        formData.append('product', JSON.stringify(product));
        formData.append('imageURL', imageURL || null);
        formData.append('subImageURL', subImageURL);

        fetch(`${window.base_url}/product-management/products/${localStorage.getItem('edit')}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
            body: formData
        })
            .then(response => response.json())
            .then(result => {
                console.log("ok");
                location.reload();
            })
            .catch(err => console.error(err));
    }

    function addDetail() {
        let rows = document.querySelectorAll('table.h-table tbody tr');
        rows = Array.from(rows);

        const Detailsl = [];

        rows.forEach(row => {
            const checkBox = row.querySelector('.table-checkbox');
            if (checkBox && checkBox.checked) {

                const diamonds = row.querySelectorAll('.list-diamond');
                const quantities = row.querySelectorAll('.quantity-input');
                const diamondTypes = row.querySelectorAll('.type-diamond');

                const diamondMap = new Map();

                diamonds.forEach((diamondElement, index) => {
                    const diamondId = diamondElement.getAttribute('data-diamond-id');
                    const quantity = parseInt(quantities[index].value) || 0;
                    const diamondType = diamondTypes[index].value;

                    if (!diamondMap.has(diamondId)) {
                        diamondMap.set(diamondId, { quantity: 0, typeDiamond: diamondType });
                    }

                    if (diamondType === '1') {
                        diamondMap.get(diamondId).quantity = 1;
                    } else {
                        diamondMap.get(diamondId).quantity += quantity;
                    }
                });

                const diamondDetails = Array.from(diamondMap, ([diamondId, { quantity, typeDiamond }]) => ({
                    diamond: { diamondId },
                    quantity,
                    typeDiamond
                }));

                const rowData = {
                    product: {
                        productId: localStorage.getItem('edit')
                    },
                    setting: {
                        settingId: row.querySelector('.list-setting').value
                    },
                    diamondDetails: diamondDetails,
                    size: row.querySelector('input[placeholder="Nhập size"]').value,
                    markupRate: row.querySelector('input[placeholder="Nhập tỉ lệ áp giá"]').value,
                    laborCost: row.querySelector('input[placeholder="Nhập tiền gia công"]').value,
                    inventory: row.querySelector('input[placeholder="Nhập số lượng tồn kho"]').value
                };
                Detailsl.push(rowData);
            }
        });
        Detailsl.forEach(detail =>{
            addProductDetail(token, detail);
        })
        location.reload();
    }

    function addProductDetail(token, productdetail) {
        fetch(`${window.base_url}/product-management/productdetails`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productdetail)
        })
            .then(response => response.json())
            .then(result => {
                console.log("hahaha", result);
            })
            .catch(error => console.error('Error fetching categories:', error));
    }

    function fetchCategory(token) {
        const categorySelect = document.getElementById('loaiSanPham');

        fetch(`${window.base_url}/category-management/categories`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(result => {
                const data = result.data;

                if (!Array.isArray(data)) {
                    throw new Error("Category should be an array");
                }

                categorySelect.innerHTML = '';
                data.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.categoryId;
                    option.textContent = category.categoryName;
                    categorySelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching categories:', error));
    }

    function fetchSetting(token, selectElement) {
        if (!selectElement) {
            console.error('Select element is not defined.');
            return;
        }

        fetch(`${window.base_url}/setting-management/settings`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(result => {
                const data = result.data;

                if (!Array.isArray(data)) {
                    throw new Error("Settings should be an array.");
                }

                selectElement.innerHTML = '';
                data.forEach(setting => {
                    const option = document.createElement('option');
                    option.value = setting.settingId;
                    option.textContent = setting.material;
                    selectElement.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching settings:', error));
    }

    window.addEventListener('click', (event) => {
        if (event.target === document.getElementById('add-product-type')) {
            if(confirm("Xác Nhận Hủy?")){
                document.getElementById('add-product-type').style.display = 'none';
            }
        }
    });

});
function cancelForm() {
    if (confirm('Bạn có Chắc Hủy Bỏ Không?')) {
        window.location.href = "/DiamondShopSystem/src/main/resources/templates/Admin/product.html";
    }
}

function fetchProduct() {
    const productId = localStorage.getItem('edit');
    const productDetailsUrl = `${window.base_url}/product-management/products/${productId}/productdetails`;
    const productUrl = `${window.base_url}/product-management/products/${productId}`;

    return fetch(productUrl, { method: 'GET' })
        .then(response => response.json())
        .then(result => {
            const data = result.data;

            if (data.length === 0) {
                // If no product details found, fetch them
                return fetch(productDetailsUrl, { method: 'GET' })
                    .then(response => response.json())
                    .then(result => {
                        const ProductDetails = result.data;
                        return ProductDetails[0].product;  // Return the product from product details
                    })
                    .catch(err => console.error('Error fetching product details:', err));
            } else {
                return data;  // Return the product data
            }
        })
        .catch(err => console.error('Error fetching product:', err));
}
