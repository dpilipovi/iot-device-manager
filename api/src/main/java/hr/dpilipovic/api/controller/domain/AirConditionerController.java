package hr.dpilipovic.api.controller.domain;

import hr.dpilipovic.common.dto.AirConditionerDto;
import hr.dpilipovic.common.service.AirConditionerService;
import hr.dpilipovic.common.util.OnCreate;
import hr.dpilipovic.common.util.OnUpdate;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/air-conditioner")
public class AirConditionerController {

  private final AirConditionerService airConditionerService;

  @GetMapping("/{airConditionerId}")
  public ResponseEntity<AirConditionerDto> getAirConditionerById(@PathVariable final Long airConditionerId) {
    return new ResponseEntity<>(airConditionerService.getAirConditionerById(airConditionerId), HttpStatus.OK);
  }

  @PatchMapping
  @Validated(OnUpdate.class)
  public ResponseEntity<AirConditionerDto> updateAirConditioner(@RequestBody @Valid final AirConditionerDto airConditionerDto) {
    return new ResponseEntity<>(airConditionerService.updateAirConditioner(airConditionerDto), HttpStatus.OK);
  }

  @PostMapping
  @Validated(OnCreate.class)
  public ResponseEntity<AirConditionerDto> createAirConditioner(@RequestBody @Valid final AirConditionerDto airConditionerDto) {
    return new ResponseEntity<>(airConditionerService.createAirConditioner(airConditionerDto), HttpStatus.CREATED);
  }

  @DeleteMapping("/{airConditionerId}")
  public ResponseEntity<Void> deleteAirConditionerById(@PathVariable final Long airConditionerId) {
    airConditionerService.deleteAirConditionerById(airConditionerId);
    
    return ResponseEntity.noContent().build();
  }

}
