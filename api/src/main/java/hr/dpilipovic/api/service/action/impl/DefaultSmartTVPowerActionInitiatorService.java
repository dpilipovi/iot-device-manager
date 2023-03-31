package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.api.service.action.SmartTVPowerActionInitiatorService;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.SmartTVService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultSmartTVPowerActionInitiatorService implements SmartTVPowerActionInitiatorService {

  private final SmartTVService smartTVService;
  private final RabbitTemplate rabbitTemplate;
  private final RabbitQueueProperties rabbitQueueProperties;
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

  private void checkIfExists(final Long smartTVId) {
    if (!smartTVService.smartTVExistsById(smartTVId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, smartTVId));
    }
  }

}
