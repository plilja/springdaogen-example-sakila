package se.plilja.example.sakila;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.plilja.example.dbframework.CurrentUserProvider;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.sql.DataSource;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class Config implements WebMvcConfigurer {

    @Bean
    public DataSource dataSource(
            @Value("${database.driver}") String dbDriver,
            @Value("${database.url}") String dbUrl,
            @Value("${database.user}") String dbUser,
            @Value("${database.password}") String dbPass
    ) {
        return DataSourceBuilder.create()
                .url(dbUrl)
                .username(dbUser)
                .password(dbPass)
                .driverClassName(dbDriver)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        var o = new ObjectMapper();
        o.findAndRegisterModules();
        o.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return o;
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("se.plilja.example"))
                .paths(regex("/.*"))
                .build();

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public CurrentUserProvider currentUserProvider() {
        return () -> "some_user_name";
    }

}
