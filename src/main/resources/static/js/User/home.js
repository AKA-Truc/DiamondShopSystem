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


