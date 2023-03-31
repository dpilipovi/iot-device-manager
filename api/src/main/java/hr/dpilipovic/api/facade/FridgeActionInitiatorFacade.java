package hr.dpilipovic.api.facade;

import hr.dpilipovic.api.service.action.FridgePowerActionInitiatorService;
import hr.dpilipovic.api.service.action.FridgeTemperatureActionInitiatorService;
import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FridgeActionInitiatorFacade {

  private final FridgePowerActionInitiatorService fridgePowerActionInitiatorService;
  private final FridgeTemperatureActionInitiatorService fridgeTemperatureActionInitiatorService;

  public void turnOn(final Long fridgeId) {
    fridgePowerActionInitiatorService.turnOn(fridgeId);
  }

  public void turnOff(final Long fridgeId) {
    fridgePowerActionInitiatorService.turnOff(fridgeId);
  }

  public void setTemperature(final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    fridgeTemperatureActionInitiatorService.setTemperature(changeTemperatureRequestDto);
  }

}
