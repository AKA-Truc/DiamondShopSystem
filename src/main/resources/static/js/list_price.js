const textOptions = ["Giảm giá đến 16%", "Trang sức kim cương ưu đãi 40%", "Mặt dây chuyền ưu đãi 15%"];
const centerText = document.getElementById("center-text");
let index = 0;

function rotateText() {
    centerText.classList.remove('visible');
    setTimeout(() => {
        centerText.textContent = textOptions[index];
        centerText.classList.add('visible');
        index = (index + 1) % textOptions.length;
    }, 1000); // Wait for the fade-out effect before changing text
}

setInterval(rotateText, 3000); // Change text every 3 seconds (1s fade-out + 1s fade-in)

// Initialize text
rotateText();

// Add ly

const diamondData = {
    '4 Ly 5': [
        {ly: '4.5', color: 'D', sach: 'IF', price: '36,805,000', discount: '33,860,000'},
        {ly: '4.5', color: 'D', sach: 'VVS1', price: '35,305,000', discount: '32,480,000'},
        {ly: '4.5', color: 'D', sach: 'VVS2', price: '31,620,000', discount: '29,090,000'},
        {ly: '4.5', color: 'D', sach: 'VS1', price: '29,120,000', discount: '26,790,000'},
        {ly: '4.5', color: 'D', sach: 'VS2', price: '26,685,000', discount: '24,550,000'},
        {ly: '4.5', color: 'E', sach: 'IF', price: '32,990,000', discount: '30,350,000'},
        {ly: '4.5', color: 'E', sach: 'VVS1', price: '31,468,000', discount: '28,950,000'},
        {ly: '4.5', color: 'E', sach: 'VVS2', price: '29,240,000', discount: '26,900,000'},
        {ly: '4.5', color: 'E', sach: 'VS1', price: '26,979,000', discount: '24,820,000'},
        {ly: '4.5', color: 'E', sach: 'VS2', price: '24,979,000', discount: '22,980,000'},
        {ly: '4.5', color: 'F', sach: 'IF', price: '31,979,000', discount: '29,420,000'},
        {ly: '4.5', color: 'F', sach: 'VVS1', price: '30,539,000', discount: '28,095,000'},
        {ly: '4.5', color: 'F', sach: 'VVS2', price: '27,500,000', discount: '25,300,000'},
        {ly: '4.5', color: 'F', sach: 'VS1', price: '26,468,000', discount: '24,350,000'},
        {ly: '4.5', color: 'F', sach: 'VS2', price: '25,750,000', discount: '23,690,000'},
        {ly: '4.5', color: 'G', sach: 'VS1', price: '25,865,000', discount: '23,795,000'},
        {ly: '4.5', color: 'G', sach: 'VS2', price: '22,990,000', discount: '21,150,000'},
    ],
    '5 Ly': [
        {ly: '5', color: 'D', sach: 'IF', price: '74,772,000', discount: '68,790,000'},
        {ly: '5', color: 'D', sach: 'VVS1', price: '68,968,000', discount: '63,450,000'},
        {ly: '5', color: 'D', sach: 'VVS2', price: '56,250,000', discount: '51,750,000'},
        {ly: '5', color: 'D', sach: 'VS1', price: '51,979,000', discount: '47,820,000'},
        {ly: '5', color: 'D', sach: 'VS2', price: '48,805,000', discount: '44,900,000'},
        {ly: '5', color: 'E', sach: 'IF', price: '64,990,000', discount: '59,790,000'},
        {ly: '5', color: 'E', sach: 'VVS1', price: '61,685,000', discount: '56,750,000'},
        {ly: '5', color: 'E', sach: 'VVS2', price: '54,240,000', discount: '49,900,000'},
        {ly: '5', color: 'E', sach: 'VS1', price: '49,968,000', discount: '45,970,000'},
        {ly: '5', color: 'E', sach: 'VS2', price: '47,479,000', discount: '43,680,000'},
        {ly: '5', color: 'F', sach: 'IF', price: '60,750,000', discount: '55,890,000'},
        {ly: '5', color: 'F', sach: 'VVS1', price: '57,500,000', discount: '52,900,000'},
        {ly: '5', color: 'F', sach: 'VVS2', price: '52,740,000', discount: '48,520,000'},
        {ly: '5', color: 'F', sach: 'VS1', price: '47,740,000', discount: '43,920,000'},
        {ly: '5', color: 'F', sach: 'VS2', price: '46,468,000', discount: '42,750,000'},
        {ly: '5', color: 'G', sach: 'VS1', price: '44,479,000', discount: '40,920,000'},
        {ly: '5', color: 'G', sach: 'VS2', price: '40,968,000', discount: '37,690,000'},
    ],

    '5 Ly 4': [
        {ly: '5.4', color: 'D', sach: 'IF', price: '125,680,000', discount: '115,625,000'},
    ],
    '6 Ly 8': [
        { id: 'D005', color: 'Xám', price: '$700' },
        { id: 'D006', color: 'Nâu', price: '$750' },
    ],

    // Add more data here as needed
};

function changeData(diamondType) {
    const info = document.getElementById('diamond-info');
    const table = document.getElementById('diamond-table');
    const tbody = table.querySelector('tbody');
    const introText = document.getElementById('intro-text');

    // Clear existing table data
    tbody.innerHTML = '';


    if (diamondType === '4 Ly') {
        introText.innerHTML = 'Liên hệ chuyên gia kim cương Jemmia để có mức giá tốt nhất.';
        introText.style.display = 'block';
    } else if (diamondType === '7 Ly') {
        introText.innerHTML = 'Kim cương 7 Ly là lựa chọn hoàn hảo cho những ai yêu thích sự nổi bật và quý phái.';
        introText.style.display = 'block';
    } else if (diamondType === '7 Ly 2') {
        introText.innerHTML = 'Kim cương 7 Ly 2 là lựa chọn hoàn hảo cho .....';
        introText.style.display = 'block';
    } else{
        introText.style.display = 'none';
    }



    if (diamondData[diamondType]) {
        const data = diamondData[diamondType];
        data.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `<td>${item.ly}</td><td>${item.color}</td><td>${item.sach}</td><td>${item.price}</td><td>${item.discount}</td>`;
            tbody.appendChild(row);
        });
        table.style.display = 'table';
        info.querySelector('h2').innerHTML = `Giá lẻ kim cương GIA ${diamondType}`;
    } else {
        table.style.display = 'none';
        info.querySelector('h2').innerHTML = `Giá lẻ kim cương GIA ${diamondType}`;
    }
}