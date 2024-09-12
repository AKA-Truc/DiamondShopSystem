document.addEventListener('DOMContentLoaded', function (){
    const token = localStorage.getItem('authToken');
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    const roles = tokenPayload.roles;
    const role = roles[0];
    console.log(role);

    if (token && role !== 'ROLE_CUSTOMER') {
        window.location.href = '/DiamondShopSystem/src/main/resources/templates/User/about_us.html';
    }
})