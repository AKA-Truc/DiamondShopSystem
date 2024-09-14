document.addEventListener('DOMContentLoaded', function () {
    const materialSelect = document.getElementById('material-select');
    const priceSelect = document.getElementById('price-select');
    const table = document.getElementById('material-table');
    const tbody = table.querySelector('tbody');
    let jewelryData = [];

    async function fetchData() {
        try {
            const response = await fetch(`${window.base_url}/setting-management/settings`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }

            // Đảm bảo chỉ lấy phần data từ object
            const responseData = await response.json();
            jewelryData = responseData.data; // Lấy mảng từ thuộc tính data
            console.log(jewelryData); // Kiểm tra xem jewelryData đã là mảng chưa

            filterAndDisplayData();
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    function filterAndDisplayData() {
        const selectedMaterial = materialSelect.value;
        const selectedPriceRange = priceSelect.value;

        let filteredData = jewelryData.filter(item => {
            // Lọc material theo keyword (Gold, Silver, Platinum)
            let matchesMaterial = true;
            if (selectedMaterial !== 'none') {
                const materialKeywords = {
                    'gold': ['gold', 'vàng'],
                    'silver': ['silver', 'bạc'],
                    'platinum': ['platinum', 'bạch kim']
                };

                const material = item.material.toLowerCase();
                matchesMaterial = materialKeywords[selectedMaterial].some(keyword => material.includes(keyword));
            }

            // Lọc theo price range
            const price = item.price;
            let matchesPrice = true;
            if (selectedPriceRange === 'under-1m') {
                matchesPrice = price < 1000000;
            } else if (selectedPriceRange === '1m-3m') {
                matchesPrice = price >= 1000000 && price <= 3000000;
            } else if (selectedPriceRange === 'above-3m') {
                matchesPrice = price > 3000000;
            }

            return matchesMaterial && matchesPrice;
        });

        tbody.innerHTML = '';

        // Populate the table with filtered data
        if (filteredData.length > 0) {
            table.classList.remove('hidden');
            filteredData.forEach(item => {
                const row = document.createElement('tr');
                row.innerHTML = `
                <td>${item.material}</td>
                <td>${item.price.toLocaleString('vi-VN')} VND</td>
            `;
                tbody.appendChild(row);
            });
        } else {
            table.classList.add('hidden');
        }
    }


    // Event listeners for the select elements
    materialSelect.addEventListener('change', filterAndDisplayData);
    priceSelect.addEventListener('change', filterAndDisplayData);

    // Fetch data on page load
    fetchData();
});
