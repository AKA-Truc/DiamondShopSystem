document.addEventListener('DOMContentLoaded', () => {
    let slideIndex = 0;

    // Khởi động slide chỉ sau khi DOM đã sẵn sàng
    showSlides();

    function showSlides() {
        let slides = document.getElementsByClassName("slide");

        if (slides.length === 0) return; // Kiểm tra xem có slide nào không

        for (let i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";  // Ẩn tất cả các slide
        }

        slideIndex++;
        if (slideIndex > slides.length) {
            slideIndex = 1;  // Nếu vượt quá số slide, quay lại slide đầu tiên
        }

        slides[slideIndex - 1].style.display = "block";  // Hiển thị slide hiện tại
        setTimeout(showSlides, 4000); // Đổi slide sau 2 giây
    }

    function plusSlides(n) {
        slideIndex += n - 1;
        showSlides();
    }

    function startSlideshow() {
        const slides = document.querySelectorAll('.slide');
        const slideshowContainers = document.querySelectorAll('.slideshow-container');

        if (slides.length === 0) return;  // Kiểm tra nếu không có slide nào

        slideshowContainers.forEach(container => {
            let index = 0;

            function showSlide() {
                index++;
                if (index >= slides.length) index = 0;
                container.querySelector('.item-grid').style.transform = `translateX(${-index * 100 / 5.95}%)`;
            }

            setInterval(showSlide, 2500); // Đổi slide sau 2.5 giây
        });
    }

    startSlideshow();  // Khởi động slideshow khi DOM đã sẵn sàng
});
