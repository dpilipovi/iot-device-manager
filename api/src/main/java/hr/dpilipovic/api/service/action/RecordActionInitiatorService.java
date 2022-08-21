package hr.dpilipovic.api.service.action;

import hr.dpilipovic.common.dto.RecordingRequestDto;

public interface RecordActionInitiatorService {

  void startRecording(RecordingRequestDto recordingRequestDto);

  void pauseRecording(Long deviceId);

  void resumeRecording(Long deviceId);

  void stopRecording(Long deviceId);

}
