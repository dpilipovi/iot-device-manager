package hr.dpilipovic.messageprocessor.service.impl;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import hr.dpilipovic.common.model.AirConditioner;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.service.AirConditionerService;
import hr.dpilipovic.messageprocessor.configuration.properties.DeviceMockProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultAirConditionerActionResolverServiceShould {

  private DefaultAirConditionerActionResolverService defaultAirConditionerActionResolverService;

  @Mock
  private AirConditionerService airConditionerService;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private DeviceMockProperties deviceMockProperties;

  @BeforeEach
  void setUp() {
    this.defaultAirConditionerActionResolverService = new DefaultAirConditionerActionResolverService(airConditionerService, restTemplate, deviceMockProperties);
  }

  @Test
  void powerAirConditionerOn() {
    final var airConditioner = AirConditioner.builder().id(1L).deviceStatus(DeviceStatus.OFF).build();
    final var updatedAirConditioner = AirConditioner.builder().id(1L).deviceStatus(DeviceStatus.ON).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(airConditionerService.fetchAirConditionerById(1L)).thenReturn(airConditioner);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(airConditionerService.saveAirConditioner(updatedAirConditioner)).thenReturn(updatedAirConditioner);

    defaultAirConditionerActionResolverService.powerAirConditionerOn(airConditioner.getId());

    verify(airConditionerService).saveAirConditioner(updatedAirConditioner);
  }

  @Test
  void powerAirConditionerOff() {
    final var airConditioner = AirConditioner.builder().id(1L).deviceStatus(DeviceStatus.ON).build();
    final var updatedAirConditioner = AirConditioner.builder().id(1L).deviceStatus(DeviceStatus.OFF).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(airConditionerService.fetchAirConditionerById(1L)).thenReturn(airConditioner);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(airConditionerService.saveAirConditioner(updatedAirConditioner)).thenReturn(updatedAirConditioner);

    defaultAirConditionerActionResolverService.powerAirConditionerOff(airConditioner.getId());

    verify(airConditionerService).saveAirConditioner(updatedAirConditioner);
  }

  @Test
  void changeAirConditionerTemperature() {
    final var changeTemperatureRequestDto = new ChangeTemperatureRequestDto(1L, 22);
    final var airConditioner = AirConditioner.builder().id(1L).temperature(23).build();
    final var updatedAirConditioner = AirConditioner.builder().id(1L).temperature(22).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(airConditionerService.fetchAirConditionerById(1L)).thenReturn(airConditioner);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(airConditionerService.saveAirConditioner(updatedAirConditioner)).thenReturn(updatedAirConditioner);

    defaultAirConditionerActionResolverService.changeAirConditionerTemperature(changeTemperatureRequestDto);

    verify(airConditionerService).saveAirConditioner(updatedAirConditioner);
  }

}