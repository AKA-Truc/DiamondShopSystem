document.addEventListener('DOMContentLoaded', function (){
    const token = localStorage.getItem('authToken');
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    const roles = tokenPayload.roles;
    const role = roles[0];
    console.log(role);

    if (role !== 'ROLE_DELIVERY') {
        alert("Bạn Không Có Quyền Truy Cập Vào Trang Này");
        window.location.href = '/DiamondShopSystem/src/main/resources/templates/Admin/admin.html';
    }
})