package goldiounes.com.vn.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileUploadService {

    private final Cloudinary cloudinary;

    public FileUploadService(@Value("${cloudinary.cloud.name}") String cloudName,
                             @Value("${cloudinary.api.key}") String apiKey,
                             @Value("${cloudinary.api.secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url"); // Trả về URL của ảnh đã được upload
    }
}

