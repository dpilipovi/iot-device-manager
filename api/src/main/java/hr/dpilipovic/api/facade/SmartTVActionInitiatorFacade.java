package hr.dpilipovic.api.facade;

import hr.dpilipovic.api.service.action.SmartTVPowerActionInitiatorService;
import hr.dpilipovic.api.service.action.SmartTVRecordActionInitiatorService;
import hr.dpilipovic.common.dto.RecordingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmartTVActionInitiatorFacade {

  private final SmartTVPowerActionInitiatorService smartTVPowerActionInitiatorService;
  private final SmartTVRecordActionInitiatorService smartTVRecordActionInitiatorService;

  public void turnOn(final Long smartTvId) {
    smartTVPowerActionInitiatorService.turnOn(smartTvId);
  }

  public void turnOff(final Long smartTvId) {
    smartTVPowerActionInitiatorService.turnOff(smartTvId);
  }

  public void startRecording(final RecordingRequestDto recordingRequestDto) {
    smartTVRecordActionInitiatorService.startRecording(recordingRequestDto);
  }

  public void pauseRecording(final Long smartTVId) {
    smartTVRecordActionInitiatorService.pauseRecording(smartTVId);
  }

  public void resumeRecording(final Long smartTVId) {
    smartTVRecordActionInitiatorService.resumeRecording(smartTVId);
  }

  public void stopRecording(final Long smartTVId) {
    smartTVRecordActionInitiatorService.stopRecording(smartTVId);
  }

}
