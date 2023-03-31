package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.api.service.action.AirConditionerPowerActionInitiatorService;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.AirConditionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAirConditionerPowerActionInitiatorService implements AirConditionerPowerActionInitiatorService {

  private static final String NOT_FOUND_MESSAGE = "Air conditioner with ID %s not found";
  private final AirConditionerService airConditionerService;
  private final RabbitQueueProperties rabbitQueueProperties;
  private final RabbitTemplate rabbitTemplate;

  @Override
  public void turnOn(final Long airConditionerId) {
    checkIfExists(airConditionerId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.airConditionerPowerOnQueue(), airConditionerId);
  }

  @Override
  public void turnOff(final Long airConditionerId) {
    checkIfExists(airConditionerId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.airConditionerPowerOffQueue(), airConditionerId);
  }

  private void checkIfExists(final Long airConditionerId) {
    if (!airConditionerService.airConditionerExistsById(airConditionerId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, airConditionerId));
    }
  }

}
