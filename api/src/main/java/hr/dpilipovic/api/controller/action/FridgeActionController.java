package hr.dpilipovic.api.controller.action;

import hr.dpilipovic.api.facade.FridgeActionInitiatorFacade;
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

  private final FridgeActionInitiatorFacade fridgeActionInitiatorFacade;

  @PostMapping("/on/{fridgeId}")
  public ResponseEntity<Void> turnFridgeOn(@PathVariable final Long fridgeId) {
    fridgeActionInitiatorFacade.turnOn(fridgeId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/off/{fridgeId}")
  public ResponseEntity<Void> turnFridgeOff(@PathVariable final Long fridgeId) {
    fridgeActionInitiatorFacade.turnOff(fridgeId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/temperature")
  public ResponseEntity<Void> changeFridgeTemperature(@RequestBody @Valid final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    fridgeActionInitiatorFacade.setTemperature(changeTemperatureRequestDto);

    return ResponseEntity.accepted().build();
  }

}
