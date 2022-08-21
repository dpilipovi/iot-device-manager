package hr.dpilipovic.messageprocessor.listener.impl;

import hr.dpilipovic.common.rabbit.configuration.RabbitQueueConfiguration;
import hr.dpilipovic.messageprocessor.listener.VacuumCleanerActionListener;
import hr.dpilipovic.messageprocessor.service.VacuumCleanerActionResolverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultVacuumCleanerActionListener implements VacuumCleanerActionListener {

  private final VacuumCleanerActionResolverService vacuumCleanerActionResolverService;
  private final RabbitQueueConfiguration rabbitQueueConfiguration;

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.vacuumCleanerPowerOnQueue.getName()}")
  public void processPowerOnMessage(final Long vacuumCleanerId) {
    log.info("Received power on message for VACUUM_CLEANER({})", vacuumCleanerId);
    vacuumCleanerActionResolverService.powerVacuumCleanerOn(vacuumCleanerId);
  }

  @Override
  @RabbitListener(queues = "#{rabbitQueueConfiguration.vacuumCleanerPowerOffQueue.getName()}")
  public void processPowerOffMessage(final Long vacuumCleanerId) {
    log.info("Received power off message for VACUUM_CLEANER({})", vacuumCleanerId);
    vacuumCleanerActionResolverService.powerVacuumCleanerOff(vacuumCleanerId);
  }

}
