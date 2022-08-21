package hr.dpilipovic.messageprocessor.listener.impl;

import hr.dpilipovic.common.dto.RecordingRequestDto;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueConfiguration;
import hr.dpilipovic.messageprocessor.listener.SmartTVActionListener;
import hr.dpilipovic.messageprocessor.service.SmartTVActionResolverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultSmartTVActionListener implements SmartTVActionListener {

  private final SmartTVActionResolverService smartTVActionResolverService;
  private final RabbitQueueConfiguration rabbitQueueConfiguration;

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.smartTVPowerOnQueue.getName()}")
  public void processPowerOnMessage(final Long smartTVId) {
    log.info("Received power on message for SMART_TV({})", smartTVId);

    try {
      smartTVActionResolverService.powerSmartTVOn(smartTVId);
    } catch (final Exception e) {
      log.error("Error while powering on SmartTV recording", e);
    }
  }

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.smartTVPowerOffQueue.getName()}")
  public void processPowerOffMessage(final Long smartTVId) {
    log.info("Received power off message for SMART_TV({})", smartTVId);

    try {
      smartTVActionResolverService.powerSmartTVOff(smartTVId);
    } catch (final Exception e) {
      log.error("Error while powering off SmartTV recording", e);
    }
  }

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.smartTVStartRecordingQueue.getName()}")
  public void processStartRecordingMessage(final RecordingRequestDto recordingRequestDto) {
    log.info("Received start recording message for SMART_TV({})", recordingRequestDto.getId());

    try {
      smartTVActionResolverService.startSmartTVRecording(recordingRequestDto);
    } catch (final Exception e) {
      log.error("Error while starting SmartTV recording", e);
    }
  }

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.smartTVPauseRecordingQueue.getName()}")
  public void processPauseRecordingMessage(final Long smartTVId) {
    log.info("Received pause recording message for SMART_TV({})", smartTVId);

    try {
      smartTVActionResolverService.pauseSmartTVRecording(smartTVId);
    } catch (final Exception e) {
      log.error("Error while pausing SmartTV recording", e);
    }
  }

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.smartTVResumeRecordingQueue.getName()}")
  public void processResumeRecordingMessage(final Long smartTVId) {
    log.info("Received resume recording message for SMART_TV({})", smartTVId);

    try {
      smartTVActionResolverService.resumeSmartTVRecording(smartTVId);
    } catch (final Exception e) {
      log.error("Error while resuming SmartTV recording", e);
    }
  }

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.smartTVStopRecordingQueue.getName()}")
  public void processStopRecordingMessage(final Long smartTVId) {
    log.info("Received stop recording message for SMART_TV({})", smartTVId);

    try {
      smartTVActionResolverService.stopSmartTVRecording(smartTVId);
    } catch (final Exception e) {
      log.error("Error while stopping SmartTV recording", e);
    }
  }

}
