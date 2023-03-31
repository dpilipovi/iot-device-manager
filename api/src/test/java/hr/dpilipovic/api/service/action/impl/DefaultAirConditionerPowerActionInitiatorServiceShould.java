package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.AirConditionerService;
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
class DefaultAirConditionerPowerActionInitiatorServiceShould {

  private DefaultAirConditionerPowerActionInitiatorService defaultAirConditionerPowerActionInitiatorService;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private RabbitQueueProperties rabbitQueueProperties;

  @Mock
  private AirConditionerService airConditionerService;

  private static final String NOT_FOUND_MESSAGE = "Air conditioner with ID %s not found";

  @BeforeEach
  public void setup() {
    this.defaultAirConditionerPowerActionInitiatorService = new DefaultAirConditionerPowerActionInitiatorService(airConditionerService, rabbitQueueProperties, rabbitTemplate);
  }

  @Test
  void turnOn() {
    final var airConditionerId = 1L;
    final var airConditionerPowerOnQueue = "air_conditioner_power_on";

    when(airConditionerService.airConditionerExistsById(airConditionerId)).thenReturn(true);
    when(rabbitQueueProperties.airConditionerPowerOnQueue()).thenReturn(airConditionerPowerOnQueue);

    defaultAirConditionerPowerActionInitiatorService.turnOn(airConditionerId);

    verify(rabbitTemplate).convertAndSend(airConditionerPowerOnQueue, airConditionerId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOnAirConditionerThatDoesNotExist() {
    final var airConditionerId = 1L;

    assertThatThrownBy(() -> defaultAirConditionerPowerActionInitiatorService.turnOn(airConditionerId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, airConditionerId));
  }

  @Test
  void turnOff() {
    final var airConditionerId = 1L;
    final var airConditionerPowerOffQueue = "air_conditioner_power_off";

    when(airConditionerService.airConditionerExistsById(airConditionerId)).thenReturn(true);
    when(rabbitQueueProperties.airConditionerPowerOffQueue()).thenReturn(airConditionerPowerOffQueue);

    defaultAirConditionerPowerActionInitiatorService.turnOff(airConditionerId);

    verify(rabbitTemplate).convertAndSend(airConditionerPowerOffQueue, airConditionerId);
  }

}
