package hr.dpilipovic.messageprocessor.service.impl;

import hr.dpilipovic.common.service.VacuumCleanerService;
import hr.dpilipovic.messageprocessor.configuration.properties.DeviceMockProperties;
import hr.dpilipovic.messageprocessor.exception.ExpectationFailedException;
import hr.dpilipovic.messageprocessor.service.VacuumCleanerActionResolverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultVacuumCleanerActionResolverService implements VacuumCleanerActionResolverService {

  private final VacuumCleanerService vacuumCleanerService;
  private final RestTemplate restTemplate;
  private final String url;

  @Autowired
  public DefaultVacuumCleanerActionResolverService(final VacuumCleanerService vacuumCleanerService, final RestTemplate restTemplate,
      final DeviceMockProperties deviceMockProperties) {
    this.vacuumCleanerService = vacuumCleanerService;
    this.restTemplate = restTemplate;
    this.url = deviceMockProperties.getUrl() + "vacuum-cleaner/%s";
  }

  @Override
  public void powerVacuumCleanerOn(final Long vacuumCleanerId) {
    final var vacuumCleaner = vacuumCleanerService.fetchVacuumCleanerById(vacuumCleanerId);
    vacuumCleaner.turnOn();

    final var response = sendRequestToDevice(vacuumCleanerId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Power on action for VACUUM_CLEANER({}) not successful", vacuumCleanerId);
      return;
    }

    vacuumCleanerService.saveVacuumCleaner(vacuumCleaner);
    log.info("Power on action for VACUUM_CLEANER({}) successful", vacuumCleanerId);
  }

  @Override
  public void powerVacuumCleanerOff(final Long vacuumCleanerId) {
    final var vacuumCleaner = vacuumCleanerService.fetchVacuumCleanerById(vacuumCleanerId);
    vacuumCleaner.turnOff();

    final var response = sendRequestToDevice(vacuumCleanerId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Power off action for VACUUM_CLEANER({}) not successful", vacuumCleanerId);
      return;
    }

    vacuumCleanerService.saveVacuumCleaner(vacuumCleaner);
    log.info("Power off action for VACUUM_CLEANER({}) successful", vacuumCleanerId);
  }

  private ResponseEntity<String> sendRequestToDevice(final Long vacuumCleanerId) {
    try {
      return restTemplate.getForEntity(String.format(url, vacuumCleanerId), String.class);
    } catch (final RestClientException restClientException) {
      log.error("Error while trying to reach {}", String.format(url, vacuumCleanerId));
    }

    throw new ExpectationFailedException("Unexpected error");
  }

}
