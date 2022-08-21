package hr.dpilipovic.devicemock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mock/device/vacuum-cleaner")
public class VacuumCleanerController {

  @GetMapping("/{vacuumCleanerId}")
  public ResponseEntity<Void> receiveActionCommand(@PathVariable final Long vacuumCleanerId) {
    log.info("VACUUM_CLEANER({}) received action command", vacuumCleanerId);

    return ResponseEntity.accepted().build();
  }

}