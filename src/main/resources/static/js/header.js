document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('authToken');
    const user = JSON.parse(atob(localStorage.getItem('authToken').split('.')[1]));

    const header = `
        <div class="header1">
            <p class="phone">Phone: (+84) 123 2345 123</p>
            <p class="center" id="center-text">hello</p>
            <p class="address">
                <a style="color:white;text-decoration: none" href="#" id="logic-login"></a>
            </p>
        </div>
        <div class="header">
            <div class="header-content">
                <div class="icons">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <a href="info.html"><i style="color: black;" class="fa-regular fa-user"></i></a>
                    <i class="fa-regular fa-heart"></i>
                    <i style="color: black;" class="fa-solid fa-cart-shopping"></i>
                </div>
                <a href="home.html" class="logo"><img src="/static/images/logo.jpg" alt="logo"></a>
            </div>
        </div>
        <ul id="main-menu">
            <li><a href="about_us.html">VỀ CHÚNG TÔI</a></li>
            <li><a href="products.html">TRANG SỨC KIM CƯƠNG</a></li>
            <li><a href="#">BẢNG GIÁ</a>
                <ul class="sub-menu">
                    <li><a href="list_price.html">BẢNG GIÁ KIM CƯƠNG VIÊN</a></li>
                    <li><a href="#">BẢNG GIÁ VỎ KIM CƯƠNG</a></li>
                </ul>
            </li>
            <li><a href="news.html">TIN TỨC</a></li>
            <li><a href="contact.html">LIÊN HỆ</a></li>
        </ul>
        <!-- search -->
        <div id="search-overlay" class="search-overlay">
            <div class="search-box">
                <input type="text" placeholder="Search...">
                <button>Search</button>
            </div>
        </div>
        <!-- cart -->
        <div id="cart-overlay" class="cart-overlay">
            <div id="cart-box" class="cart-box">
                <h2>Your Shopping Cart</h2>
                <div id="cart-items">
                </div>
                <button onclick="window.location.href='./cart.html'">XEM GIỎ HÀNG</button>
            </div>
        </div>
    `;
    document.body.insertAdjacentHTML('afterbegin', header);

    function loadCartItems() {
        console.log('Token:', token);
        fetch(`${window.base_url}/cart-management/cart-items/${user.userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(result => {
                const data = result.data;
                const cartItemsContainer = document.getElementById('cart-items');
                cartItemsContainer.innerHTML = ''; // Đảm bảo xóa sạch trước khi thêm các mục mới
                console.log(data);

                if (data.length === 0) {
                    cartItemsContainer.innerHTML = '<p>No items in the cart.</p>';
                } else {
                    // Hiển thị tất cả các item (hoặc giới hạn 5 item)
                    data.slice(0, 5).forEach(cartItem => {
                        const cartItemDiv = document.createElement('div');
                        cartItemDiv.className = 'cart-item-content';
                        cartItemDiv.onclick = () => window.location.href = './cart.html'; // Điều hướng đến giỏ hàng khi click

                        // Tạo nội dung HTML cho item
                        cartItemDiv.innerHTML = `
                    <img src="${cartItem.product.imageURL}" alt="${cartItem.product.productName}" class="cart-item-image">
                    <div class="cart-item-info">
                        <p class="cart-item-name">${cartItem.product.productName}</p>
                        <p class="cart-item-quantity">Số lượng: ${cartItem.quantity}</p>
                    </div>
                `;

                        console.log('Appending Cart Item:', cartItemDiv);
                        cartItemsContainer.append(cartItemDiv);  // Thêm mục vào container
                    });

                    // Thêm thông báo nếu có nhiều hơn 5 mục
                    if (data.length > 5) {
                        const messageDiv = document.createElement('div');
                        messageDiv.innerHTML = '<p>Vui lòng vào giỏ hàng để xem đầy đủ.</p>';
                        cartItemsContainer.append(messageDiv);
                    }
                }
            })
            .catch(error => {
                console.error('Error fetching cart items:', error);
            });
    }


    // Gọi lại loadCartItems khi click vào icon giỏ hàng
    const cartIcon = document.querySelector(".fa-cart-shopping");
    cartIcon.addEventListener("click", function () {
        loadCartItems();  // Ensure the latest cart data is fetched
        const cartBox = document.getElementById("cart-box");
        const cartOverlay = document.getElementById("cart-overlay");
        cartBox.style.display = "flex";
        cartOverlay.style.display = "block"; // Ensure overlay is displayed
        setTimeout(() => {
            cartBox.classList.add("active");
            cartOverlay.classList.add("active");
        }, 10);
    });

    // Text rotation for center message
    const textOptions = ["Giảm giá đến 16%", "Trang sức kim cương ưu đãi 40%", "Mặt dây chuyền ưu đãi 15%"];
    const centerText = document.getElementById("center-text");
    let textIndex = 0;

    function rotateText() {
        centerText.classList.remove('visible');
        setTimeout(() => {
            centerText.textContent = textOptions[textIndex];
            centerText.classList.add('visible');
            textIndex = (textIndex + 1) % textOptions.length;
        }, 1000);
    }

    setInterval(rotateText, 2000);
    rotateText();

    // Search icon click event
    const searchIcon = document.querySelector(".fa-magnifying-glass");
    const searchOverlay = document.getElementById("search-overlay");
    const cartOverlay = document.getElementById("cart-overlay");
    const cartBox = document.getElementById("cart-box");

    // Show the search overlay with transition
    searchIcon.addEventListener("click", function () {
        searchOverlay.style.display = "flex";
        setTimeout(() => {
            searchOverlay.classList.add("active");
        }, 10);
    });

    // Hide the overlays when clicking outside the content boxes
    document.addEventListener("click", function (e) {
        if (e.target === searchOverlay || !searchOverlay.contains(e.target) && e.target !== searchIcon) {
            searchOverlay.classList.remove("active");
            setTimeout(() => {
                searchOverlay.style.display = "none";
            }, 500);
        }
        if (e.target === cartBox || !cartBox.contains(e.target) && e.target !== cartIcon) {
            cartBox.classList.remove("active");
            setTimeout(() => {
                cartBox.style.display = "none";
                cartOverlay.style.display = "none";
            }, 200);
        }
    });

    // Hide the overlays when pressing the Escape key
    document.addEventListener("keydown", function (e) {
        if (e.key === "Escape") {
            searchOverlay.classList.remove("active");
            cartBox.classList.remove("active");
            setTimeout(() => {
                searchOverlay.style.display = "none";
                cartOverlay.style.display = "none";
                cartBox.style.display = "none";
            }, 200);
        }
    });

    // Login/Logout logic
    if (localStorage.getItem('authToken')) {
        document.getElementById('logic-login').textContent = "Đăng Xuất";
        document.getElementById('logic-login').addEventListener('click', logout);
    } else {
        document.getElementById('logic-login').textContent = "Đăng Nhập";
        document.getElementById('logic-login').href = "../login.html";
    }
});
