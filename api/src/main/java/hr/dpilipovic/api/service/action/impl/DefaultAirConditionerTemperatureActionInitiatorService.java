package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.api.service.action.AirConditionerTemperatureActionInitiatorService;
import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.AirConditionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAirConditionerTemperatureActionInitiatorService implements AirConditionerTemperatureActionInitiatorService {

  private static final String NOT_FOUND_MESSAGE = "Air conditioner with ID %s not found";
  private final AirConditionerService airConditionerService;
  private final RabbitQueueProperties rabbitQueueProperties;
  private final RabbitTemplate rabbitTemplate;

  @Override
  public void setTemperature(final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    checkIfExists(changeTemperatureRequestDto.getId());

    rabbitTemplate.convertAndSend(rabbitQueueProperties.airConditionerChangeTemperatureQueue(), changeTemperatureRequestDto);
  }

  private void checkIfExists(final Long airConditionerId) {
    if (!airConditionerService.airConditionerExistsById(airConditionerId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, airConditionerId));
    }
  }

}
