package alekssandher.free_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import alekssandher.free_library.utils.Snowflake;

@Configuration
public class SnowFlakeConfig {
    
    @Bean
    public Snowflake snowflake()
    {
        return new Snowflake();
    }
}
