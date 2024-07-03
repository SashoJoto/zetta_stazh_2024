package dev.zettalove.zettalove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZettaloveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZettaloveApplication.class, args);
	}

}
