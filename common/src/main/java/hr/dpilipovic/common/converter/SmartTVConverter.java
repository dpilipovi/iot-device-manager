package hr.dpilipovic.common.converter;

import hr.dpilipovic.common.dto.SmartTVDto;
import hr.dpilipovic.common.model.SmartTV;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SmartTVConverter implements Converter<SmartTV, SmartTVDto> {

  @Override
  public SmartTVDto convert(final SmartTV smartTV) {
    return SmartTVDto.builder()
        .id(smartTV.getId())
        .name(smartTV.getName())
        .deviceStatus(smartTV.getDeviceStatus())
        .recordingStatus(smartTV.getRecordingStatus())
        .deviceType(smartTV.getDeviceType())
        .lastActivityTime(smartTV.getLastActivityTime())
        .channel(smartTV.getChannel())
        .build();
  }

  public SmartTV convert(final SmartTVDto smartTVDto) {
    return SmartTV.builder()
        .id(smartTVDto.getId())
        .name(smartTVDto.getName())
        .deviceStatus(smartTVDto.getDeviceStatus())
        .recordingStatus(smartTVDto.getRecordingStatus())
        .deviceType(smartTVDto.getDeviceType())
        .lastActivityTime(smartTVDto.getLastActivityTime())
        .channel(smartTVDto.getChannel())
        .build();
  }

}
