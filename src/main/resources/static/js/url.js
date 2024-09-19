window.base_url = "http://localhost:8080";

function logout() {
    fetch(`${window.base_url}/user-management/logout`,{
        method: 'DELETE',
        headers:{
            'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(data =>{
            localStorage.removeItem('authToken');
            window.location.href = '/DiamondShopSystem/src/main/resources/templates/login.html';
        })
        .catch(error=>{
            console.log(error);})
}