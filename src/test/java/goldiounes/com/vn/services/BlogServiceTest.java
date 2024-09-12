package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.BlogDTO;
import goldiounes.com.vn.models.entities.Blog;
import goldiounes.com.vn.repositories.BlogRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogServiceTest {

    @Mock
    private BlogRepo blogRepo;

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBlogs_BlogsExist_ReturnsListOfBlogDTOs() {
        // Arrange
        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setContent("Test Content");

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setTitle("Test Blog");
        blogDTO.setContent("Test Content");

        List<Blog> blogs = Arrays.asList(blog);
        List<BlogDTO> blogDTOs = Arrays.asList(blogDTO);

        when(blogRepo.findAll()).thenReturn(blogs);
        when(modelMapper.map(blogs, new TypeToken<List<BlogDTO>>() {}.getType())).thenReturn(blogDTOs);

        // Act
        List<BlogDTO> result = blogService.getAllBlogs();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Blog", result.get(0).getTitle());
        verify(blogRepo).findAll();
    }

    @Test
    void getAllBlogs_NoBlogsFound_ThrowsRuntimeException() {
        // Arrange
        when(blogRepo.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> blogService.getAllBlogs());
        verify(blogRepo).findAll();
    }

    @Test
    void getBlogs_ExistingBlog_ReturnsBlogDTO() {
        // Arrange
        int blogId = 1;
        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setContent("Test Content");

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setTitle("Test Blog");
        blogDTO.setContent("Test Content");

        when(blogRepo.findById(blogId)).thenReturn(Optional.of(blog));
        when(modelMapper.map(blog, new TypeToken<BlogDTO>() {}.getType())).thenReturn(blogDTO);

        // Act
        BlogDTO result = blogService.getBlogs(blogId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Blog", result.getTitle());
        verify(blogRepo).findById(blogId);
    }

    @Test
    void getBlogs_NonExistingBlog_ThrowsRuntimeException() {
        // Arrange
        int blogId = 1;
        when(blogRepo.findById(blogId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> blogService.getBlogs(blogId));
        verify(blogRepo).findById(blogId);
    }

    @Test
    void createBlog_ValidBlogDTO_ReturnsCreatedBlogDTO() throws IOException {
        // Arrange
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setTitle("New Blog");
        blogDTO.setContent("New Content");

        Blog blog = new Blog();
        blog.setTitle("New Blog");
        blog.setContent("New Content");

        MultipartFile file = mock(MultipartFile.class);
        String imageURL = "http://example.com/image.jpg";

        when(modelMapper.map(blogDTO, Blog.class)).thenReturn(blog);
        when(blogRepo.findByTitle(blog.getTitle())).thenReturn(null);
        when(fileUploadService.uploadImage(file)).thenReturn(imageURL);
        when(modelMapper.map(blog, BlogDTO.class)).thenReturn(blogDTO);

        // Act
        BlogDTO result = blogService.createBlog(blogDTO, file);

        // Assert
        assertNotNull(result);
        assertEquals("New Blog", result.getTitle());
        assertEquals("New Content", result.getContent());
        assertEquals(imageURL, blog.getUrl());
        verify(blogRepo).save(blog);
        verify(fileUploadService).uploadImage(file);
    }

    @Test
    void createBlog_BlogAlreadyExists_ThrowsRuntimeException() throws IOException {
        // Arrange
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setTitle("Existing Blog");

        Blog blog = new Blog();
        blog.setTitle("Existing Blog");

        when(modelMapper.map(blogDTO, Blog.class)).thenReturn(blog);
        when(blogRepo.findByTitle(blog.getTitle())).thenReturn(blog);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> blogService.createBlog(blogDTO, null));
        verify(blogRepo, never()).save(any(Blog.class));
    }

    @Test
    void deleteBlog_ExistingBlog_ReturnsTrue() {
        // Arrange
        int blogId = 1;
        Blog blog = new Blog();
        blog.setBlogID(blogId);

        when(blogRepo.findById(blogId)).thenReturn(Optional.of(blog));

        // Act
        boolean result = blogService.deleteBlog(blogId);

        // Assert
        assertTrue(result);
        verify(blogRepo).deleteById(blogId);
    }

    @Test
    void deleteBlog_NonExistingBlog_ThrowsRuntimeException() {
        // Arrange
        int blogId = 1;
        when(blogRepo.findById(blogId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> blogService.deleteBlog(blogId));
        verify(blogRepo).findById(blogId);
        verify(blogRepo, never()).deleteById(blogId);
    }

    @Test
    void updateBlog_ExistingBlog_ReturnsUpdatedBlogDTO() throws IOException {
        // Arrange
        int blogId = 1;
        BlogDTO inputDTO = new BlogDTO();
        inputDTO.setTitle("Updated Blog");
        inputDTO.setContent("Updated Content");

        Blog existingBlog = new Blog();
        existingBlog.setBlogID(blogId);
        existingBlog.setTitle("Old Blog");
        existingBlog.setContent("Old Content");

        Blog updatedBlog = new Blog();
        updatedBlog.setBlogID(blogId);
        updatedBlog.setTitle("Updated Blog");
        updatedBlog.setContent("Updated Content");

        BlogDTO outputDTO = new BlogDTO();
        outputDTO.setTitle("Updated Blog");
        outputDTO.setContent("Updated Content");

        MultipartFile file = mock(MultipartFile.class);
        String imageURL = "http://example.com/updated.jpg";

        when(blogRepo.findById(blogId)).thenReturn(Optional.of(existingBlog));
        when(modelMapper.map(inputDTO, Blog.class)).thenReturn(updatedBlog);
        when(file.isEmpty()).thenReturn(false);
        when(fileUploadService.uploadImage(file)).thenReturn(imageURL);
        when(blogRepo.save(any(Blog.class))).thenReturn(updatedBlog);
        when(modelMapper.map(updatedBlog, BlogDTO.class)).thenReturn(outputDTO);

        // Act
        System.out.println("Before calling updateBlog");
        BlogDTO result = blogService.updateBlog(blogId, inputDTO, file);
        System.out.println("After calling updateBlog, result: " + result);

        // Assert
        assertNotNull(result, "The result should not be null");
        if (result != null) {
            System.out.println("Result title: " + result.getTitle());
            System.out.println("Result content: " + result.getContent());
        }
        assertEquals("Updated Blog", result.getTitle(), "The title should be updated");
        assertEquals("Updated Content", result.getContent(), "The content should be updated");
        verify(blogRepo).findById(blogId);
        verify(blogRepo).save(any(Blog.class));
        verify(fileUploadService).uploadImage(file);
    }


    @Test
    void updateBlog_NonExistingBlog_ThrowsRuntimeException() throws IOException {
        // Arrange
        int blogId = 1;
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setTitle("Updated Blog");
        blogDTO.setContent("Updated Content");

        when(blogRepo.findById(blogId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> blogService.updateBlog(blogId, blogDTO, null));
        verify(blogRepo).findById(blogId);
        verify(blogRepo, never()).save(any(Blog.class));
    }
}
