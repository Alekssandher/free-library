package alekssandher.free_library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    
    @Value("${CLOUDINARY_SECRET:NOT_SET}")
    private String cloudSecret;

    @Value("${CLOUDINARY_NAME:NOT_SET}")
    private String cloudName;

    @Value("${CLOUDINARY_KEY:NOT_SET}")
    private String cloudKey;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", cloudKey,
                "api_secret", cloudSecret
        ));
    }

}
