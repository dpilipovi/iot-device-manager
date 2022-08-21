package hr.dpilipovic.messageprocessor.service.impl;

import hr.dpilipovic.common.model.VacuumCleaner;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.service.VacuumCleanerService;
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
class DefaultVacuumCleanerActionResolverServiceShould {

  private DefaultVacuumCleanerActionResolverService defaultVacuumCleanerActionResolverService;

  @Mock
  private VacuumCleanerService vacuumCleanerService;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private DeviceMockProperties deviceMockProperties;

  @BeforeEach
  void setUp() {
    this.defaultVacuumCleanerActionResolverService = new DefaultVacuumCleanerActionResolverService(vacuumCleanerService, restTemplate, deviceMockProperties);
  }

  @Test
  void powerVacuumCleanerOn() {
    final var vacuumCleaner = VacuumCleaner.builder().id(1L).deviceStatus(DeviceStatus.OFF).build();
    final var updatedVacuumCleaner = VacuumCleaner.builder().id(1L).deviceStatus(DeviceStatus.ON).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(vacuumCleanerService.fetchVacuumCleanerById(1L)).thenReturn(vacuumCleaner);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(vacuumCleanerService.saveVacuumCleaner(updatedVacuumCleaner)).thenReturn(updatedVacuumCleaner);

    defaultVacuumCleanerActionResolverService.powerVacuumCleanerOn(vacuumCleaner.getId());

    verify(vacuumCleanerService).saveVacuumCleaner(updatedVacuumCleaner);
  }

  @Test
  void powerVacuumCleanerOff() {
    final var vacuumCleaner = VacuumCleaner.builder().id(1L).deviceStatus(DeviceStatus.ON).build();
    final var updatedVacuumCleaner = VacuumCleaner.builder().id(1L).deviceStatus(DeviceStatus.OFF).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(vacuumCleanerService.fetchVacuumCleanerById(1L)).thenReturn(vacuumCleaner);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(vacuumCleanerService.saveVacuumCleaner(updatedVacuumCleaner)).thenReturn(updatedVacuumCleaner);

    defaultVacuumCleanerActionResolverService.powerVacuumCleanerOff(vacuumCleaner.getId());

    verify(vacuumCleanerService).saveVacuumCleaner(updatedVacuumCleaner);
  }

}