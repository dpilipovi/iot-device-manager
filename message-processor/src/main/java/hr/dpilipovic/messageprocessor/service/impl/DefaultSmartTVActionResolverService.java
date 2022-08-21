package hr.dpilipovic.messageprocessor.service.impl;

import hr.dpilipovic.common.dto.RecordingRequestDto;
import hr.dpilipovic.common.service.SmartTVService;
import hr.dpilipovic.messageprocessor.configuration.properties.DeviceMockProperties;
import hr.dpilipovic.messageprocessor.exception.ExpectationFailedException;
import hr.dpilipovic.messageprocessor.service.SmartTVActionResolverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class DefaultSmartTVActionResolverService implements SmartTVActionResolverService {

  private final SmartTVService smartTVService;
  private final RestTemplate restTemplate;
  private final String url;

  @Autowired
  public DefaultSmartTVActionResolverService(final SmartTVService smartTVService, final RestTemplate restTemplate,
      final DeviceMockProperties deviceMockProperties) {
    this.smartTVService = smartTVService;
    this.restTemplate = restTemplate;
    this.url = deviceMockProperties.getUrl() + "smart-tv/%s";
  }

  @Override
  public void powerSmartTVOn(final Long smartTVId) {
    final var smartTV = smartTVService.fetchSmartTVById(smartTVId);
    smartTV.turnOn();

    final var response = sendRequestToDevice(smartTVId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Power on action for SMART_TV({}) not successful", smartTVId);
      return;
    }

    smartTVService.saveSmartTV(smartTV);
    log.info("Power on action for SMART_TV({}) successful", smartTVId);
  }

  @Override
  public void powerSmartTVOff(final Long smartTVId) {
    final var smartTV = smartTVService.fetchSmartTVById(smartTVId);
    smartTV.turnOff();

    final var response = sendRequestToDevice(smartTVId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Power off action for SMART_TV({}) not successful", smartTVId);
      return;
    }

    smartTVService.saveSmartTV(smartTV);
    log.info("Power off action for SMART_TV({}) successful", smartTVId);
  }

  @Override
  public void startSmartTVRecording(final RecordingRequestDto recordingRequestDto) {
    final var smartTV = smartTVService.fetchSmartTVById(recordingRequestDto.getId());
    smartTV.startRecording(recordingRequestDto.getChannel());

    final var response = sendRequestToDevice(recordingRequestDto.getId());

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Start recording action for SMART_TV({}) not successful", recordingRequestDto.getId());
      return;
    }

    smartTVService.saveSmartTV(smartTV);
    log.info("Start recording action for SMART_TV({}) successful", recordingRequestDto.getId());
  }

  @Override
  public void pauseSmartTVRecording(final Long smartTVId) {
    final var smartTV = smartTVService.fetchSmartTVById(smartTVId);
    smartTV.pauseRecording();

    final var response = sendRequestToDevice(smartTVId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Pause recording action for SMART_TV({}) not successful", smartTVId);
      return;
    }

    smartTVService.saveSmartTV(smartTV);
    log.info("Pause recording action for SMART_TV({}) successful", smartTVId);
  }

  @Override
  public void resumeSmartTVRecording(final Long smartTVId) {
    final var smartTV = smartTVService.fetchSmartTVById(smartTVId);
    smartTV.resumeRecording();

    final var response = sendRequestToDevice(smartTVId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Resume recording action for SMART_TV({}) not successful", smartTVId);
      return;
    }

    smartTVService.saveSmartTV(smartTV);
    log.info("Resume recording action for SMART_TV({}) successful", smartTVId);
  }

  @Override
  public void stopSmartTVRecording(final Long smartTVId) {
    final var smartTV = smartTVService.fetchSmartTVById(smartTVId);
    smartTV.stopRecording();

    final var response = sendRequestToDevice(smartTVId);

    if (!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
      log.info("Stop recording action for SMART_TV({}) not successful", smartTVId);
      return;
    }

    smartTVService.saveSmartTV(smartTV);
    log.info("Stop recording action for SMART_TV({}) successful", smartTVId);
  }

  private ResponseEntity<String> sendRequestToDevice(final Long smartTVId) {
    try {
      return restTemplate.getForEntity(String.format(url, smartTVId), String.class);
    } catch (final RestClientException restClientException) {
      log.error("Error while trying to reach {}", String.format(url, smartTVId));
    }

    throw new ExpectationFailedException("Unexpected error");
  }

}
