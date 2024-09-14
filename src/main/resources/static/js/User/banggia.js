document.addEventListener('DOMContentLoaded', function () {
    populateTable('none', 'all');
});

document.getElementById('material-select').addEventListener('change', function () {
    const selectedMaterial = this.value;
    const selectedPrice = document.getElementById('price-select').value;
    populateTable(selectedMaterial, selectedPrice);
});

document.getElementById('price-select').addEventListener('change', function () {
    const selectedPrice = this.value;
    const selectedMaterial = document.getElementById('material-select').value;
    populateTable(selectedMaterial, selectedPrice);
});

function populateTable(selectedMaterial, selectedPrice) {
    const table = document.getElementById('material-table');
    const tbody = table.querySelector('tbody');

    // Data sets for Gold, Silver, and Platinum
    const data = {
        'gold': [
            { material: 'White Gold 18K', price: 2500000 },
            { material: 'Yellow Gold 18K', price: 3000000 },
            { material: 'Rose Gold 18K', price: 2700000 },
            { material: 'White Gold 10K', price: 1300000 },
            { material: 'Yellow Gold 10K', price: 1400000 },
            { material: 'White Gold 9K', price: 1000000 },
            { material: '14K Gold-Plated Silver', price: 1500000 },
            { material: '18K Gold-Plated Silver', price: 1800000 }
        ],
        'silver': [
            { material: 'Silver', price: 1000000 },
            { material: 'Rhodium Plated Silver', price: 1200000 },
            { material: '14K Gold-Plated Silver', price: 1500000 },
            { material: '18K Gold-Plated Silver', price: 1800000 }
        ],
        'platinum': [
            { material: 'Platinum', price: 3500000 },
            { material: 'Platinum 950', price: 4000000 }
        ]
    };

    // Function to filter by price range
    function filterByPrice(item, priceRange) {
        switch (priceRange) {
            case 'under-1m':
                return item.price < 1000000;
            case '1m-3m':
                return item.price >= 1000000 && item.price <= 3000000;
            case 'above-3m':
                return item.price > 3000000;
            default:
                return true;
        }
    }

    // Clear previous table data
    tbody.innerHTML = '';

    // If "none" is selected, show all data
    if (selectedMaterial === 'none') {
        const allData = [...data.gold, ...data.silver, ...data.platinum].filter(item => filterByPrice(item, selectedPrice));
        allData.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `<td>${item.material}</td><td>${item.price.toLocaleString()} VND</td>`;
            tbody.appendChild(row);
        });
        table.classList.remove('hidden'); // Show table
    }
    // If a valid material is selected, show only relevant data
    else if (data[selectedMaterial]) {
        const filteredData = data[selectedMaterial].filter(item => filterByPrice(item, selectedPrice));
        filteredData.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `<td>${item.material}</td><td>${item.price.toLocaleString()} VND</td>`;
            tbody.appendChild(row);
        });
        table.classList.remove('hidden'); // Show table
    }
    // If nothing valid is selected, hide the table
    else {
        table.classList.add('hidden'); // Hide table
    }
}