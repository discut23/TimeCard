package group1.artifact1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TimeCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeCardApplication.class, args);

	}

}
