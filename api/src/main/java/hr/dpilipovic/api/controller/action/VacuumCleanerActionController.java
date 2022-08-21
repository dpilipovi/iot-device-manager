package hr.dpilipovic.api.controller.action;

import hr.dpilipovic.api.service.action.VacuumCleanerActionInitiatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/vacuum-cleaner/action")
public class VacuumCleanerActionController {

  private final VacuumCleanerActionInitiatorService vacuumCleanerActionInitiatorService;

  @PostMapping("/on/{vacuumCleanerId}")
  public ResponseEntity<Void> turnVacuumCleanerOn(@PathVariable final Long vacuumCleanerId) {
    vacuumCleanerActionInitiatorService.turnOn(vacuumCleanerId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/off/{vacuumCleanerId}")
  public ResponseEntity<Void> turnVacuumCleanerOff(@PathVariable final Long vacuumCleanerId) {
    vacuumCleanerActionInitiatorService.turnOff(vacuumCleanerId);

    return ResponseEntity.accepted().build();
  }

}
