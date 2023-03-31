package hr.dpilipovic.api.facade;

import hr.dpilipovic.api.service.action.AirConditionerPowerActionInitiatorService;
import hr.dpilipovic.api.service.action.AirConditionerTemperatureActionInitiatorService;
import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirConditionerActionInitiatorFacade {
  
  private final AirConditionerPowerActionInitiatorService airConditionerPowerActionInitiatorService;
  private final AirConditionerTemperatureActionInitiatorService airConditionerTemperatureActionInitiatorService;

  public void turnOn(final Long airConditionerId) {
    airConditionerPowerActionInitiatorService.turnOn(airConditionerId);
  }

  public void turnOff(final Long airConditionerId) {
    airConditionerPowerActionInitiatorService.turnOff(airConditionerId);
  }

  public void setTemperature(final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    airConditionerTemperatureActionInitiatorService.setTemperature(changeTemperatureRequestDto);
  }

}
