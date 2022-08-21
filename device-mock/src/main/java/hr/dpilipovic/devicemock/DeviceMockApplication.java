package hr.dpilipovic.devicemock;

import hr.dpilipovic.common.configuration.CommonDataConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(CommonDataConfiguration.class)
@SpringBootApplication
public class DeviceMockApplication {

  public static void main(String[] args) {
    SpringApplication.run(DeviceMockApplication.class, args);
  }

}
