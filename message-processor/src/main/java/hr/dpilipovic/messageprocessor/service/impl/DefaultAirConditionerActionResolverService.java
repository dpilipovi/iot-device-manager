package hr.dpilipovic.messageprocessor.service.impl;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import hr.dpilipovic.common.service.AirConditionerService;
import hr.dpilipovic.messageprocessor.configuration.properties.DeviceMockProperties;
import hr.dpilipovic.messageprocessor.exception.ExpectationFailedException;
import hr.dpilipovic.messageprocessor.service.AirConditionerActionResolverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class DefaultAirConditionerActionResolverService implements AirConditionerActionResolverService {

  private final AirConditionerService airConditionerService;
  private final RestTemplate restTemplate;
  private final String url;

  @Autowired
  public DefaultAirConditionerActionResolverService(final AirConditionerService airConditionerService, final RestTemplate restTemplate,
      final DeviceMockProperties deviceMockProperties) {
    this.airConditionerService = airConditionerService;
    this.restTemplate = restTemplate;
    this.url = deviceMockProperties.getUrl() + "air-conditioner/%s";
  }

  @Override
  public void powerAirConditionerOn(final Long airConditionerId) {
    final var airConditioner = airConditionerService.fetchAirConditionerById(airConditionerId);
    airConditioner.turnOn();

    final var response = sendRequestToDevice(airConditionerId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Power on action for AIR_CONDITIONER({}) not successful", airConditionerId);
      return;
    }

    airConditionerService.saveAirConditioner(airConditioner);
    log.info("Power on action for AIR_CONDITIONER({}) successful", airConditionerId);
  }

  @Override
  public void powerAirConditionerOff(final Long airConditionerId) {
    final var airConditioner = airConditionerService.fetchAirConditionerById(airConditionerId);
    airConditioner.turnOff();

    final var response = sendRequestToDevice(airConditionerId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Power off action for AIR_CONDITIONER({}) not successful", airConditionerId);
      return;
    }

    airConditionerService.saveAirConditioner(airConditioner);
    log.info("Power off action for AIR_CONDITIONER({}) successful", airConditionerId);
  }

  @Override
  public void changeAirConditionerTemperature(final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    final var airConditioner = airConditionerService.fetchAirConditionerById(changeTemperatureRequestDto.getId());
    airConditioner.setTemperature(changeTemperatureRequestDto.getTemperature());

    final var response = sendRequestToDevice(changeTemperatureRequestDto.getId());

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Change temperature action for AIR_CONDITIONER({}) not successful", changeTemperatureRequestDto.getId());
      return;
    }

    airConditionerService.saveAirConditioner(airConditioner);
    log.info("Change temperature action for AIR_CONDITIONER({}) successful", changeTemperatureRequestDto.getId());
  }

  private ResponseEntity<String> sendRequestToDevice(final Long airConditionerId) {
    try {
      return restTemplate.getForEntity(String.format(url, airConditionerId), String.class);
    } catch (final RestClientException restClientException) {
      log.error("Error while trying to reach {}", String.format(url, airConditionerId));
    }

    throw new ExpectationFailedException("Unexpected error");
  }

}
