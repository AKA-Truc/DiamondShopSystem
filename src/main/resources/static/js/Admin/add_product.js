document.addEventListener('DOMContentLoaded', () => {
    const addButton = document.querySelector('.table-add-btn');
    const checkAllBox = document.querySelector('.table-checkAll');
    const tableBody = document.querySelector('table.h-table tbody');
    const token = localStorage.getItem('authToken');

    fetchCategory(token);

    if (checkAllBox) {
        checkAllBox.addEventListener('change', (e) => {
            const isChecked = e.target.checked;
            tableBody.querySelectorAll('.table-checkbox').forEach(checkbox => {
                checkbox.checked = isChecked;
            });
        });
    }

    if (addButton) {
        addButton.addEventListener('click', () => {
            const newRow = createTableRow();
            tableBody.appendChild(newRow);
        });
    }

    function createTableRow() {
        const newRow = document.createElement('tr');
        newRow.innerHTML = `
            <td class="text-center check-col"><input type="checkbox" class="table-checkbox"></td>
            <td>
                <select class="table-control item-control list-setting">
                </select>
            </td>
            <td>
                <div class="item-container">
                    <select class="table-control item-control list-diamond">
                        <!-- Options will be populated dynamically -->
                    </select>
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

        // Add event listeners for the new row
        newRow.querySelector('.row-delete').addEventListener('click', deleteRow);
        newRow.querySelector('.toggle-quantity').addEventListener('click', handleToggleQuantity);
        fetchDiamond(token, newRow.querySelector('.list-diamond'));
        fetchSetting(token, newRow.querySelector('.list-setting'))
        return newRow;
    }

    function handleToggleQuantity(e) {
        const container = e.target.previousElementSibling;
        const newFieldContainer = document.createElement("div");
        newFieldContainer.classList.add("item-container");

        newFieldContainer.innerHTML = `
            <select class="table-control item-control list-diamond">
                <!-- Options will be populated dynamically -->
            </select>
            <input type="text" class="table-control item-control quantity-input" placeholder="Nhập số lượng">
        `;

        container.parentNode.insertBefore(newFieldContainer, e.target);
        const diamondSelect = newFieldContainer.querySelector('.list-diamond');
        fetchDiamond(token, diamondSelect);
    }

    function deleteRow(e) {
        if (confirm('Bạn có chắc chắn muốn xóa dòng này không?')) {
            e.target.closest('tr').remove();
        }
    }

    // Initialize existing row action buttons
    document.querySelectorAll('.row-delete').forEach(btn => {
        btn.addEventListener('click', deleteRow);
    });

    document.querySelectorAll('.toggle-quantity').forEach(btn => {
        btn.addEventListener('click', handleToggleQuantity);
    });
});

function fetchCategory(token){
    const categorySelect  = document.getElementById('loaiSanPham');

    fetch(`${window.base_url}/category-management/categories`,{
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(result =>{
            const data = result.data;

            if(!Array.isArray(data)){
                throw new Error("Category should be an array");
            }

            categorySelect.innerHTML= '';

            data.forEach(category =>{
                const option = document.createElement('option');
                option.value = category.categoryId;
                option.textContent = category.categoryName;
                categorySelect.appendChild(option);
            });
        })
        .catch(error => { console.error(error); });
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
                option.textContent = diamond.origin + diamond.carat;
                selectElement.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching diamonds:', error);
        });
}
function fetchSetting(token,selectElement){
    if (!selectElement) {
        console.error('Select element is not defined.');
        return;
    }

    fetch(`${window.base_url}/setting-management/settings`,{
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

            data.forEach(setiing => {
                const option = document.createElement('option');
                option.value = setiing.settingId;
                option.textContent = setiing.material;
                selectElement.appendChild(option);
            });
        })
}