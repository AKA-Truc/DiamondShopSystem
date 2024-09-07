document.addEventListener('DOMContentLoaded', function () {
    const header = `
        <div class="header1">
            <p class="phone">Phone: (+84) 123 2345 123</p>
            <p class="center" id="center-text">hello</p>
            <p class="address">
                <a style="color:white;text-decoration: none" href="../login.html">Đăng nhập</a>
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
            <li><a href="about_us.html">VỀ CHÚNG TÔI </a></li>
            <li><a href="products.html">TRANG SỨC KIM CƯƠNG </a></li>
            <li><a href="">BẢNG GIÁ</a>
                <ul class="sub-menu">
                    <li><a href="list_price.html">BẢNG GIÁ KIM CƯƠNG VIÊN</a></li>
                    <li><a href="">BẢNG GIÁ VỎ KIM CƯƠNG</a></li>
                </ul>
            </li>
            <li><a href="promotion.html">KHUYẾN MÃI</a></li>
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
                <p>No items in the cart.</p>
                <button onclick="window.location.href='./cart.html'">XEM GIỎ HÀNG</button>
            </div>
        </div>
    `;
    document.body.insertAdjacentHTML('afterbegin', header);

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

    // icon click
    const searchIcon = document.querySelector(".fa-magnifying-glass");
    const searchOverlay = document.getElementById("search-overlay");
    const cartIcon = document.querySelector(".fa-cart-shopping");
    const cartBox = document.getElementById("cart-box");
    const cartOverlay = document.getElementById("cart-overlay");

    // Show the search overlay with transition
    searchIcon.addEventListener("click", function () {
        searchOverlay.style.display = "flex";
        setTimeout(() => {
            searchOverlay.classList.add("active");
        }, 10); // Slight delay to allow display to take effect
    });

    // Show the cart overlay with transition
    cartIcon.addEventListener("click", function () {
        cartBox.style.display = "flex";
        cartOverlay.style.display = "block"; // Ensure overlay is displayed
        setTimeout(() => {
            cartBox.classList.add("active");
            cartOverlay.classList.add("active");
        }, 10);
    });

    // Hide the overlays when clicking outside the content boxes
    document.addEventListener("click", function (e) {
        if (e.target === searchOverlay || !searchOverlay.contains(e.target) && e.target !== searchIcon) {
            searchOverlay.classList.remove("active");
            setTimeout(() => {
                searchOverlay.style.display = "none";
            }, 500); // Matches the transition duration
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
});
