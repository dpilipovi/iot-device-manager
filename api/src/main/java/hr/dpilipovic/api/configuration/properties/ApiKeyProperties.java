package hr.dpilipovic.api.configuration.properties;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api.security")
public class ApiKeyProperties {

  @NotBlank
  private String apiKey;

}
