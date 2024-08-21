document.addEventListener("DOMContentLoaded", () => {
    // Function to fetch order details by ID
    const fetchOrder = () => {
        // Get the order ID from a hidden input field or URL parameter
        const id = document.getElementById("id").value;
        fetch(`http://localhost:8080/order-management/orders/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch order");
                }
                return response.json();
            })
            .then(responseData => {
                console.log("Raw response data fetched: ", responseData);

                // Check if the response data is in the expected format
                if (!responseData || !responseData.data) {
                    console.error('Expected an object with a "data" property but received:', responseData);
                    return;
                }

                // Extract data from the response
                const order = responseData.data;

                // Update HTML content with fetched order details
                document.getElementById("orderId").textContent = order.orderId;
                document.getElementById("userName").textContent = order.user.userName;
                document.getElementById("productImage").src = order.orderDetails[0].product.imageURL;
                document.getElementById("productName").textContent = order.orderDetails[0].product.productName;
                document.getElementById("productCategory").textContent = order.orderDetails[0].product.category.categoryName;
                document.getElementById("productQuantity").textContent = order.orderDetails[0].quantity;
            })
            .catch(error => {
                console.error("Error fetching order:", error);
                // Optionally display an error message to the user
            });
    };

    // Call fetchOrder function on page load
    fetchOrder();
});
