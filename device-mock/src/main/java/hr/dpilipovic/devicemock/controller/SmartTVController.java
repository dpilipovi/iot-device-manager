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
@RequestMapping("/mock/device/smart-tv")
public class SmartTVController {

  @GetMapping("/{smartTVId}")
  public ResponseEntity<Void> receiveActionCommand(@PathVariable final Long smartTVId) {
    log.info("SMART_TV({}) received action command", smartTVId);

    return ResponseEntity.accepted().build();
  }

}