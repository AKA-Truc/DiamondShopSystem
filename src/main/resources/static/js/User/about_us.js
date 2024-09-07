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
