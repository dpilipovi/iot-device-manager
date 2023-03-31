package hr.dpilipovic.api.controller.action;

import hr.dpilipovic.api.facade.SmartTVActionInitiatorFacade;
import hr.dpilipovic.common.dto.RecordingRequestDto;
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
@RequestMapping("/api/device/smart-tv/action")
public class SmartTVActionController {

  private final SmartTVActionInitiatorFacade smartTVActionInitiatorFacade;

  @PostMapping("/on/{smartTVId}")
  public ResponseEntity<Void> turnAirConditionerOn(@PathVariable final Long smartTVId) {
    smartTVActionInitiatorFacade.turnOn(smartTVId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/off/{smartTVId}")
  public ResponseEntity<Void> turnAirConditionerOff(@PathVariable final Long smartTVId) {
    smartTVActionInitiatorFacade.turnOff(smartTVId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/recording/start")
  public ResponseEntity<Void> startSmartTVRecording(@RequestBody @Valid final RecordingRequestDto recordingRequestDto) {
    smartTVActionInitiatorFacade.startRecording(recordingRequestDto);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/recording/pause/{smartTVId}")
  public ResponseEntity<Void> pauseSmartTVRecording(@PathVariable final Long smartTVId) {
    smartTVActionInitiatorFacade.pauseRecording(smartTVId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/recording/resume/{smartTVId}")
  public ResponseEntity<Void> resumeSmartTVRecording(@PathVariable final Long smartTVId) {
    smartTVActionInitiatorFacade.resumeRecording(smartTVId);

    return ResponseEntity.accepted().build();
  }

  @PostMapping("/recording/stop/{smartTVId}")
  public ResponseEntity<Void> stopSmartTVRecording(@PathVariable final Long smartTVId) {
    smartTVActionInitiatorFacade.stopRecording(smartTVId);

    return ResponseEntity.accepted().build();
  }

}
