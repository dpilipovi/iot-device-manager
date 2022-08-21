package hr.dpilipovic.api.controller.action;

import hr.dpilipovic.api.service.action.FridgeActionInitiatorService;
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
@RequestMapping("/api/device/fridge/action")
public class FridgeActionController {

  private final FridgeActionInitiatorService fridgeActionInitiatorService;

  @PostMapping("/on/{fridgeId}")
  public ResponseEntity<Void> turnFridgeOn(@PathVariable final Long fridgeId) {
    fridgeActionInitiatorService.turnOn(fridgeId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/off/{fridgeId}")
  public ResponseEntity<Void> turnFridgeOff(@PathVariable final Long fridgeId) {
    fridgeActionInitiatorService.turnOff(fridgeId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/temperature")
  public ResponseEntity<Void> changeFridgeTemperature(@RequestBody @Valid final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    fridgeActionInitiatorService.setTemperature(changeTemperatureRequestDto);

    return ResponseEntity.accepted().build();
  }

}
