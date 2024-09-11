document.addEventListener('DOMContentLoaded', () => {
    const diamondForm = document.getElementById("diamondForm");
    const diamondList = document.getElementById("diamond-List");
    const searchInput = document.getElementById('search-bar');
    const popup1Overlay = document.getElementById('popup1Overlay');
    const token = localStorage.getItem('authToken');
    let isEditing = false;
    let editingDiamondId = null;

    // Fetch the list of diamonds
    function fetchDiamond() {
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
                diamondList.innerHTML = '';
                data.forEach((diamond, index) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${diamond.shape}</td>
                        <td>${diamond.color}</td>
                        <td>${diamond.cut}</td>
                        <td>${diamond.carat}</td>
                        <td>${diamond.measurement}</td>
                        <td>${diamond.clarity}</td>
                        <td>${diamond.giacode}</td>
                        <td>${diamond.price}</td>
                        <td class="action-buttons">
                            <button class="edit-btn" onclick="editDiamond(${diamond.diamondId})"><ion-icon name="create-outline"></ion-icon></button>
                            <button class="delete-btn" data-id="${diamond.diamondId}" ><ion-icon name="trash-outline"></ion-icon></button>
                        </td>
                    `;
                    diamondList.appendChild(row);
                });
                addDeleteEventListeners();
            })
            .catch(error => console.error("Error fetching diamond:", error));
    }

    // Add or update a diamond
    function addOrUpdateDiamond(diamondData) {
        let url = `${window.base_url}/diamond-management/diamonds`;
        let method = 'POST';

        if (isEditing) {
            url = `${window.base_url}/diamond-management/diamonds/${editingDiamondId}`;
            method = 'PUT';
        }

        fetch(url, {
            method: method,
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(diamondData)
        })
            .then(response => response.json())
            .then(result => {
                alert(isEditing ? "Diamond updated successfully!" : "Diamond added successfully!");
                fetchDiamond();
                popup1Overlay.style.display = 'none';
                resetFormState();
            })
            .catch(error => {
                alert("Fail to add or update Diamond");
                console.error("Error adding/updating diamond:", error)
            });
    }

    function addDeleteEventListeners() {
        const popup = document.getElementById('popup');
        const yesBtn = document.getElementById('yes-btn');
        const noBtn = document.getElementById('no-btn');
        const deleteBtns = document.querySelectorAll('.delete-btn');
        let currentRow;
        let diamondId;

        deleteBtns.forEach(button => {
            button.addEventListener('click', function () {
                popup.style.display = 'flex';
                currentRow = this.closest('tr');
                diamondId = this.getAttribute('data-id');
            });
        });

        yesBtn.addEventListener('click', () => {
            if (currentRow && diamondId) {
                fetch(`${window.base_url}/diamond-management/diamonds/${diamondId}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            currentRow.remove();
                            alert('Diamond deleted successfully');
                        } else {
                            alert('Failed to delete diamond');
                        }
                        popup.style.display = 'none';
                    })
                    .catch(error => {
                        console.error('Error deleting diamond:', error);
                        popup.style.display = 'none';
                    });
            }
        });

        noBtn.addEventListener('click', () => {
            popup.style.display = 'none';
        });
    }

    diamondForm.addEventListener("submit", (e) => {
        e.preventDefault();

        // Get form data
        const formData = new FormData(diamondForm);
        const diamondData = {
            shape: formData.get("shape").trim(),
            color: formData.get("color").trim(),
            cut: formData.get("cut").trim(),
            clarity: formData.get("clarity").trim(),
            carat: formData.get("carat").trim(),
            measurement: formData.get("measurement").trim(),
            price: formData.get("price").trim(),
            giacode: formData.get("GIA").trim()
        };

        const missingFields = [];
        for (const [key, value] of Object.entries(diamondData)) {
            if (!value) {
                missingFields.push(key);
            }
        }

        if (missingFields.length > 0) {
            alert(`Please fill in all required fields: ${missingFields.join(', ')}`);
            return;
        }

        // Proceed to add or update the diamond
        addOrUpdateDiamond(diamondData);
    });

    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#diamond-List tr');

        rows.forEach(row => {
            const giaCode = row.querySelector('td:nth-child(8)').textContent.toLowerCase();
            row.style.display = giaCode === searchText ? 'table-row' : 'none';
        });
    });

    window.editDiamond = function(diamondId) {
        isEditing = true;
        editingDiamondId = diamondId;

        fetch(`${window.base_url}/diamond-management/diamonds/${diamondId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(diamond => {
                const data = diamond.data;
                document.getElementById("measurement").value = data.measurement || "";
                document.getElementById("shape").value = data.shape || "";
                document.getElementById("color").value = data.color || "";
                document.getElementById("clarity").value = data.clarity || "";
                document.getElementById("carat").value = data.carat || "";
                document.getElementById("cut").value = data.cut || "";
                document.getElementById("price").value = data.price || "";
                document.getElementById("GIA").value = data.giacode || "";
                popup1Overlay.style.display = 'flex';
            })
            .catch(error => console.error("Error fetching diamond details:", error));
    };

    document.getElementById('openpopup1').addEventListener('click', () => {
        diamondForm.reset();
        popup1Overlay.style.display = 'flex';
        resetFormState();
    });

    document.getElementById('closepopup1').addEventListener('click', () => {
        if (confirm("Xác Nhận Hủy?")) {
            popup1Overlay.style.display = 'none';
        }
    });

    document.getElementById('cancelButton').addEventListener('click', () => {
        if (confirm("Xác Nhận Hủy?")) {
            popup1Overlay.style.display = 'none';
        }
    });

    window.addEventListener('click', (event) => {
        if (event.target === popup1Overlay) {
            if (confirm("Xác Nhận Hủy?")) {
                popup1Overlay.style.display = 'none';
            }
        }
    });

    function resetFormState() {
        isEditing = false;
        editingDiamondId = null;
    }

    fetchDiamond();
});
