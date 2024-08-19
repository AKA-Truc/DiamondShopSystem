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

// Review
document.addEventListener('DOMContentLoaded', () => {
    const reviewsWrapper = document.querySelector('.reviews-wrapper');
    const reviewItems = document.querySelectorAll('.review-item');
    const totalReviews = reviewItems.length;
    const visibleReviews = 3; // Number of reviews visible at one time
    let currentIndex = 0;

    function updateSlider() {
        const offset = -currentIndex * (100 / visibleReviews);
        reviewsWrapper.style.transform = `translateX(${offset}%)`;
    }

    function goToNextReview() {
        if (currentIndex < totalReviews - visibleReviews) {
            currentIndex++;
        } else {
            currentIndex = 0;
        }
        updateSlider();
    }

    function goToPrevReview() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = totalReviews - visibleReviews;
        }
        updateSlider();
    }

    // Auto swipe every 2 seconds
    setInterval(goToNextReview, 2000);

    // Initialize
    updateSlider();
});