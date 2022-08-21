package hr.dpilipovic.common.converter;

import hr.dpilipovic.common.dto.FridgeDto;
import hr.dpilipovic.common.model.Fridge;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FridgeConverter implements Converter<Fridge, FridgeDto> {

  @Override
  public FridgeDto convert(final Fridge fridge) {
    return FridgeDto.builder()
        .id(fridge.getId())
        .name(fridge.getName())
        .deviceStatus(fridge.getDeviceStatus())
        .deviceType(fridge.getDeviceType())
        .lastActivityTime(fridge.getLastActivityTime())
        .temperature(fridge.getTemperature())
        .build();
  }

  public Fridge convert(final FridgeDto fridgeDto) {
    return Fridge.builder()
        .id(fridgeDto.getId())
        .name(fridgeDto.getName())
        .deviceStatus(fridgeDto.getDeviceStatus())
        .deviceType(fridgeDto.getDeviceType())
        .lastActivityTime(fridgeDto.getLastActivityTime())
        .temperature(fridgeDto.getTemperature())
        .build();
  }

}
