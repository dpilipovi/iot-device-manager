package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.common.dto.RecordingRequestDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.SmartTVService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSmartTVRecordActionInitiatorServiceShould {

  private DefaultSmartTVRecordActionInitiatorService defaultSmartTVRecordActionInitiatorService;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private RabbitQueueProperties rabbitQueueProperties;

  @Mock
  private SmartTVService smartTVService;

  private static final String NOT_FOUND_MESSAGE = "Smart TV with ID %s not found";

  @BeforeEach
  public void setup() {
    this.defaultSmartTVRecordActionInitiatorService = new DefaultSmartTVRecordActionInitiatorService(smartTVService, rabbitTemplate, rabbitQueueProperties);
  }

  @Test
  void startRecording() {
    final var recordingRequestDto = RecordingRequestDto.builder().id(1L).channel(1).build();
    final var queue = "smart_tv_start_recording";

    when(smartTVService.smartTVExistsById(recordingRequestDto.getId())).thenReturn(true);
    when(rabbitQueueProperties.smartTVStartRecordingQueue()).thenReturn(queue);

    defaultSmartTVRecordActionInitiatorService.startRecording(recordingRequestDto);

    verify(rabbitTemplate).convertAndSend(queue, recordingRequestDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingStartRecording_whenSmartTVThatDoesNotExist() {
    final var recordingRequestDto = RecordingRequestDto.builder().id(1L).channel(1).build();

    assertThatThrownBy(() -> defaultSmartTVRecordActionInitiatorService.startRecording(recordingRequestDto))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, recordingRequestDto.getId()));
  }

  @Test
  void pauseRecording() {
    final var smartTVId = 1L;
    final var queue = "smart_tv_pause_recording";

    when(smartTVService.smartTVExistsById(smartTVId)).thenReturn(true);
    when(rabbitQueueProperties.smartTVPauseRecordingQueue()).thenReturn(queue);

    defaultSmartTVRecordActionInitiatorService.pauseRecording(smartTVId);

    verify(rabbitTemplate).convertAndSend(queue, smartTVId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToPauseRecording_whenSmartTVThatDoesNotExist() {
    final var smartTVId = 1L;

    assertThatThrownBy(() -> defaultSmartTVRecordActionInitiatorService.pauseRecording(smartTVId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, smartTVId));
  }

  @Test
  void resumeRecording() {
    final var smartTVId = 1L;
    final var queue = "smart_tv_resume_recording";

    when(smartTVService.smartTVExistsById(smartTVId)).thenReturn(true);
    when(rabbitQueueProperties.smartTVResumeRecordingQueue()).thenReturn(queue);

    defaultSmartTVRecordActionInitiatorService.resumeRecording(smartTVId);

    verify(rabbitTemplate).convertAndSend(queue, smartTVId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingResumeRecording_whenSmartTVThatDoesNotExist() {
    final var smartTVId = 1L;

    assertThatThrownBy(() -> defaultSmartTVRecordActionInitiatorService.resumeRecording(smartTVId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, smartTVId));
  }

  @Test
  void stopRecording() {
    final var smartTVId = 1L;
    final var queue = "smart_tv_stop_recording";

    when(smartTVService.smartTVExistsById(smartTVId)).thenReturn(true);
    when(rabbitQueueProperties.smartTVStopRecordingQueue()).thenReturn(queue);

    defaultSmartTVRecordActionInitiatorService.stopRecording(smartTVId);

    verify(rabbitTemplate).convertAndSend(queue, smartTVId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingStopRecording_whenSmartTVThatDoesNotExist() {
    final var smartTVId = 1L;

    assertThatThrownBy(() -> defaultSmartTVRecordActionInitiatorService.stopRecording(smartTVId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, smartTVId));
  }

}
