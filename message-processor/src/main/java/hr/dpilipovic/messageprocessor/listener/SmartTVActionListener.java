package hr.dpilipovic.messageprocessor.listener;

import hr.dpilipovic.common.dto.RecordingRequestDto;

public interface SmartTVActionListener {

  void processPowerOnMessage(Long smartTVId);

  void processPowerOffMessage(Long smartTVId);

  void processStartRecordingMessage(RecordingRequestDto recordingRequestDto);

  void processPauseRecordingMessage(Long smartTVId);

  void processResumeRecordingMessage(Long smartTVId);

  void processStopRecordingMessage(Long smartTVId);

}
