package com.anime.guessanime;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GuessanimeApplication {
	public static void main(String[] args) {
		//Definir as variaveis de ambiente
		Dotenv dotenv = Dotenv.load();
		System.setProperty("spring.datasource.url",dotenv.get("DB_URL"));
		System.setProperty("spring.datasource.username",dotenv.get("DB_USERNAME"));
		System.setProperty("spring.datasource.password",dotenv.get("DB_PASSWORD"));


		SpringApplication app = new SpringApplication(GuessanimeApplication.class);
		app.setAdditionalProfiles("dev");
		app.run(args);
	}
	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		return factory -> factory.setPort(5050);
	}

}
