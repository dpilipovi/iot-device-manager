package hr.dpilipovic.common.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordingRequestDto {

  @NotNull
  private Long id;

  @NotNull
  private Integer channel;

}
