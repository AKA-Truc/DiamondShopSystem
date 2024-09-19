document.addEventListener('DOMContentLoaded', () => {
    // Fetch and display top-selling products
    fetchTopSellingProducts();
    // Fetch and display total products sold
    fetchTotalProductsSold();
    // Fetch and display total orders for the current day
    fetchTotalOrdersToday();
    // Fetch and display revenue by month and year
    fetchRevenueByMonthAndYear();
    // Fetch and display total orders by year
    fetchTotalOrdersByYear();
    // Fetch and display customer data by gender
    fetchCustomerDataByGender();
    // Fetch and display total customers
    fetchTotalCustomers();
    fetchTotalOrder();
    fetchTotalRevenueChart()
    fetchTopCustomerSelling()
    // Add event listeners for month and year select elements
    document.getElementById('select-month').addEventListener('change', updateData);
    document.getElementById('select-year').addEventListener('change', updateData);
});

function updateData() {
    fetchRevenueByMonthAndYear();
    fetchTotalOrdersByYear();
    fetchTotalRevenueChart()
}

// Fetch Top Selling Products
async function fetchTopSellingProducts() {
    try {
        const response = await fetch(`${window.base_url}/dashboard/product-topSelling`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('authToken')}`
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        const topProductsList = document.getElementById('top-products-list');
        topProductsList.innerHTML = ''; // Clear existing list

        // Insert headers again after clearing
        const headerHTML = `
            <li class="product-header">
                <span class="product-index-header">#</span>
                <span class="product-name-header">Name</span>
                <span class="product-sales-header">Sales</span>
            </li>`;
        topProductsList.insertAdjacentHTML('afterbegin', headerHTML);

        data.data.forEach((product, index) => {
            const productName = product[0];
            const productSales = product[1]; // Number of items sold
            const colorClass = `color-${(index % 5) + 1}`; // Cycle through 5 colors

            let listItem = `
                        <li>
                            <span class="product-index">${index + 1}</span>
                            <span class="product-name ${colorClass}">${productName}</span>
                            <span class="product-sales">${productSales} items sold</span>
                        </li>`;
            topProductsList.insertAdjacentHTML('beforeend', listItem);
        });
    } catch (error) {
        console.error('Error fetching top-selling products:', error);
    }
}


// Fetch Total Products Sold
function fetchTotalProductsSold() {
    fetch(`${window.base_url}/dashboard/products/totalSold`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken') // Add your token if needed
        }
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('products-sold-value').innerText = data.data;
        })
        .catch(error => console.error('Error fetching total products sold:', error));
}

// Fetch Total Orders Today
function fetchTotalOrdersToday() {
    fetch(`${window.base_url}/dashboard/order-date`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken') // Add your token if needed
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            document.getElementById('order-today-value').innerText = data.data;
        })
        .catch(error => console.error('Error fetching total orders today:', error));
}

// Fetch Revenue by Month and Year
function fetchRevenueByMonthAndYear() {
    const month = document.getElementById('select-month').value;
    const year = document.getElementById('select-year').value;

    const url = `${window.base_url}/dashboard/revenue-month?month=${month}&year=${year}`;

    fetch(url, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken')
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data && data.data !== null) {
                const revenue = data.data;
                const revenueElement = document.getElementById('total-sales-value');
                revenueElement.textContent = revenue.toLocaleString();
            } else {
                console.error('No data found');
                document.getElementById('total-sales-value').textContent = '0'; // Handle case with no data
            }
        })
        .catch(error => console.error('Error fetching revenue by month and year:', error));
}


// Fetch Total Orders by Year
function fetchTotalOrdersByYear() {
    const year = document.getElementById('select-year').value;

    fetch(`${window.base_url}/dashboard/order-year?year=${year}`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken')
        }
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;

            const ordersByMonth = Array(12).fill(0);

            data.forEach(item => {
                const month = item[0] - 1;
                ordersByMonth[month] = item[1];
            });

            const ctx = document.getElementById('order-by-year-chart').getContext('2d');

            // Tạo gradient
            const gradient = ctx.createLinearGradient(0, 0, 0, 200);  // Tạo gradient từ trên xuống dưới
            gradient.addColorStop(0, 'rgba(75, 192, 192, 1)');
            gradient.addColorStop(1, 'rgba(75, 192, 192, 0.02)');


            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],  // Nhãn cho 12 tháng
                    datasets: [{
                        label: 'Số lượng đơn đặt hàng',
                        data: ordersByMonth,  // Số lượng đơn đặt hàng theo tháng
                        backgroundColor: gradient,
                        borderColor: 'rgba(75, 192, 192, 0.5)',  // Màu viền
                        borderWidth: 2.5,
                        fill: true
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            grid: {
                                display: false
                            }
                        },
                        x: {
                            grid: {
                                display: false
                            }
                        }
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching order data:', error));
}

// Fetch Customer Data by Gender
function fetchCustomerDataByGender() {
    fetch(`${window.base_url}/dashboard/customer/gender`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken')
        }
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;

            const genderLabels = data.map(item => item[0]); // Lấy nhãn (Giới tính)
            const genderCounts = data.map(item => item[1]); // Lấy số lượng khách hàng

            const genderChart = document.getElementById('customer-gender-chart').getContext('2d');

            new Chart(genderChart, {
                type: 'bar',  // Biểu đồ cột
                data: {
                    labels: genderLabels,
                    datasets: [{
                        label: 'Số lượng khách hàng',
                        data: genderCounts,
                        backgroundColor: [
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(255, 99, 132, 0.5)'
                        ],
                        borderColor: [
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(255, 99, 132, 0.5)'
                        ],
                        borderWidth: 1,
                        borderRadius: 10
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,  // Bắt đầu trục Y từ 0
                            grid: {
                                display: false  // Bỏ đường lưới trên trục Y
                            }
                        },
                        x: {
                            grid: {
                                display: false  // Bỏ đường lưới trên trục X
                            }
                        }
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching customer data by gender:', error));
}


// Fetch Total Customers
function fetchTotalCustomers() {
    fetch(`${window.base_url}/dashboard/customer`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken') // Add your token if needed
        }
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('new-customers-value').innerText = data.data;
        })
        .catch(error => console.error('Error fetching total customers:', error));
}

function fetchTotalOrder() {
    fetch(`${window.base_url}/dashboard/order`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken') // Add your token if needed
        }
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('total-order-value').innerText = data.data;
        })
        .catch(error => console.error('Error fetching total orders:', error));
}

function fetchTotalRevenueChart() {

    const year = document.getElementById('select-year').value;

    fetch(`${window.base_url}/dashboard/revenue?year=${year}`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken')
        }
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;

            const revenueByMonth = Array(12).fill(0);

            data.forEach(item => {
                const month = item[0] - 1;
                revenueByMonth[month] = item[1];
            });

            const ctx = document.getElementById('total-revenue-chart').getContext('2d');


            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],  // Nhãn cho 12 tháng
                    datasets: [{
                        label: 'Số lượng đơn đặt hàng',
                        data: revenueByMonth,  // Số lượng đơn đặt hàng theo tháng
                        backgroundColor: 'rgba(221,50,112,0.5)',
                        borderColor: 'rgba(221,50,112,1)',  // Màu viền
                        borderWidth: 2.5,
                        fill: false,
                        tension: 0.4  // Tăng độ mềm cho đường biểu đồ
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            grid: {
                                display: false
                            }
                        },
                        x: {
                            grid: {
                                display: false
                            }
                        }
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching order data:', error));
}

function fetchTopCustomerSelling() {

    fetch(`${window.base_url}/dashboard/user-list`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken')
        }
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;

            const customerNames = data.map(item => item[0]);  // Tên khách hàng
            const orderCounts = data.map(item => item[1]);    // Số lượng đơn đặt hàng

            const ctx = document.getElementById('sales-customer-chart').getContext('2d');

            new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: customerNames,
                    datasets: [{
                        label: 'Số lượng đơn đặt hàng',
                        data: orderCounts,
                        backgroundColor: [
                            'rgba(75,192,192,0.8)',
                            'rgba(54, 162, 235, 0.8)',
                            'rgba(255, 206, 86, 0.8)',
                            'rgba(231, 74, 59, 0.8)',
                            'rgba(153, 102, 255, 0.8)',
                            'rgba(255, 159, 64, 0.8)'
                        ],
                        borderColor: [
                            'rgba(75, 192, 192, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(231, 74, 59, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,  // Tắt tỷ lệ khung hình để biểu đồ phù hợp với container
                    plugins: {
                        legend: {
                            position: 'right',
                            align: 'center',
                            labels: {
                                boxWidth: 10,
                                padding: 20
                            }
                        }
                    },
                    layout: {
                        padding: {
                            left: 10, right: 10, top: 10, bottom: 10
                        }
                    }
                }
            });
        })
        .catch(error => console.error('Error fetching user data:', error));
}