package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.VacuumCleanerService;
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
class DefaultVacuumCleanerActionInitiatorServiceShould {

  private DefaultVacuumCleanerPowerActionInitiatorService defaultVacuumCleanerActionInitiatorService;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private RabbitQueueProperties rabbitQueueProperties;

  @Mock
  private VacuumCleanerService vacuumCleanerService;

  private static final String NOT_FOUND_MESSAGE = "Vacuum cleaner with ID %s not found";

  @BeforeEach
  public void setup() {
    this.defaultVacuumCleanerActionInitiatorService =
        new DefaultVacuumCleanerPowerActionInitiatorService(rabbitTemplate, vacuumCleanerService, rabbitQueueProperties);
  }

  @Test
  void turnOn() {
    final var vacuumCleanerId = 1L;
    final var vacuumCleanerPowerOnQueue = "vacuum_cleaner_power_on";

    when(vacuumCleanerService.vacuumCleanerExistsById(vacuumCleanerId)).thenReturn(true);
    when(rabbitQueueProperties.vacuumCleanerPowerOnQueue()).thenReturn(vacuumCleanerPowerOnQueue);

    defaultVacuumCleanerActionInitiatorService.turnOn(vacuumCleanerId);

    verify(rabbitTemplate).convertAndSend(vacuumCleanerPowerOnQueue, vacuumCleanerId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOnVacuumCleanerThatDoesNotExist() {
    final var vacuumCleanerId = 1L;

    assertThatThrownBy(() -> defaultVacuumCleanerActionInitiatorService.turnOn(vacuumCleanerId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, vacuumCleanerId));
  }

  @Test
  void turnOff() {
    final var vacuumCleanerId = 1L;
    final var vacuumCleanerPowerOffQueue = "vacuum_cleaner_power_off";

    when(vacuumCleanerService.vacuumCleanerExistsById(vacuumCleanerId)).thenReturn(true);
    when(rabbitQueueProperties.vacuumCleanerPowerOffQueue()).thenReturn(vacuumCleanerPowerOffQueue);

    defaultVacuumCleanerActionInitiatorService.turnOff(vacuumCleanerId);

    verify(rabbitTemplate).convertAndSend(vacuumCleanerPowerOffQueue, vacuumCleanerId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOffVacuumCleanerThatDoesNotExist() {
    final var vacuumCleanerId = 1L;

    assertThatThrownBy(() -> defaultVacuumCleanerActionInitiatorService.turnOff(vacuumCleanerId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, vacuumCleanerId));
  }

}
