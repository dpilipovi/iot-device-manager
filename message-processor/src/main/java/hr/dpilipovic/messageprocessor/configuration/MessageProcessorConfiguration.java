package hr.dpilipovic.messageprocessor.configuration;

import hr.dpilipovic.messageprocessor.configuration.properties.DeviceMockProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(DeviceMockProperties.class)
public class MessageProcessorConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
