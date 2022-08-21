package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
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
class DefaultAirConditionerActionInitiatorServiceShould {

  private DefaultAirConditionerActionInitiatorService defaultAirConditionerActionInitiatorService;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private RabbitQueueProperties rabbitQueueProperties;

  @Mock
  private AirConditionerService airConditionerService;

  private static final String NOT_FOUND_MESSAGE = "Air conditioner with ID %s not found";

  @BeforeEach
  public void setup() {
    this.defaultAirConditionerActionInitiatorService = new DefaultAirConditionerActionInitiatorService(airConditionerService, rabbitQueueProperties, rabbitTemplate);
  }

  @Test
  void turnOn() {
    final var airConditionerId = 1L;
    final var airConditionerPowerOnQueue = "air_conditioner_power_on";

    when(airConditionerService.airConditionerExistsById(airConditionerId)).thenReturn(true);
    when(rabbitQueueProperties.airConditionerPowerOnQueue()).thenReturn(airConditionerPowerOnQueue);

    defaultAirConditionerActionInitiatorService.turnOn(airConditionerId);

    verify(rabbitTemplate).convertAndSend(airConditionerPowerOnQueue, airConditionerId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOnAirConditionerThatDoesNotExist() {
    final var airConditionerId = 1L;

    assertThatThrownBy(() -> defaultAirConditionerActionInitiatorService.turnOn(airConditionerId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, airConditionerId));
  }

  @Test
  void turnOff() {
    final var airConditionerId = 1L;
    final var airConditionerPowerOffQueue = "air_conditioner_power_off";

    when(airConditionerService.airConditionerExistsById(airConditionerId)).thenReturn(true);
    when(rabbitQueueProperties.airConditionerPowerOffQueue()).thenReturn(airConditionerPowerOffQueue);

    defaultAirConditionerActionInitiatorService.turnOff(airConditionerId);

    verify(rabbitTemplate).convertAndSend(airConditionerPowerOffQueue, airConditionerId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOffAirConditionerThatDoesNotExist() {
    final var airConditionerId = 1L;
    assertThatThrownBy(() -> defaultAirConditionerActionInitiatorService.turnOff(airConditionerId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, airConditionerId));
  }

  @Test
  void setTemperature() {
    final var changeTemperatureRequestDto = ChangeTemperatureRequestDto.builder().id(1L).temperature(22).build();
    final var airConditionerChangeTemperatureQueue = "air_conditioner_change_temperature";

    when(airConditionerService.airConditionerExistsById(changeTemperatureRequestDto.getId())).thenReturn(true);
    when(rabbitQueueProperties.airConditionerChangeTemperatureQueue()).thenReturn(airConditionerChangeTemperatureQueue);

    defaultAirConditionerActionInitiatorService.setTemperature(changeTemperatureRequestDto);

    verify(rabbitTemplate).convertAndSend(airConditionerChangeTemperatureQueue, changeTemperatureRequestDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToChangeTemperatureForAirConditionerThatDoesNotExist() {
    final var changeTemperatureRequestDto = ChangeTemperatureRequestDto.builder().id(1L).temperature(22).build();

    assertThatThrownBy(() -> defaultAirConditionerActionInitiatorService.setTemperature(changeTemperatureRequestDto))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, changeTemperatureRequestDto.getId()));
  }

}