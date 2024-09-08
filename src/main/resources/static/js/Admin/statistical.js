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

    // Add event listeners for month and year select elements
    document.getElementById('select-month').addEventListener('change', updateData);
    document.getElementById('select-year').addEventListener('change', updateData);
});

function updateData() {
    fetchRevenueByMonthAndYear();
    fetchTotalOrdersByYear();
}

// Fetch Top Selling Products
async function fetchTopSellingProducts() {
    try {
        const response = await fetch(`${window.base_url}/dashboard/product-topSelling`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('authToken')}` // Add your token if needed
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        const topProductsList = document.getElementById('top-products-list');
        topProductsList.innerHTML = ''; // Clear existing list

        // Assuming the API returns an array of arrays with [ProductName, TotalQuantity]
        data.data.forEach(product => {
            let listItem = `<li>${product[0]} <span>${product[1]}</span></li>`;
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
            'Authorization': 'Bearer ' + localStorage.getItem('authToken') // Add your token if needed
        }
    })
        .then(response => response.json())
        .then(data => {
            // Assuming you have a chart to update with the total orders data
            const orderChart = document.getElementById('order-by-year-chart');
            // Update the chart or UI here
        })
        .catch(error => console.error('Error fetching total orders by year:', error));
}

// Fetch Customer Data by Gender
function fetchCustomerDataByGender() {
    fetch(`${window.base_url}/dashboard/customer/gender`, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken')
        }
    })
        .then(response => response.json())
        .then(data => {
            const genderChart = document.getElementById('customer-gender-chart');
            // Use Chart.js to update the gender chart here
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

//ràng buộc token
// document.addEventListener('DOMContentLoaded', function() {
//   const accessToken = sessionStorage.getItem('accessToken');

//   if (!accessToken) {
//       window.location.href = '../Login/login.html';
//   }
// });