document.addEventListener('DOMContentLoaded', async function () {
    // Gọi hàm fetchBlog và chờ dữ liệu trả về
    mainDisplay();
});
async function mainDisplay(){
    const data = await fetchBlog();
    console.log(data);

    if(!localStorage.getItem('blogid')){
        localStorage.setItem('blogid', data[0].blogId);
    }

    displayBlog(data);
}

async function fetchBlog() {
    const token = localStorage.getItem('authToken');

    try {
        const response = await fetch(`${window.base_url}/blog-management/blogs`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        const result = await response.json();
        const data = result.data;

        if (Array.isArray(data) && data.length > 0) {
            return data;
        } else {
            console.log("No blogs found.");
            return [];
        }
    } catch (error) {
        console.error('Error fetching blog list:', error);
        return [];
    }
}

function displayBlog(data) {
    const mainBanner = document.getElementById('main-banner');
    const blogList = document.getElementById('blog-list');

    // Xóa tất cả các phần tử trong danh sách blog trước khi thêm mới
    blogList.innerHTML = '';

    data.forEach(blog => {
        if (blog.blogId == localStorage.getItem('blogid')) {
            // Hiển thị blog được chọn trong phần main banner
            const processedContent = blog.content.replaceAll('\n', '<br>');

            mainBanner.innerHTML = `
                <img src="${blog.url}" alt="Main Banner">
                <div style="margin-top: 40px" class="banner-title">
                    <a style="color: black; text-decoration: none; font-size: 30px;" href="/blog/${blog.id}">
                        ${blog.title}
                    </a>
                </div>
                <div style="margin-top: 30px" class="banner-content">
                    <p style="color: black; text-decoration: none; font-size: 20px;">
                        ${processedContent}
                    </p>
                </div>
            `;
        } else {
            // Thêm blog vào danh sách
            const blogItem = document.createElement('div');
            blogItem.classList.add('blog-item');
            blogItem.innerHTML = `
                <img src="${blog.url}" alt="${blog.title}" class="blog-image">
                <h4><a href="#" onclick="oneBlog(${blog.blogId}); return false;">${blog.title}</a></h4>
            `;
            blogList.appendChild(blogItem);
        }
    });
}

function oneBlog(id) {
    localStorage.removeItem('blogid');
    localStorage.setItem('blogid',id);

    mainDisplay();
}