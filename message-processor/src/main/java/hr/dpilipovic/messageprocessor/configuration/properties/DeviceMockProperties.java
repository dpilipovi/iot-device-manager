package hr.dpilipovic.messageprocessor.configuration.properties;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "mock.device.base")
public class DeviceMockProperties {

  @NotBlank
  private String url;

}
