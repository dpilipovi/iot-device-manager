package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.api.service.action.VacuumCleanerActionInitiatorService;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.VacuumCleanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultVacuumCleanerActionInitiatorService implements VacuumCleanerActionInitiatorService {

  private final VacuumCleanerService vacuumCleanerService;
  private final RabbitQueueProperties rabbitQueueProperties;
  private final RabbitTemplate rabbitTemplate;
  private static final String NOT_FOUND_MESSAGE = "Vacuum cleaner with ID %s not found";

  @Override
  public void turnOn(final Long vacuumCleanerId) {
    checkIfExists(vacuumCleanerId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.vacuumCleanerPowerOnQueue(), vacuumCleanerId);
  }

  @Override
  public void turnOff(final Long vacuumCleanerId) {
    checkIfExists(vacuumCleanerId);

    rabbitTemplate.convertAndSend(rabbitQueueProperties.vacuumCleanerPowerOffQueue(), vacuumCleanerId);
  }

  private void checkIfExists(final Long vacuumCleanerId) {
    if (!vacuumCleanerService.vacuumCleanerExistsById(vacuumCleanerId)){
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, vacuumCleanerId));
    }
  }

}
