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
document.addEventListener("DOMContentLoaded", function () {
    const searchIcon = document.querySelector(".fa-magnifying-glass");
    const searchOverlay = document.getElementById("search-overlay");
    const cartIcon = document.querySelector(".fa-cart-shopping");
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
        cartOverlay.style.display = "flex";
        setTimeout(() => {
            cartOverlay.classList.add("active");
        }, 10);
    });
    console.log(cartIcon)
    // Hide the overlays when clicking outside the content boxes
    document.addEventListener("click", function (e) {
        if (e.target === searchOverlay || !searchOverlay.contains(e.target) && e.target !== searchIcon) {
            searchOverlay.classList.remove("active");
            setTimeout(() => {
                searchOverlay.style.display = "none";
            }, 500); // Matches the transition duration
        }
        if (e.target === cartOverlay || !cartOverlay.contains(e.target) && e.target !== cartIcon) {
            cartOverlay.classList.remove("active");
            setTimeout(() => {
                cartOverlay.style.display = "none";
            }, 500);
        }
    });

    document.addEventListener("keydown", function (e) {
        if (e.key === "Escape") {
            searchOverlay.classList.remove("active");
            cartOverlay.classList.remove("active");
            setTimeout(() => {
                searchOverlay.style.display = "none";
                cartOverlay.style.display = "none";
            }, 500);
        }
    });
});

// slide show
let slideIndex = 0;
showSlides();

function showSlides() {
    let slides = document.getElementsByClassName("slide");
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slides.length) {
        slideIndex = 1;
    }
    slides[slideIndex - 1].style.display = "block";
    setTimeout(showSlides, 2000); // Change slide every 2 seconds
}

function plusSlides(n) {
    slideIndex += n - 1;
    showSlides();
}

function startSlideshow() {
    const slides = document.querySelectorAll('.slide');
    const slideshowContainers = document.querySelectorAll('.slideshow-container');

    slideshowContainers.forEach(container => {
        let index = 0;

        function showSlide() {
            index++;
            if (index >= slides.length - 1) index = 0;
            container.querySelector('.item-grid').style.transform = `translateX(${-index * 100 / 5.95}%)`;
        }

        setInterval(showSlide, 2500); // Change slide every 3 seconds
    });
}

document.addEventListener('DOMContentLoaded', startSlideshow);


