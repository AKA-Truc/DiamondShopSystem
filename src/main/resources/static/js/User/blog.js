document.addEventListener('DOMContentLoaded', async function () {
    localStorage.setItem('contentVisible', 'false')
    mainDisplay(); // Gọi hàm fetchBlog và hiển thị

    if (document.referrer !== window.location.href) {
        localStorage.removeItem('blogid');
    }
});

async function mainDisplay() {
    const data = await fetchBlog();

    if (data.length === 0) {
        displayNoBlogs();
    } else {
        if (!localStorage.getItem('blogid')) {
            localStorage.setItem('blogid', data[data.length - 1].blogId);
        }
        displayBlog(data);
    }
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
    mainBanner.innerHTML = '';

    data.forEach(blog => {
        if (blog.blogId === Number(localStorage.getItem('blogid'))){
            const processedContent = blog.content.replaceAll('\n', '<br>');
            const isContentVisible = localStorage.getItem('contentVisible') === 'true'; // Kiểm tra trạng thái mở

            mainBanner.innerHTML = `
                <img src="${blog.url}" alt="Main Banner">
                <div style="margin-top: 40px" class="banner-title">
                    <a style="color: black; text-decoration: none; font-size: 30px;" href="#}">
                        ${blog.title}
                    </a>
                </div>
                <div id="banner-content" style="margin-top: 30px; ${isContentVisible ? 'display: block;' : 'display: none;'}" class="banner-content">
                    <p style="color: black; text-decoration: none; font-size: 20px;">
                        ${processedContent}
                    </p>
                </div>
            `;

            const newMainBanner = mainBanner.cloneNode(true);
            mainBanner.parentNode.replaceChild(newMainBanner, mainBanner);

            newMainBanner.addEventListener('click', function () {
                const content = document.getElementById('banner-content');
                if (content.style.display === 'none' || content.style.display === '') {
                    content.style.display = 'flex';
                    localStorage.setItem('contentVisible', 'true');
                }
            });
        } else {
            // Thêm blog vào danh sách
            const blogItem = document.createElement('div');
            blogItem.classList.add('blog-item');
            blogItem.innerHTML = `
                <img src="${blog.url}" alt="${blog.title}" class="blog-image">
                <h4 id = "blog-title">${blog.title}</h4>
            `;
            // Gắn sự kiện click vào toàn bộ phần tử blog-item
            blogItem.addEventListener('click', function() {
                oneBlog(blog.blogId);
            });
            blogList.appendChild(blogItem);
        }
    });
}


function displayNoBlogs() {
    const mainBanner = document.getElementById('main-banner');
    const blogList = document.getElementById('blog-list');

    // Hiển thị thông báo "Không có blog nào" trong phần chính
    mainBanner.innerHTML = `
        <div style="margin-top: 40px; text-align: center;">
            <h2>Hiện không có blog nào</h2>
        </div>
    `;

    // Hiển thị thông báo "Không có blog nào" trong danh sách blog
    blogList.innerHTML = `
        <div style="text-align: center;">
            <p>Hiện không có blog nào.</p>
        </div>
    `;
}

function oneBlog(id) {
    localStorage.setItem('blogid', id); // Cập nhật blog ID
    mainDisplay();
}
