package hr.dpilipovic.messageprocessor.listener.impl;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueConfiguration;
import hr.dpilipovic.messageprocessor.listener.FridgeActionListener;
import hr.dpilipovic.messageprocessor.service.FridgeActionResolverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultFridgeActionListener implements FridgeActionListener {

  private final FridgeActionResolverService fridgeActionResolverService;
  private final RabbitQueueConfiguration rabbitQueueConfiguration;

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.fridgePowerOnQueue().getName()}")
  public void processPowerOnMessage(final Long fridgeId) {
    log.info("Received power on message for FRIDGE({})", fridgeId);
    fridgeActionResolverService.powerFridgeOn(fridgeId);
  }

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.fridgePowerOffQueue().getName()}")
  public void processPowerOffMessage(final Long fridgeId) {
    log.info("Received power off message for FRIDGE({})", fridgeId);
    fridgeActionResolverService.powerFridgeOff(fridgeId);
  }

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.fridgeChangeTemperatureQueue().getName()}")
  public void processChangeTemperatureMessage(final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    log.info("Received change temperature message for FRIDGE({})", changeTemperatureRequestDto.getId());
    fridgeActionResolverService.changeFridgeTemperature(changeTemperatureRequestDto);
  }

}
