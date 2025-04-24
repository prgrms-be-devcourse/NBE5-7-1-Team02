package easy.gc_coffee_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GcCoffeeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GcCoffeeApiApplication.class, args);
    }

}
