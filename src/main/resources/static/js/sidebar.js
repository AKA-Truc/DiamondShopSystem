document.addEventListener('DOMContentLoaded', function () {
    const sidebar = `
        <div class="sidebar">
            <h2>GOLDIOUNES SHOP</h2>
            <ul class="main-menu">
                <li><a href="./admin.html"><span class="icon"><ion-icon name="home-outline"></ion-icon></span> Trang chủ</a></li>
                <li class="submenu">
                    <a href="customer_management.html"><ion-icon name="people-outline"></ion-icon> Quản lý người dùng</a>
                </li>
                <li class="submenu">
                    <a href="product.html"><ion-icon name="server-outline"></ion-icon> Quản lý sản phẩm</a>
                </li>
                <li class="submenu">
                    <a href="discount.html"><ion-icon name="pricetag-outline"></ion-icon> Mã giảm giá</a>
                </li>
                <li class="submenu">
                    <a href="blog_management.html"><ion-icon name="newspaper-outline"></ion-icon> Quản lý bài viết</a>
                </li>
                <li class="submenu">
                    <a href="diamond.html"><ion-icon name="diamond-outline"></ion-icon> Quản lý kim Cương</a>
                </li>
                <li class="submenu">
                    <a href="#"><ion-icon name="cart-outline"></ion-icon> Bán hàng</a>
                    <ul>
                        <li><a href="./detail.html"><ion-icon name="clipboard-outline"></ion-icon> Quản lý đơn hàng</a></li>
                        <li><a href="./order.html"><ion-icon name="clipboard-outline"></ion-icon> Quản lý hóa đơn</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="./feedback.html"><ion-icon name="notifications-outline"></ion-icon> Phản hồi</a>
                </li>
                <li><a href="./statistical.html"><span class="icon"><ion-icon name="analytics"></ion-icon></span> Thống kê</a></li>               
                <li class="submenu">
                    <a href="#" onclick="logout()"><ion-icon name="log-out-outline"></ion-icon> Đăng xuất</a>
                </li>
            </ul>
        </div>
    `;
    document.body.insertAdjacentHTML('afterbegin', sidebar);
});
