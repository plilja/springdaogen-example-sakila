package se.plilja.example.sakila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("se.plilja.example")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}

