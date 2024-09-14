document.addEventListener('DOMContentLoaded', () => {
    const blogForm = document.getElementById("blog-form"); // Updated ID from 'user-form' to 'blog-form'
    const blogTableBody = document.getElementById("blog-table-body"); // Updated ID from 'customer-table-body' to 'blog-table-body'
    const searchInput = document.querySelector('.search-bar');
    const popup1Overlay = document.getElementById('popup1Overlay');
    const token = localStorage.getItem('authToken');
    let isEditing = false;
    let editingBlogId = null;

    function fetchBlogs() {
        fetch(`${window.base_url}/blog-management/blogs`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
            }
        })
            .then(response => response.json())
            .then(result => {
                const data = result.data;

                if (!Array.isArray(data)) {
                    throw new Error("Expected an array but got something else");
                }

                blogTableBody.innerHTML = '';
                data.forEach((blog, index) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${blog.title}</td>
                        <td>${blog.content.substring(0, 50)}...</td>
                        <td class="action-buttons">
                            <button class="edit-btn" onclick="editBlog('${blog.blogId}')"><ion-icon name="create-outline"></ion-icon></button>
                            <button class="delete-btn" data-id="${blog.blogId}"><ion-icon name="trash-outline"></ion-icon></button>
                        </td>
                    `;
                    blogTableBody.appendChild(row);
                });

                addEditEventListeners();
                addDeleteEventListeners();
            })
            .catch(error => console.error("Error fetching blogs:", error));
    }

    function handleSubmit(e) {
        e.preventDefault();
        const formData = new FormData();
        const blog = {
            title: document.getElementById('Title').value,
            content: document.getElementById('content').value
        };

        formData.append('blog', JSON.stringify(blog));

        const imageURL = document.getElementById('image').files[0];
        if (imageURL) {
            formData.append('imageURL', imageURL);
        }

        if (isEditing) {
            // PUT request for update
            fetch(`${window.base_url}/blog-management/blogs/${editingBlogId}`, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    alert('Cập nhật bài viết thành công!');
                    fetchBlogs();
                    popup1Overlay.style.display = 'none';
                    resetFormState();
                })
                .catch(error => console.error("Error updating blog:", error));
        } else {
            // POST request for create
            fetch(`${window.base_url}/blog-management/blogs`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    alert('Tạo bài viết thành công!');
                    fetchBlogs();
                    popup1Overlay.style.display = 'none';
                    resetFormState();
                })
                .catch(error => console.error("Cập nhật bài viết thất bại: ", error));
        }
    }

    function addDeleteEventListeners() {
        const popup = document.getElementById('popup');
        const yesBtn = document.getElementById('yes-btn');
        const noBtn = document.getElementById('no-btn');
        const deleteBtns = document.querySelectorAll('.delete-btn');
        let currentRow;
        let blogIdToDelete;

        deleteBtns.forEach(button => {
            button.addEventListener('click', function () {
                popup.style.display = 'flex';
                currentRow = this.closest('tr');
                blogIdToDelete = this.getAttribute('data-id');
            });
        });

        yesBtn.addEventListener('click', () => {
            if (currentRow && blogIdToDelete) {
                fetch(`${window.base_url}/blog-management/blogs/${blogIdToDelete}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            currentRow.remove();
                            alert('Xóa bài viết thành công');
                        } else {
                            alert('Xóa bài viết thất bại');
                        }
                        popup.style.display = 'none';
                    })
                    .catch(error => {
                        console.error('Error deleting blog:', error);
                        popup.style.display = 'none';
                    });
            }
        });

        noBtn.addEventListener('click', () => {
            popup.style.display = 'none';
        });
    }

    function addEditEventListeners() {
        const editButtons = document.querySelectorAll('button[onclick^="editBlog"]');
        editButtons.forEach(button => {
            button.addEventListener('click', function () {
                const blogId = this.getAttribute('onclick').match(/'(.*?)'/)[1];
                editBlog(blogId);
            });
        });
    }

    function resetFormState() {
        isEditing = false;
        editingBlogId = null;
        blogForm.reset();
    }

    blogForm.addEventListener("submit", handleSubmit);

    searchInput.addEventListener('input', () => {
        const searchText = searchInput.value.trim().toLowerCase();
        const rows = document.querySelectorAll('#blog-table-body tr');

        rows.forEach(row => {
            const blogTitle = row.querySelector('td:nth-child(2)').textContent.toLowerCase();
            row.style.display = blogTitle.includes(searchText) ? 'table-row' : 'none';
        });
    });

    window.editBlog = function(blogId) {
        isEditing = true;
        editingBlogId = blogId;

        fetch(`${window.base_url}/blog-management/blogs/${blogId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(result => {
                const blog = result.data;

                if (blog) {
                    document.getElementById("Title").value = blog.title || '';
                    document.getElementById("content").value = blog.content || '';

                    // Show the form
                    popup1Overlay.style.display = 'flex';
                } else {
                    alert('Không tìm thấy bài viết này');
                }
            })
            .catch(error => {
                console.error("Error fetching blog details:", error);
                alert('Không thể truy cập chi tiết bài viết. Hãy thử lại.');
            });
    };

    document.getElementById('openpopup1').addEventListener('click', () => {
        blogForm.reset();
        popup1Overlay.style.display = 'flex';
        resetFormState();
    });

    document.getElementById('closepopup1').addEventListener('click', () => {
        if(confirm("Xác Nhận Hủy?")){
            popup1Overlay.style.display = 'none';
        }
    });

    document.getElementById('cancelButton').addEventListener('click', () => {
        if(confirm("Xác Nhận Hủy?")){
            popup1Overlay.style.display = 'none';
        }
    });

    window.addEventListener('click', (event) => {
        if (event.target === popup1Overlay) {
            if(confirm("Xác Nhận Hủy?")){
                popup1Overlay.style.display = 'none';
            }
        }
    });

    fetchBlogs();
});
