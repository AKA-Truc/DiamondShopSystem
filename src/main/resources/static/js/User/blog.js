document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('authToken');

    fetch(`${window.base_url}/blog-management/blogs`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(result => {
            const data = result.data;
            console.log(data);

            if (data.length > 0) {
                const mainBanner = document.getElementById('main-banner');
                const blogList = document.getElementById('blog-list');

                // Lấy bài blog đầu tiên
                const firstBlog = data[0];

                // Hiển thị bài blog đầu tiên trong phần main banner
                mainBanner.innerHTML = `
                <a href="/blog/${firstBlog.id}" target="_self">
                    <img src="${firstBlog.url}" alt="Main Banner">
                </a>
                <div style="margin-top: 40px" class="banner-title">
                    <a style="color: black; text-decoration: none; font-size: 30px;" href="/blog/${firstBlog.id}">
                        ${firstBlog.title}
                    </a>
                </div>
                <div style="margin-top: 30px" class="banner-content">
                    <p style="color: black; text-decoration: none; font-size: 20px;">
                        ${firstBlog.content}
                    </p>
                </div>
            `;

                const remainingBlogs = data.slice(1);

                remainingBlogs.forEach(blog => {
                    const blogItem = document.createElement('div');
                    blogItem.classList.add('blog-item');
                    blogItem.innerHTML = `
                    <img src="${blog.url}" alt="${blog.title}" class="blog-image">
                    <h4><a href="/blog/${blog.id}" target="_blank">${blog.title}</a></h4>
                    <p>${blog.shortDescription}</p>
                `;
                    blogList.appendChild(blogItem);
                });
            } else {
                console.log("No blogs found.");
            }
        })
        .catch(error => {
            console.error('Error fetching blog list:', error);
        });
});
