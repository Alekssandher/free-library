package alekssandher.free_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FreeLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreeLibraryApplication.class, args);
	}

}
