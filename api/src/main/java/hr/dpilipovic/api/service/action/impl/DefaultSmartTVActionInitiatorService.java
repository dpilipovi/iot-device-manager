package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.api.service.action.SmartTVActionInitiatorService;
import hr.dpilipovic.common.dto.RecordingRequestDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.SmartTVService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultSmartTVActionInitiatorService implements SmartTVActionInitiatorService {

  private final SmartTVService smartTVService;
  private final RabbitQueueProperties rabbitQueueProperties;
  private final RabbitTemplate rabbitTemplate;
  private static final String NOT_FOUND_MESSAGE = "Smart TV with ID %s not found";

  @Override
  public void turnOn(final Long smartTVId) {
    checkIfExists(smartTVId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.smartTVPowerOnQueue(), smartTVId);
  }

  @Override
  public void turnOff(final Long smartTVId) {
    checkIfExists(smartTVId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.smartTVPowerOffQueue(), smartTVId);
  }

  @Override
  public void startRecording(final RecordingRequestDto recordingRequestDto) {
    checkIfExists(recordingRequestDto.getId());

    rabbitTemplate.convertAndSend(rabbitQueueProperties.smartTVStartRecordingQueue(), recordingRequestDto);
  }

  @Override
  public void pauseRecording(final Long smartTVId) {
    checkIfExists(smartTVId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.smartTVPauseRecordingQueue(), smartTVId);
  }

  @Override
  public void resumeRecording(final Long smartTVId) {
    checkIfExists(smartTVId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.smartTVResumeRecordingQueue(), smartTVId);
  }

  @Override
  public void stopRecording(final Long smartTVId) {
    checkIfExists(smartTVId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.smartTVStopRecordingQueue(), smartTVId);
  }

  private void checkIfExists(final Long smartTVId) {
    if (!smartTVService.smartTVExistsById(smartTVId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, smartTVId));
    }
  }

}
