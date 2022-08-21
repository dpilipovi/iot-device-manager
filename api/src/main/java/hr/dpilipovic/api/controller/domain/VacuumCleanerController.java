package hr.dpilipovic.api.controller.domain;

import hr.dpilipovic.common.dto.VacuumCleanerDto;
import hr.dpilipovic.common.service.VacuumCleanerService;
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
@RequestMapping("/api/device/vacuum-cleaner")
public class VacuumCleanerController {

  private final VacuumCleanerService vacuumCleanerService;

  @GetMapping("/{vacuumCleanerId}")
  public ResponseEntity<VacuumCleanerDto> getDeviceById(@PathVariable final Long vacuumCleanerId) {
    return new ResponseEntity<>(vacuumCleanerService.getVacuumCleanerById(vacuumCleanerId), HttpStatus.OK);
  }

  @PatchMapping
  @Validated(OnUpdate.class)
  public ResponseEntity<VacuumCleanerDto> updateDevice(@RequestBody @Valid final VacuumCleanerDto vacuumCleanerDto) {
    return new ResponseEntity<>(vacuumCleanerService.updateVacuumCleaner(vacuumCleanerDto), HttpStatus.OK);
  }

  @PostMapping
  @Validated(OnCreate.class)
  public ResponseEntity<VacuumCleanerDto> createDevice(@RequestBody @Valid final VacuumCleanerDto vacuumCleanerDto) {
    return new ResponseEntity<>(vacuumCleanerService.createVacuumCleaner(vacuumCleanerDto), HttpStatus.CREATED);
  }

  @DeleteMapping("/{vacuumCleanerId}")
  public ResponseEntity<Void> deleteDeviceById(@PathVariable final Long vacuumCleanerId) {
    vacuumCleanerService.deleteVacuumCleanerById(vacuumCleanerId);

    return ResponseEntity.noContent().build();
  }

}
