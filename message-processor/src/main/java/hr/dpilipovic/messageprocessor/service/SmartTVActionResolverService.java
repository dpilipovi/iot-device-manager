package hr.dpilipovic.messageprocessor.service;

import hr.dpilipovic.common.dto.RecordingRequestDto;

public interface SmartTVActionResolverService {

  void powerSmartTVOn(Long smartTVId);

  void powerSmartTVOff(Long smartTVId);

  void startSmartTVRecording(RecordingRequestDto recordingRequestDto);

  void pauseSmartTVRecording(Long smartTVId);

  void resumeSmartTVRecording(Long smartTVId);

  void stopSmartTVRecording(Long smartTVId);

}
