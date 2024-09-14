document.addEventListener('DOMContentLoaded', () => {
    let slideIndex = 0;
    const categories = ["Nhẫn Kim Cương","Bông Tai Kim Cương"];

    showSlides();

    function showSlides() {
        let slides = document.getElementsByClassName("slide");

        if (slides.length === 0) return;

        for (let i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }

        slideIndex++;
        if (slideIndex > slides.length) {
            slideIndex = 1;
        }

        slides[slideIndex - 1].style.display = "block";
        setTimeout(showSlides, 4000);
    }

    function startSlideshow() {
        const slides = document.querySelectorAll('.slide');
        const slideshowContainers = document.querySelectorAll('.slideshow-container');

        if (slides.length === 0) return;

        slideshowContainers.forEach(container => {
            let index = 0;

            function showSlide() {
                index++;
                if (index >= slides.length) index = 0;
                container.querySelector('.item-grid').style.transform = `translateX(${-index * 100 / 5.95}%)`;
            }

            setInterval(showSlide, 2500);
        });
    }

    startSlideshow();

    //document.getElementById('rings-section').innerHTML = ``;

    categories.forEach((index,count) => {
        console.log(index.toUpperCase(),count);
        Product(index.toUpperCase(),count);
    })

});

function plusSlides(n) {
    slideIndex += n - 1;
    showSlides();
}

function Product(category,count) {
    const minPrice = 0;
    const maxPrice = Number.MAX_SAFE_INTEGER;

    fetch(`${window.base_url}/product-management/products/category/${category}/${minPrice}/${maxPrice}`, {
        method: 'GET',
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;

            if (data) {
                const dataDisplay = data.slice(0, 4); // Lấy 4 sản phẩm đầu tiên
                console.log(dataDisplay);
                if(count === 0){
                    dataDisplay.forEach(index => {
                        const ProductList = document.getElementById('rings-section');

                        const productHTML = `
                        <div class="item-small">
                            <img src="${index.imageURL}" alt="font">
                            <img src="${index.subImageURL}" alt="back">
                            <span class="badge">Giảm giá</span>
                        </div>
                    `;
                        ProductList.insertAdjacentHTML('beforeend', productHTML);
                    });
                }
                else {
                    dataDisplay.forEach(index => {
                        const ProductList = document.getElementById('earrings-item');

                        const productHTML = `
                        <div onclick="productdetail(${index.productId})" class="item-small">
                            <img src="${index.imageURL}" alt="font">
                            <img src="${index.subImageURL}" alt="back">
                            <span class="badge">Giảm giá</span>
                        </div>
                    `;

                        ProductList.insertAdjacentHTML('beforeend', productHTML);
                    });
                }

            }
        })
        .catch(err => {
            console.error(err);
        });
}

function productdetail(id){
    localStorage.setItem('productId',id);
    window.location.href ="/DiamondShopSystem/src/main/resources/templates/User/detail.html"
}

