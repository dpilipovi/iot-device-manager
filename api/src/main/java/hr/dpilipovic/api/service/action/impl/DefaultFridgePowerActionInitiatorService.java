package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.api.service.action.FridgePowerActionInitiatorService;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.FridgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFridgePowerActionInitiatorService implements FridgePowerActionInitiatorService {

  private final FridgeService fridgeService;
  private final RabbitTemplate rabbitTemplate;
  private final RabbitQueueProperties rabbitQueueProperties;
  private static final String NOT_FOUND_MESSAGE = "Fridge with ID %s not found";

  @Override
  public void turnOn(final Long fridgeId) {
    checkIfExists(fridgeId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.fridgePowerOnQueue(), fridgeId);
  }

  @Override
  public void turnOff(final Long fridgeId) {
    checkIfExists(fridgeId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.fridgePowerOffQueue(), fridgeId);
  }

  private void checkIfExists(final Long fridgeId) {
    if (!fridgeService.fridgeExistsById(fridgeId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, fridgeId));
    }
  }

}
