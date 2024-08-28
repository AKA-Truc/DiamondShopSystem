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

setInterval(rotateText, 3000);
rotateText(); // Initialize text

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
//
document.getElementById("kienthuc-link").addEventListener("click", function () {
    document.getElementById("dautu-content").classList.remove("active");
    document.getElementById("sukien-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.remove("active");
    document.getElementById("kienthuc-content").classList.add("active");
});

document.getElementById("dautu-link").addEventListener("click", function () {
    document.getElementById("kienthuc-content").classList.remove("active");
    document.getElementById("sukien-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.remove("active");
    document.getElementById("dautu-content").classList.add("active");
});

document.getElementById("sukien-link").addEventListener("click", function () {
    document.getElementById("dautu-content").classList.remove("active");
    document.getElementById("kienthuc-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.remove("active");
    document.getElementById("sukien-content").classList.add("active");
});

document.getElementById("thongtin-link").addEventListener("click", function () {
    document.getElementById("dautu-content").classList.remove("active");
    document.getElementById("kienthuc-content").classList.remove("active");
    document.getElementById("sukien-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.add("active");
});

document.getElementById("khuyenmai-link").addEventListener("click", function () {
    document.getElementById("dautu-content").classList.remove("active");
    document.getElementById("kienthuc-content").classList.remove("active");
    document.getElementById("sukien-content").classList.remove("active");
    document.getElementById("thongtin-content").classList.remove("active");
    document.getElementById("khuyenmai-content").classList.add("active");
});