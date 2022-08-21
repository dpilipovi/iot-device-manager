package hr.dpilipovic.common.dto;

import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.model.codebook.RecordingStatus;
import hr.dpilipovic.common.util.OnCreate;
import hr.dpilipovic.common.util.OnUpdate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmartTVDto {

  @Null(groups = OnCreate.class)
  @NotNull(groups = OnUpdate.class)
  private Long id;

  @NotBlank
  private String name;

  @Null
  private DeviceType deviceType;

  @Null
  private DeviceStatus deviceStatus;

  @Null
  private RecordingStatus recordingStatus;

  @Null
  private Integer channel;

  @Null
  private LocalDateTime lastActivityTime;

}
