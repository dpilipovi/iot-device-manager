package hr.dpilipovic.messageprocessor.listener.impl;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import hr.dpilipovic.common.rabbit.configuration.RabbitQueueConfiguration;
import hr.dpilipovic.messageprocessor.listener.AirConditionerActionListener;
import hr.dpilipovic.messageprocessor.service.AirConditionerActionResolverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultAirConditionerActionListener implements AirConditionerActionListener {

  private final AirConditionerActionResolverService airConditionerActionResolverService;
  private final RabbitQueueConfiguration rabbitQueueConfiguration;

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.airConditionerPowerOnQueue().getName()}")
  public void processPowerOnMessage(final Long airConditionerId) {
    log.info("Received power on message for AIR_CONDITIONER({})", airConditionerId);
    airConditionerActionResolverService.powerAirConditionerOn(airConditionerId);
  }

  @Override
  @RabbitListener(queues =  "#{rabbitQueueConfiguration.airConditionerPowerOffQueue().getName()}")
  public void processPowerOffMessage(final Long airConditionerId) {
    log.info("Received power off message for AIR_CONDITIONER({})", airConditionerId);
    airConditionerActionResolverService.powerAirConditionerOff(airConditionerId);
  }

  @Override
  @RabbitListener(queues =  "#{rabbitQueueConfiguration.airConditionerChangeTemperatureQueue().getName()}")
  public void processChangeTemperatureMessage(final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    log.info("Received change temperature message for AIR_CONDITIONER({})", changeTemperatureRequestDto.getId());
    airConditionerActionResolverService.changeAirConditionerTemperature(changeTemperatureRequestDto);
  }

}
