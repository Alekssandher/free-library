package alekssandher.free_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfig {
    
    private final Dotenv dotenv;
    private String cloudSecret;
    private String cloudName;
    private String cloudKey;

    public CloudinaryConfig(Dotenv dotenv)
    {
        this.dotenv = dotenv;
        this.cloudName = getEnv("CLOUDINARY_NAME");
        this.cloudKey = getEnv("CLOUDINARY_KEY");
        this.cloudSecret = getEnv("CLOUDINARY_SECRET");
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", cloudKey,
                "api_secret", cloudSecret
        ));
    }

    private String getEnv(String key) {
        return dotenv.get(key) != null ? dotenv.get(key) : System.getenv(key);
    }
}
