package hr.dpilipovic.api;

import hr.dpilipovic.common.configuration.CommonDataConfiguration;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({CommonDataConfiguration.class, RabbitQueueConfiguration.class})
@SpringBootApplication
public class ApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

}
