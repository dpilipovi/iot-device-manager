package hr.dpilipovic.api.configuration;

import hr.dpilipovic.api.configuration.properties.ApiKeyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApiKeyProperties.class)
public class ApiPropertiesConfiguration {

}
