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


