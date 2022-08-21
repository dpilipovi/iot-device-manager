package hr.dpilipovic.messageprocessor.service.impl;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;
import hr.dpilipovic.common.model.Fridge;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.service.FridgeService;
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
class DefaultFridgeActionResolverServiceShould {

  private DefaultFridgeActionResolverService defaultFridgeActionResolverService;

  @Mock
  private FridgeService fridgeService;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private DeviceMockProperties deviceMockProperties;

  @BeforeEach
  void setUp() {
    this.defaultFridgeActionResolverService = new DefaultFridgeActionResolverService(fridgeService, restTemplate, deviceMockProperties);
  }

  @Test
  void powerFridgeOn() {
    final var fridge = Fridge.builder().id(1L).deviceStatus(DeviceStatus.OFF).build();
    final var updatedFridge = Fridge.builder().id(1L).deviceStatus(DeviceStatus.ON).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(fridgeService.fetchFridgeById(1L)).thenReturn(fridge);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(fridgeService.saveFridge(updatedFridge)).thenReturn(updatedFridge);

    defaultFridgeActionResolverService.powerFridgeOn(fridge.getId());

    verify(fridgeService).saveFridge(updatedFridge);
  }

  @Test
  void powerFridgeOff() {
    final var fridge = Fridge.builder().id(1L).deviceStatus(DeviceStatus.ON).build();
    final var updatedFridge = Fridge.builder().id(1L).deviceStatus(DeviceStatus.OFF).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(fridgeService.fetchFridgeById(1L)).thenReturn(fridge);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(fridgeService.saveFridge(updatedFridge)).thenReturn(updatedFridge);

    defaultFridgeActionResolverService.powerFridgeOff(fridge.getId());

    verify(fridgeService).saveFridge(updatedFridge);
  }

  @Test
  void changeFridgeTemperature() {
    final var changeTemperatureRequestDto = new ChangeTemperatureRequestDto(1L, 5);
    final var fridge = Fridge.builder().id(1L).temperature(6).build();
    final var updatedFridge = Fridge.builder().id(1L).temperature(5).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(fridgeService.fetchFridgeById(1L)).thenReturn(fridge);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(fridgeService.saveFridge(updatedFridge)).thenReturn(updatedFridge);

    defaultFridgeActionResolverService.changeFridgeTemperature(changeTemperatureRequestDto);

    verify(fridgeService).saveFridge(updatedFridge);
  }

}