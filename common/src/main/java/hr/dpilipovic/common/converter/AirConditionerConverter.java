package hr.dpilipovic.common.converter;

import hr.dpilipovic.common.dto.AirConditionerDto;
import hr.dpilipovic.common.model.AirConditioner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AirConditionerConverter implements Converter<AirConditioner, AirConditionerDto> {

  @Override
  public AirConditionerDto convert(final AirConditioner airConditioner) {
    return AirConditionerDto.builder()
        .id(airConditioner.getId())
        .name(airConditioner.getName())
        .deviceStatus(airConditioner.getDeviceStatus())
        .deviceType(airConditioner.getDeviceType())
        .lastActivityTime(airConditioner.getLastActivityTime())
        .temperature(airConditioner.getTemperature())
        .build();
  }

  public AirConditioner convert(final AirConditionerDto airConditionerDto) {
    return AirConditioner.builder()
        .id(airConditionerDto.getId())
        .name(airConditionerDto.getName())
        .deviceStatus(airConditionerDto.getDeviceStatus())
        .deviceType(airConditionerDto.getDeviceType())
        .lastActivityTime(airConditionerDto.getLastActivityTime())
        .temperature(airConditionerDto.getTemperature())
        .build();
  }

}
