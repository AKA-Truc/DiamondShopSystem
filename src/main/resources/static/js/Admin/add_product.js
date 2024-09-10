document.addEventListener('DOMContentLoaded', () => {
    const addButton = document.querySelector('.table-add-btn');
    const checkAllBox = document.querySelector('.table-checkAll');
    const tableBody = document.querySelector('table.h-table tbody');
    const token = localStorage.getItem('authToken');

    fetchCategory(token);

    if (checkAllBox) {
        checkAllBox.addEventListener('change', handleCheckAllChange);
    }

    if (addButton) {
        addButton.addEventListener('click', handleAddButtonClick);
    }

    document.querySelector('.save').addEventListener('click', handleSubmit);

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
                </div>
                <span class="toggle-quantity">+</span>
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
        fetchDiamond(token, newRow.querySelector('.list-diamond'));
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
        const container = e.target.previousElementSibling;  // Get the container for the new field
        const row = e.target.closest('tr');  // Get the current row
        const newFieldContainer = document.createElement("div");  // Create a new container div for the new select and inputs
        newFieldContainer.classList.add("item-container");

        // Create the new HTML structure
        newFieldContainer.innerHTML = `
        <select class="table-control item-control list-diamond"></select>
        <select class="table-control item-control type-diamond">
            <option value="1">Kim Cương Phụ</option>
            <option value="0">Kim Cương Chủ (1 viên)</option>
        </select>
        <input type="number" class="table-control item-control quantity-input" placeholder="Nhập số lượng">
    `;

        // Insert the new field container before the toggle button
        container.parentNode.insertBefore(newFieldContainer, e.target);

        // Fetch diamond options and populate the new select
        fetchDiamond(token, newFieldContainer.querySelector('.list-diamond'));

        // Get the diamond type select in the newly added row
        const typeDiamondSelect = newFieldContainer.querySelector('.type-diamond');

        // Check if "Kim Cương Chủ" already exists in the row
        const otherDiamondTypes = row.querySelectorAll('.type-diamond');
        const existingKcChu = Array.from(otherDiamondTypes).some(select => select.value === '0');  // Check if any "Kim Cương Chủ" is already selected

        if (existingKcChu) {
            // If a "Kim Cương Chủ" already exists, disable the "Chủ" option in the new select
            typeDiamondSelect.value = '1';  // Default to "Kim Cương Phụ"
            typeDiamondSelect.querySelector('option[value="0"]').setAttribute('disabled', 'true');  // Disable "Chủ"
        }

        // Add the change event listener for diamond type changes
        typeDiamondSelect.addEventListener('change', handleDiamondTypeChange);
    }

    function handleDiamondTypeChange(e) {
        const currentSelect = e.target;  // The select element that was changed
        const currentValue = currentSelect.value;  // Get the selected value
        const row = currentSelect.closest('tr');  // Get the current row
        const quantityInput = currentSelect.parentNode.querySelector('.quantity-input');  // Get the quantity input field

        // If "Chủ" is selected, disable it in other selects and set quantity to 1
        if (currentValue === '0') {  // Assuming '0' is "Kim Cương Chủ"
            // Disable "Chủ" in other diamond type selects within the same row
            const otherDiamondTypes = row.querySelectorAll('.type-diamond');
            otherDiamondTypes.forEach(select => {
                if (select !== currentSelect) {
                    select.value = '1';  // Set to "Kim Cương Phụ"
                    select.querySelector('option[value="0"]').setAttribute('disabled', 'true');  // Disable "Chủ"
                }
            });

            // Set the quantity to 1 and disable the input
            quantityInput.value = 1;
            quantityInput.setAttribute('disabled', 'true');

        } else {  // If not "Chủ" (i.e., "Phụ"), allow changing quantity and re-enable "Chủ"
            // Enable the quantity input
            quantityInput.removeAttribute('disabled');
            quantityInput.value = '';  // Clear the quantity input

            // Re-enable the "Chủ" option if there is no other "Chủ" selected in the row
            const otherDiamondTypes = row.querySelectorAll('.type-diamond');
            const existingKcChu = Array.from(otherDiamondTypes).some(select => select.value === '0');  // Check if any other select has "Chủ"

            if (!existingKcChu) {  // If no other "Chủ" exists, re-enable the "Chủ" option
                otherDiamondTypes.forEach(select => {
                    select.querySelector('option[value="0"]').removeAttribute('disabled');
                });
            }
        }
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

        console.log('Main Form Data:', product);

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

                    if (diamondType === 'Chủ') {
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
                        const settingId = row.querySelector('.list-setting').value;
                        const size = parseFloat(row.querySelector('input[placeholder="Nhập size"]').value);
                        const markupRate = parseFloat(row.querySelector('input[placeholder="Nhập tỉ lệ áp giá"]').value);
                        const laborCost = parseFloat(row.querySelector('input[placeholder="Nhập tiền gia công"]').value);
                        const inventory = parseFloat(row.querySelector('input[placeholder="Nhập số lượng tồn kho"]').value);

                        const diamonds = row.querySelectorAll('.list-diamond');
                        const quantities = row.querySelectorAll('.quantity-input');
                        const diamondTypes = row.querySelectorAll('.type-diamond');

                        const diamondMap = new Map();

                        diamonds.forEach((diamondElement, index) => {
                            const diamondId = diamondElement.value;
                            const quantity = parseInt(quantities[index].value) || 0;
                            const diamondType = diamondTypes[index].value;

                            if (!diamondMap.has(diamondId)) {
                                diamondMap.set(diamondId, { quantity: 0, typeDiamond: diamondType });
                            }

                            if (diamondType === 'Chủ') {
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
                location.reload();
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

    function fetchDiamond(token, selectElement) {
        if (!selectElement) {
            console.error('Select element is not defined.');
            return;
        }

        fetch(`${window.base_url}/diamond-management/diamonds`, {
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
                    throw new Error("Diamonds should be an array.");
                }

                selectElement.innerHTML = '';
                data.forEach(diamond => {
                    const option = document.createElement('option');
                    option.value = diamond.diamondId;
                    option.textContent = `${diamond.diamondName}`;
                    selectElement.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching diamonds:', error));
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