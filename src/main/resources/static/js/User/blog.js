document.addEventListener('DOMContentLoaded', function (){
    const token  = localStorage.getItem('authToken')

    fetch(`${window.base_url}/blog-management/blogs`,{
        method: 'GET'
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;
            console.log(data);

            data.forEach(blog => {

            })


        })
})