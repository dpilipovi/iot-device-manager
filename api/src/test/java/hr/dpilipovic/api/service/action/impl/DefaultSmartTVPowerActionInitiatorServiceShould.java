package hr.dpilipovic.api.service.action.impl;

import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueProperties;
import hr.dpilipovic.common.service.SmartTVService;
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
class DefaultSmartTVPowerActionInitiatorServiceShould {

  private DefaultSmartTVPowerActionInitiatorService defaultSmartTVPowerActionInitiatorService;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private RabbitQueueProperties rabbitQueueProperties;

  @Mock
  private SmartTVService smartTVService;

  private static final String NOT_FOUND_MESSAGE = "Smart TV with ID %s not found";

  @BeforeEach
  public void setup() {
    this.defaultSmartTVPowerActionInitiatorService = new DefaultSmartTVPowerActionInitiatorService(smartTVService, rabbitTemplate, rabbitQueueProperties);
  }

  @Test
  void turnOn() {
    final var smartTVId = 1L;
    final var queue = "smart_tv_power_on";

    when(smartTVService.smartTVExistsById(smartTVId)).thenReturn(true);
    when(rabbitQueueProperties.smartTVPowerOnQueue()).thenReturn(queue);

    defaultSmartTVPowerActionInitiatorService.turnOn(smartTVId);

    verify(rabbitTemplate).convertAndSend(queue, smartTVId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOnSmartTVThatDoesNotExist() {
    final var smartTVId = 1L;

    assertThatThrownBy(() -> defaultSmartTVPowerActionInitiatorService.turnOn(smartTVId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, smartTVId));
  }

  @Test
  void turnOff() {
    final var smartTVId = 1L;
    final var queue = "smart_tv_power_off";

    when(smartTVService.smartTVExistsById(smartTVId)).thenReturn(true);
    when(rabbitQueueProperties.smartTVPowerOffQueue()).thenReturn(queue);

    defaultSmartTVPowerActionInitiatorService.turnOff(smartTVId);

    verify(rabbitTemplate).convertAndSend(queue, smartTVId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToTurnOffSmartTVThatDoesNotExist() {
    final var smartTVId = 1L;

    assertThatThrownBy(() -> defaultSmartTVPowerActionInitiatorService.turnOff(smartTVId))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(String.format(NOT_FOUND_MESSAGE, smartTVId));
  }

}
