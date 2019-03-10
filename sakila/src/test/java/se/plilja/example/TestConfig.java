package se.plilja.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import se.plilja.example.dbframework.CurrentUserProvider;
import se.plilja.example.model.ActorDao;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@ContextConfiguration(classes = ActorDao.class)
//@ComponentScan(value = "se.plilja.example", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value =Config.class)})
public class TestConfig {

    @Primary
    @Bean
    public DataSource dataSource() {
        String file = getClass().getClassLoader().getResource("init.sql").getFile();
        return DataSourceBuilder.create()
                .url(String.format("jdbc:h2:mem:db;INIT=RUNSCRIPT FROM '%s'", file))
                .driverClassName("org.h2.Driver")
                .username("docker")
                .password("docker")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public CurrentUserProvider currentUserProvider() {
        return () -> "Foo";
    }
}
