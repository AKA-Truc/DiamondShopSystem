document.addEventListener('DOMContentLoaded', function () {
    const footer = `
        <footer class="footer">
            <div class="grid">
                <div class="grid_row">
                    <div class="grid__column-2-4">
                        <h3 class="footer-heading">Danh mục</h3>
                        <ul class="footer__list">
                            <li>
                                <a href="products.html">Bông tai kim cương</a>
                                <a href="products.html">Lắc tay kim cương</a>
                                <a href="products.html">Nhẫn kim cương</a>
                                <a href="products.html">Dây chuyền kim cương</a>
                                <a href="products.html">Mặt dây chuyền kim cương</a>
                            </li>
                        </ul>
                    </div>
                    <div class="grid__column-2-4">
                        <h3 class="footer-heading">Liên hệ </h3>
                        <ul class="footer__list">
                            <li>
                                <a href="/static/css/User/about_us.css">Về chúng tôi</a>
                                <a href="#">Tuyển dụng</a>
                                <a href="#">Chính sách đổi trả</a>
                                <a href="#">Chính sách bảo hành</a>
                                <a href="#">Chính sách vận chuyển</a>
                            </li>
                        </ul>
                    </div>
                    <div class="grid__column-2-4">
                        <h3 class="footer-heading">Đăng ký ngay</h3>
                        <ul class="footer__list">
                            <li>
                                <a href="">Để nhận được các tin tức thời trang mới nhất cùng với ưu đãi khuyến mãi.</a>
                                <form action="">
                                    <input type="text" id="email" name="email" placeholder="Email">
                                    <input type="submit" value="ĐĂNG KÝ NGAY">
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
    `;
    document.body.insertAdjacentHTML('beforeend', footer);
});
