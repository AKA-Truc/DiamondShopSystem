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

    function handleCheckAllChange(e) {
        const isChecked = e.target.checked;
        tableBody.querySelectorAll('.table-checkbox').forEach(checkbox => {
            checkbox.checked = isChecked;
        });
    }

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
                    <select class="table-control item-control list-diamond"></select>
                    <input type="number" class="table-control item-control quantity-input" placeholder="Nhập số lượng">
                </div>
                <span class="toggle-quantity">+</span>
            </td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập size"></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập tỉ lệ áp giá"></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập tiền gia công"></td>
            <td><input type="number" class="table-control item-control" placeholder="Nhập số lượng tồn kho"></td>
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

    function handleToggleQuantity(e) {
        const container = e.target.previousElementSibling;
        const newFieldContainer = document.createElement("div");
        newFieldContainer.classList.add("item-container");

        newFieldContainer.innerHTML = `
            <select class="table-control item-control list-diamond"></select>
            <input type="number" class="table-control item-control quantity-input" placeholder="Nhập số lượng">
        `;

        container.parentNode.insertBefore(newFieldContainer, e.target);
        fetchDiamond(token, newFieldContainer.querySelector('.list-diamond'));
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
        const product = {
            productName: document.getElementById('Name').value,
            warrantyPeriod: document.getElementById('Warranty').value,
            category: {
                categoryId: document.getElementById('loaiSanPham').value
            }
        };

        const formData = new FormData();

        // Convert product object to JSON string and append
        formData.append('product', JSON.stringify(product));

        // Append files if they exist
        const imageURL = document.getElementById('image').files[0];
        const subImageURL = document.getElementById('image1').files[0];

        if (imageURL) {
            formData.append('imageURL', imageURL);
        }

        if (subImageURL) {
            formData.append('subImageURL', subImageURL);
        }

        console.log('Main Form Data:', product);

        // Submit data
        submitData(formData);
    }

    function submitData(formData) {
        const token = localStorage.getItem('authToken'); // Get the token
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


                        // Create a map to handle diamondId and accumulated quantities
                        const diamondMap = new Map();

                        // Loop through all the diamonds and quantities in the row
                        diamonds.forEach((diamondElement, index) => {
                            const diamondId = diamondElement.value;
                            const quantity = parseInt(quantities[index].value) || 0;

                            if (diamondMap.has(diamondId)) {
                                // If diamondId already exists, update the quantity
                                const existingQuantity = diamondMap.get(diamondId);
                                diamondMap.set(diamondId, existingQuantity + quantity);
                            } else {
                                // Otherwise, add a new entry
                                diamondMap.set(diamondId, quantity);
                            }
                        });

                        // Convert the map to an array of diamondDetails
                        const diamondDetails = Array.from(diamondMap, ([diamondId, quantity]) => ({
                            diamond: { diamondId },
                            quantity
                        }));

                        const rowData = {
                            product:{
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
                    Detailsl.forEach(productdetail => {
                        console.log(productdetail);
                        addProductDetail(token, productdetail)
                    })
                });
            })
            .catch(error => console.error('Error submitting product data:', error));
    }

});
function addProductDetail(token, productdetail){
    console.log(token);
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
            console.log("hahaha",result);
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
                option.textContent = `${diamond.origin} ${diamond.carat}`;
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