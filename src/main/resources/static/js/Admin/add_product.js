document.addEventListener('DOMContentLoaded', () => {
    const addButton = document.querySelector('.table-add-btn');
    const checkAllBox = document.querySelector('.table-checkAll');
    const tableBody = document.querySelector('table.h-table tbody');
    const token = localStorage.getItem('authToken');
    const modal = document.getElementById('diamond-modal');
    const closeModalBtn = document.getElementById('close-modal');
    const searchInput = document.getElementById('diamond-search');
    const diamondList = document.getElementById('diamond-list');
    const searchButton = document.getElementById('search-diamond-btn');
    let currentDiamondSelect = null;

    closeModalBtn.addEventListener('click', () => {
        modal.style.display = 'none';
        searchInput.value = '';
        diamondList.innerHTML = '';
    });

    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
            searchInput.value = '';
            diamondList.innerHTML = '';
        }
    });

    fetchCategory(token);

    if (checkAllBox) {
        checkAllBox.addEventListener('change', handleCheckAllChange);
    }

    if (addButton) {
        addButton.addEventListener('click', handleAddButtonClick);
    }

    document.querySelector('.save').addEventListener('click', handleSubmit);

    if (searchButton) {
        searchButton.addEventListener('click', handleSearchButtonClick);
    }

    initializeExistingRows();

    function handleAddButtonClick() {
        const newRow = createTableRow();
        tableBody.appendChild(newRow);
    }

    function createTableRow() {
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
                <button class="toggle-quantity">+</button>
            </td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập size" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập tỉ lệ áp giá" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập tiền gia công" required></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập số lượng tồn kho" required></td>
            <td>
                <ul class="table-action">
                    <li class="table-action-control row-save hidden"><i class="fa-solid fa-check" aria-hidden="true"></i></li>
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
        tableBody.querySelectorAll('.table-checkbox').forEach(checkbox => {
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

        const imageURL = document.getElementById('image').files[0];
        const subImageURL = document.getElementById('image1').files[0];

        if (!imageURL || !subImageURL) {
            alert("Vui lòng tải lên đầy đủ cả hai ảnh sản phẩm.");
            return;
        }

        const product = {
            productName,
            warrantyPeriod,
            category: {
                categoryId
            }
        };

        const formData = new FormData();
        formData.append('product', JSON.stringify(product));
        formData.append('imageURL', imageURL);
        formData.append('subImageURL', subImageURL);

        let isValid = true;
        let hasMainDiamond = false;
        const rows = Array.from(document.querySelectorAll('table.h-table tbody tr'));

        rows.forEach(row => {
            const checkBox = row.querySelector('.table-checkbox');
            if (checkBox && checkBox.checked) {
                const settingId = row.querySelector('.list-setting').value;
                const size = parseFloat(row.querySelector('input[placeholder="Nhập size"]').value);
                const markupRate = parseFloat(row.querySelector('input[placeholder="Nhập tỉ lệ áp giá"]').value);
                const laborCost = parseFloat(row.querySelector('input[placeholder="Nhập tiền gia công"]').value);
                const inventory = parseFloat(row.querySelector('input[placeholder="Nhập số lượng tồn kho"]').value);

                if (!settingId || size <= 0 || markupRate <= 0 || laborCost <= 0 || inventory <= 0) {
                    alert("Vui lòng nhập thông tin hợp lệ cho các trường số (lớn hơn 0).");
                    isValid = false;
                    return;
                }

                const diamonds = row.querySelectorAll('.list-diamond');
                const quantities = row.querySelectorAll('.quantity-input');
                const diamondTypes = row.querySelectorAll('.type-diamond');

                diamonds.forEach((diamondElement, index) => {
                    const diamondId = diamondElement.value;
                    const quantity = parseInt(quantities[index].value) || 0;
                    const diamondType = diamondTypes[index].value;

                    if (!diamondId || quantity <= 0) {
                        alert("Vui lòng chọn kim cương và nhập số lượng hợp lệ.");
                        isValid = false;
                        return;
                    }

                    if (diamondType === '0') {
                        hasMainDiamond = true;
                    }
                });
            }
        });

        if (!hasMainDiamond) {
            alert("Phải có ít nhất một 'Kim Cương Chủ'.");
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        submitData(formData);
    }

    function submitData(formData) {
        const token = localStorage.getItem('authToken');
        fetch(`${window.base_url}/product-management/product`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
            body: formData
        })
            .then(response => response.json())
            .then(result => {
                const data = result.data;
                console.log('Product data submitted:', result);

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
                                productId: data.productId
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
                console.log(Detailsl);

                Detailsl.forEach(productdetail => {
                    console.log(productdetail);
                    addProductDetail(token, productdetail);
                });

                alert("Create product successfully");
                window.location.href = "/DiamondShopSystem/src/main/resources/templates/Admin/product.html";
            })
            .catch(error => {
                console.error('Error submitting product data:', error);
                alert("Fail to create product");
                // location.reload();
            });
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

function openProductTypeForm() {
    document.getElementById('add-product-type').style.display = 'flex';
}

function closeProductTypeForm() {
    if (confirm("Xác Nhận Hủy?")) {
        document.getElementById('add-product-type').style.display = 'none';
    }
}

function submitProductType() {
    const categoryName = document.getElementById('additional-product-name').value;
    const token = localStorage.getItem('authToken');

    fetch(`${window.base_url}/category-management/categories`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ categoryName: categoryName })
    })
        .then(response => {
            if (response.ok && response.headers.get('content-type')?.includes('application/json')) {
                return response.json();
            } else {
                throw new Error('Invalid response from server');
            }
        })
        .then(result => {
            const data = result.data;
            console.log(data);
            document.getElementById('add-product-type').style.display = 'none';
            location.reload();
        })
        .catch(error => {
            alert('Error add categories' + error);
            console.error('Error fetching categories:', error);
        });
}


function submitDiamondShell() {
    const material = document.getElementById('material').value;
    const price = document.getElementById('price').value;
    const token = localStorage.getItem('authToken');

    fetch(`${window.base_url}/setting-management/settings`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            material: material,
            price: price
        })
    })
        .then(response => {
            if (response.ok && response.headers.get('content-type')?.includes('application/json')) {
                return response.json();
            } else {
                throw new Error('Invalid response from server');
            }
        })
        .then(result => {
            const data = result.data;
            console.log(data);
            document.getElementById('add-diamond-shell').style.display = 'none';
            location.reload();
        })
        .catch(error => {
            alert('Error add Setting' + error);
            console.error('Error fetching Setting:', error);
        });
}


function openDiamondShellForm() {
    document.getElementById('add-diamond-shell').style.display = 'flex';
}

function closeDiamondShellForm() {
    if (confirm("Xác Nhận Hủy?")) {
        document.getElementById('add-diamond-shell').style.display = 'none';
    }
}