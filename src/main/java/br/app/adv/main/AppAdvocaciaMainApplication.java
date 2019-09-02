package br.app.adv.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AppAdvocaciaMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppAdvocaciaMainApplication.class, args);
	}

}
