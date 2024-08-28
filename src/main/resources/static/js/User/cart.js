const textOptions = ["Giảm giá đến 16%", "Trang sức kim cương ưu đãi 40%", "Mặt dây chuyền ưu đãi 15%"];
const centerText = document.getElementById("center-text");
let textIndex = 0;

function rotateText() {
    centerText.classList.remove('visible');
    setTimeout(() => {
        centerText.textContent = textOptions[textIndex];
        centerText.classList.add('visible');
        textIndex = (textIndex + 1) % textOptions.length;
    }, 1000); // Wait for the fade-out effect before changing text
}

setInterval(rotateText, 3000);
rotateText();
function calculateCartTotal() {
    const cartRows = document.querySelectorAll('.cart-table tbody tr');
    let subtotal = 0;


    cartRows.forEach(row => {
        const priceElement = row.querySelector('#price');
        const quantityInput = row.querySelector('.quantity-input');
        const price = parseInt(priceElement.textContent.replace(/[^0-9]/g, ''));
        const quantity = parseInt(quantityInput.value);


        const total = price * quantity;


        subtotal += total;


        const totalElement = row.querySelector('td:last-child');
        totalElement.textContent = new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(total);
    });

    // Cập nhật phần "Tạm tính" và "Tổng" trong giỏ hàng
    document.getElementById('PriceName').textContent = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(subtotal);

    const grandTotalElement = document.querySelector('.cart-summary-row:last-child span:last-child');
    grandTotalElement.textContent = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(subtotal);
}


calculateCartTotal();


document.querySelectorAll('.quantity-input').forEach(input => {
    input.addEventListener('change', calculateCartTotal);
});

// icon click
document.addEventListener("DOMContentLoaded", function () {
    const searchIcon = document.querySelector(".fa-magnifying-glass");
    const searchOverlay = document.getElementById("search-overlay");
    const cartIcon = document.querySelector(".fa-cart-shopping");
    const cartBox = document.getElementById("cart-box");
    const cartOverlay = document.getElementById("cart-overlay");


    searchIcon.addEventListener("click", function () {
        searchOverlay.style.display = "flex";
        setTimeout(() => {
            searchOverlay.classList.add("active");
        }, 10);
    });

    cartIcon.addEventListener("click", function () {
        cartBox.style.display = "flex";
        cartOverlay.style.display = "block";
        setTimeout(() => {
            cartBox.classList.add("active");
            cartOverlay.classList.add("active");
        }, 10);
    });

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
