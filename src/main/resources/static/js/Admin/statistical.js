// Global variables to store chart instances
let monthlyRevenueChart;
let topCustomersChart;

// Function to fetch total revenue
async function fetchTotalRevenue(year) {
    try {
        const response = await fetch(`http://localhost:8080/api/report/getTotalRevenue?year=${year}`);
        if (!response.ok) {
            throw new Error('Failed to fetch total revenue');
        }
        const data = await response.json();
        document.getElementById('total-revenue-value').textContent = (data || 0).toLocaleString();
    } catch (error) {
        console.error('Error fetching total revenue:', error);
    }
}

// Function to fetch total orders
async function fetchTotalOrders(year) {
    try {
        const response = await fetch(`http://localhost:8080/api/report/getTotalOrders?year=${year}`);
        if (!response.ok) {
            throw new Error('Failed to fetch total orders');
        }
        const data = await response.json();
        document.getElementById('total-orders-value').textContent = data || 0;
    } catch (error) {
        console.error('Error fetching total orders:', error);
    }
}

// Function to fetch total products sold
async function fetchTotalProductsSold(year) {
    try {
        const response = await fetch(`http://localhost:8080/api/report/getTotalProductsSold?year=${year}`);
        if (!response.ok) {
            throw new Error('Failed to fetch total products sold');
        }
        const data = await response.json();
        document.getElementById('total-products-sold-value').textContent = data || 0;
    } catch (error) {
        console.error('Error fetching total products sold:', error);
    }
}

// Function to fetch total employees
async function fetchTotalEmployees(year) {
    try {
        const response = await fetch(`http://localhost:8080/api/report/getTotalEmployees?year=${year}`);
        if (!response.ok) {
            throw new Error('Failed to fetch total employees');
        }
        const data = await response.json();
        document.getElementById('total-employees-value').textContent = data || 0;
    } catch (error) {
        console.error('Error fetching total employees:', error);
    }
}

// Function to fetch monthly revenue data
async function fetchMonthlyRevenue(year) {
    try {
        const response = await fetch(`http://localhost:8080/api/report/getMonthlyRevenueData?year=${year}`);
        if (!response.ok) {
            throw new Error('Failed to fetch monthly revenue data');
        }
        const monthlyRevenueData = await response.json();
        updateMonthlyRevenueChart(monthlyRevenueData, year);
    } catch (error) {
        console.error('Error fetching monthly revenue:', error);
    }
}

// Function to fetch top customers
async function fetchTopCustomers(year) {
    try {
        const response = await fetch(`http://localhost:8080/api/report/getTopCustomersByOrders?year=${year}`);
        if (!response.ok) {
            throw new Error('Failed to fetch top customers');
        }
        const topCustomersData = await response.json();
        updateTopCustomersChart(topCustomersData);
    } catch (error) {
        console.error('Error fetching top customers:', error);
    }
}

// Function to update monthly revenue chart
function updateMonthlyRevenueChart(data, year) {
    const chartData = {
        labels: data.map(item => item.month),
        datasets: [{
            label: 'Monthly Revenue',
            data: data.map(item => item.revenue),
            backgroundColor: 'rgba(50, 120, 122, 1)',
            borderColor: 'rgba(50, 120, 122, 1)',
            borderWidth: 3,
            fill: false
        }]
    };

    const chartOptions = {
        responsive: true,
        title: {
            display: true,
            text: `Monthly Revenue in ${year}`
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    };

    const ctx = document.getElementById('monthly-revenue-chart').getContext('2d');
    if (monthlyRevenueChart) {
        monthlyRevenueChart.destroy();
    }
    monthlyRevenueChart = new Chart(ctx, {
        type: 'line',
        data: chartData,
        options: chartOptions
    });
}

function updateTopCustomersChart(data) {
    const chartData = {
        labels: data.map(item => item.customerName),
        datasets: [{
            label: 'Number of Orders',
            data: data.map(item => item.orderCount),
            backgroundColor: [
                'rgba(70, 171, 142, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 205, 86, 1)',
                'rgba(45, 94, 128, 1)',
                'rgba(45, 94, 200, 1)',
                'rgba(45, 94, 18, 1)',
                'rgba(230, 126, 34, 1)'
            ],
            fill: true
        }]
    };

    const chartOptions = {
        responsive: true,
        title: {
            display: true,
            text: 'Top Customers by Number of Orders'
        }
    };

    const ctx = document.getElementById('top-customers-chart').getContext('2d');
    if (topCustomersChart) {
        topCustomersChart.destroy();
    }
    topCustomersChart = new Chart(ctx, {
        type: 'pie',
        data: chartData,
        options: chartOptions
    });
}

const yearSelect = document.getElementById('year-select');
yearSelect.addEventListener('change', () => {
    const selectedYear = yearSelect.value;
    fetchTotalRevenue(selectedYear);
    fetchTotalOrders(selectedYear);
    fetchTotalProductsSold(selectedYear);
    fetchTotalEmployees(selectedYear);
    fetchMonthlyRevenue(selectedYear);
    fetchTopCustomers(selectedYear);
});

document.addEventListener('DOMContentLoaded', () => {
    const selectedYear = yearSelect.value;
    fetchTotalRevenue(selectedYear);
    fetchTotalOrders(selectedYear);
    fetchTotalProductsSold(selectedYear);
    fetchTotalEmployees(selectedYear);
    fetchMonthlyRevenue(selectedYear);
    fetchTopCustomers(selectedYear);
});
//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//   const accessToken = sessionStorage.getItem('accessToken');

//   if (!accessToken) {
//       window.location.href = '../Login/login.html';
//   }
// });