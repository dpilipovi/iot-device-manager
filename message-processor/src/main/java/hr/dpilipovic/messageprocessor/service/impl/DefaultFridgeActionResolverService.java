package hr.dpilipovic.messageprocessor.service.impl;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import hr.dpilipovic.common.service.FridgeService;
import hr.dpilipovic.messageprocessor.configuration.properties.DeviceMockProperties;
import hr.dpilipovic.messageprocessor.exception.ExpectationFailedException;
import hr.dpilipovic.messageprocessor.service.FridgeActionResolverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class DefaultFridgeActionResolverService implements FridgeActionResolverService {

  private final FridgeService fridgeService;
  private final RestTemplate restTemplate;
  private final String url;

  @Autowired
  public DefaultFridgeActionResolverService(final FridgeService fridgeService, final RestTemplate restTemplate,
      final DeviceMockProperties deviceMockProperties) {
    this.fridgeService = fridgeService;
    this.restTemplate = restTemplate;
    this.url = deviceMockProperties.getUrl() + "fridge/%s";
  }

  @Override
  public void powerFridgeOn(final Long fridgeId) {
    final var fridge = fridgeService.fetchFridgeById(fridgeId);
    fridge.turnOn();

    final var response = sendRequestToDevice(fridgeId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Power on action for FRIDGE({}) not successful", fridgeId);
      return;
    }

    fridgeService.saveFridge(fridge);
    log.info("Power on action for FRIDGE({}) successful", fridgeId);
  }

  @Override
  public void powerFridgeOff(final Long fridgeId) {
    final var fridge = fridgeService.fetchFridgeById(fridgeId);
    fridge.turnOff();

    final var response = sendRequestToDevice(fridgeId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Power off action for FRIDGE({}) not successful", fridgeId);
      return;
    }

    fridgeService.saveFridge(fridge);
    log.info("Power off action for FRIDGE({}) successful", fridgeId);
  }

  @Override
  public void changeFridgeTemperature(final ChangeTemperatureRequestDto changeTemperatureRequestDto) {
    final var fridge = fridgeService.fetchFridgeById(changeTemperatureRequestDto.getId());
    fridge.setTemperature(changeTemperatureRequestDto.getTemperature());

    final var response = sendRequestToDevice(changeTemperatureRequestDto.getId());

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Change temperature action for FRIDGE({}) not successful", changeTemperatureRequestDto.getId());
      return;
    }

    fridgeService.saveFridge(fridge);
    log.info("Change temperature action for FRIDGE({}) successful", changeTemperatureRequestDto.getId());
  }

  private ResponseEntity<String> sendRequestToDevice(final Long fridgeId) {
    try {
      return restTemplate.getForEntity(String.format(url, fridgeId), String.class);
    } catch (final RestClientException restClientException) {
      log.error("Error while trying to reach {}", String.format(url, fridgeId));
    }

    throw new ExpectationFailedException("Unexpected error");
  }

}
