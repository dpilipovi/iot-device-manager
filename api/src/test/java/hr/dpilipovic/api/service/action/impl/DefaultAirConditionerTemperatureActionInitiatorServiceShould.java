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
class DefaultAirConditionerTemperatureActionInitiatorServiceShould {

  private DefaultAirConditionerTemperatureActionInitiatorService defaultAirConditionerTemperatureActionInitiatorService;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private RabbitQueueProperties rabbitQueueProperties;

  @Mock
  private AirConditionerService airConditionerService;

  private static final String NOT_FOUND_MESSAGE = "Air conditioner with ID %s not found";

  @BeforeEach
  public void setup() {
    this.defaultAirConditionerTemperatureActionInitiatorService =
        new DefaultAirConditionerTemperatureActionInitiatorService(airConditionerService, rabbitQueueProperties, rabbitTemplate);
  }

  @Test
  void setTemperature() {
    final var changeTemperatureRequestDto = ChangeTemperatureRequestDto.builder().id(1L).temperature(22).build();
    final var airConditionerChangeTemperatureQueue = "air_conditioner_change_temperature";

    when(airConditionerService.airConditionerExistsById(changeTemperatureRequestDto.getId())).thenReturn(true);
    when(rabbitQueueProperties.airConditionerChangeTemperatureQueue()).thenReturn(airConditionerChangeTemperatureQueue);

    defaultAirConditionerTemperatureActionInitiatorService.setTemperature(changeTemperatureRequestDto);

    verify(rabbitTemplate).convertAndSend(airConditionerChangeTemperatureQueue, changeTemperatureRequestDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToChangeTemperatureForAirConditionerThatDoesNotExist() {
    final var changeTemperatureRequestDto = ChangeTemperatureRequestDto.builder().id(1L).temperature(22).build();

    assertThatThrownBy(() -> defaultAirConditionerTemperatureActionInitiatorService.setTemperature(changeTemperatureRequestDto))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, changeTemperatureRequestDto.getId()));
  }

}
