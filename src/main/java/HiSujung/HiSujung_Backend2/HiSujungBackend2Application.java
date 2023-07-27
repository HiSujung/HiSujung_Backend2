package HiSujung.HiSujung_Backend2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HiSujungBackend2Application {

	public static void main(String[] args) {
		SpringApplication.run(HiSujungBackend2Application.class, args);
	}

}
