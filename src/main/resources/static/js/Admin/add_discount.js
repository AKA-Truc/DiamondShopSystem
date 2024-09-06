document.addEventListener("DOMContentLoaded", () => {
    const voucherForm = document.getElementById("voucherForm");
    const voucherList = document.getElementById("voucher-List");
    const token = localStorage.getItem('accessToken');  // Lấy token từ localStorage

    // Fetch existing discount vouchers from the API and populate the table
    function fetchVouchers() {
        fetch('http://localhost:8080/promotion-management/promotions', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,  // Thêm token vào header
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Unauthorized. Please log in again.');
                    window.location.href = '/DiamondShopSystem/src/main/resources/templates/login.html';
                    return;
                }
                return response.json();
            })
            .then(data => {
                voucherList.innerHTML = '';  // Clear previous data
                data.forEach((voucher, index) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${voucher.name}</td>
                        <td>${voucher.percent}%</td>
                        <td>${voucher.describes}</td>
                        <td>${new Date(voucher.startDate).toLocaleString()}</td>
                        <td>${new Date(voucher.exDate).toLocaleString()}</td>
                        <td>
                            <button onclick="editVoucher(${voucher.id})">Edit</button>
                            <button onclick="deleteVoucher(${voucher.id})">Delete</button>
                        </td>
                    `;
                    voucherList.appendChild(row);
                });
            })
            .catch(error => console.error("Error fetching vouchers:", error));
    }

    // Submit new voucher to API
    voucherForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const formData = new FormData(voucherForm);
        const voucherData = {
            name: formData.get("Name"),
            percent: formData.get("Percent"),
            startDate: formData.get("StartDate"),
            exDate: formData.get("EXDate"),
            describes: formData.get("Describes")
        };

        fetch('http://localhost:8080/promotion-management/promotions', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,  // Thêm token vào header
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(voucherData)
        })
            .then(response => {
                if (response.status === 401) {
                    alert('Unauthorized. Please log in again.');
                    window.location.href = '/DiamondShopSystem/src/main/resources/templates/login.html';
                    return;
                }
                return response.json();
            })
            .then(data => {
                alert("Mã giảm giá đã được thêm!");
                fetchVouchers();
            })
            .catch(error => console.error("Error adding voucher:", error));
    });

    // Delete a voucher by ID
    window.deleteVoucher = function (id) {
        if (confirm("Bạn có chắc muốn xóa mã giảm giá này?")) {
            fetch(`http://localhost:8080/promotion-management/promotions/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`,  // Thêm token vào header
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.status === 401) {
                        alert('Unauthorized. Please log in again.');
                        window.location.href = './login1.html';
                        return;
                    }
                    if (response.ok) {
                        alert("Mã giảm giá đã được xóa!");
                        fetchVouchers();
                    } else {
                        console.error("Failed to delete voucher");
                    }
                })
                .catch(error => console.error("Error deleting voucher:", error));
        }
    };

    // Fetch the list of vouchers on page load
    fetchVouchers();
});
