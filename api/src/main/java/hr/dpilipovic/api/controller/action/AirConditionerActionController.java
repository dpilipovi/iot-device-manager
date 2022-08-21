package hr.dpilipovic.api.controller.action;

import hr.dpilipovic.api.service.action.AirConditionerActionInitiatorService;
import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/air-conditioner/action")
public class AirConditionerActionController {

  private final AirConditionerActionInitiatorService airConditionerActionInitiatorService;

  @PostMapping("/on/{airConditionerId}")
  public ResponseEntity<Void> turnAirConditionerOn(@PathVariable final Long airConditionerId) {
    airConditionerActionInitiatorService.turnOn(airConditionerId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/off/{airConditionerId}")
  public ResponseEntity<Void> turnAirConditionerOff(@PathVariable final Long airConditionerId) {
    airConditionerActionInitiatorService.turnOff(airConditionerId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/temperature")
  public ResponseEntity<Void> changeAirConditionerTemperature(@RequestBody @Valid final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    airConditionerActionInitiatorService.setTemperature(changeTemperatureRequestDto);

    return ResponseEntity.accepted().build();
  }

}
