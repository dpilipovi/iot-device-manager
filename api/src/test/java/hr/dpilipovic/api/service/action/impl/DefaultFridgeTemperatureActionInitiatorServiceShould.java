package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
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
class DefaultFridgeTemperatureActionInitiatorServiceShould {

  private DefaultFridgeTemperatureActionInitiatorService defaultFridgeTemperatureActionInitiatorService;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private RabbitQueueProperties rabbitQueueProperties;

  @Mock
  private FridgeService fridgeService;

  private static final String NOT_FOUND_MESSAGE = "Fridge with ID %s not found";

  @BeforeEach
  public void setup() {
    this.defaultFridgeTemperatureActionInitiatorService = new DefaultFridgeTemperatureActionInitiatorService(fridgeService, rabbitTemplate, rabbitQueueProperties);
  }

  @Test
  void setTemperature() {
    final var changeTemperatureRequestDto = ChangeTemperatureRequestDto.builder().id(1L).temperature(22).build();
    final var fridgeChangeTemperatureQueue = "fridge_change_temperature";

    when(fridgeService.fridgeExistsById(changeTemperatureRequestDto.getId())).thenReturn(true);
    when(rabbitQueueProperties.fridgeChangeTemperatureQueue()).thenReturn(fridgeChangeTemperatureQueue);

    defaultFridgeTemperatureActionInitiatorService.setTemperature(changeTemperatureRequestDto);

    verify(rabbitTemplate).convertAndSend(fridgeChangeTemperatureQueue, changeTemperatureRequestDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToChangeTemperatureForFridgeThatDoesNotExist() {
    final var changeTemperatureRequestDto = ChangeTemperatureRequestDto.builder().id(1L).temperature(22).build();

    assertThatThrownBy(() -> defaultFridgeTemperatureActionInitiatorService.setTemperature(changeTemperatureRequestDto))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, changeTemperatureRequestDto.getId()));
  }

}
