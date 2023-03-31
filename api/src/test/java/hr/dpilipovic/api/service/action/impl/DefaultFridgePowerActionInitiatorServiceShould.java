package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.FridgeService;
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
class DefaultFridgePowerActionInitiatorServiceShould {

  private DefaultFridgePowerActionInitiatorService defaultFridgePowerActionInitiatorService;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private RabbitQueueProperties rabbitQueueProperties;

  @Mock
  private FridgeService fridgeService;

  private static final String NOT_FOUND_MESSAGE = "Fridge with ID %s not found";

  @BeforeEach
  public void setup() {
    this.defaultFridgePowerActionInitiatorService = new DefaultFridgePowerActionInitiatorService(fridgeService, rabbitTemplate, rabbitQueueProperties);
  }

  @Test
  void turnOn() {
    final var fridgeId = 1L;
    final var fridgePowerOnQueue = "fridge_power_on";

    when(fridgeService.fridgeExistsById(fridgeId)).thenReturn(true);
    when(rabbitQueueProperties.fridgePowerOnQueue()).thenReturn(fridgePowerOnQueue);

    defaultFridgePowerActionInitiatorService.turnOn(fridgeId);

    verify(rabbitTemplate).convertAndSend(fridgePowerOnQueue, fridgeId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOnFridgeThatDoesNotExist() {
    final var fridgeId = 1L;

    assertThatThrownBy(() -> defaultFridgePowerActionInitiatorService.turnOn(fridgeId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, fridgeId));
  }

  @Test
  void turnOff() {
    final var fridgeId = 1L;
    final var fridgePowerOffQueue = "fridge_power_off";

    when(fridgeService.fridgeExistsById(fridgeId)).thenReturn(true);
    when(rabbitQueueProperties.fridgePowerOffQueue()).thenReturn(fridgePowerOffQueue);

    defaultFridgePowerActionInitiatorService.turnOff(fridgeId);

    verify(rabbitTemplate).convertAndSend(fridgePowerOffQueue, fridgeId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOffFridgeThatDoesNotExist() {
    final var fridgeId = 1L;

    assertThatThrownBy(() -> defaultFridgePowerActionInitiatorService.turnOff(fridgeId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, fridgeId));
  }

}
