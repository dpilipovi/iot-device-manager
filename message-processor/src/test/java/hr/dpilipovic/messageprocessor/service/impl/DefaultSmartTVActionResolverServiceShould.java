package hr.dpilipovic.messageprocessor.service.impl;

import hr.dpilipovic.common.dto.RecordingRequestDto;
import hr.dpilipovic.common.model.SmartTV;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.RecordingStatus;
import hr.dpilipovic.common.service.SmartTVService;
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
class DefaultSmartTVActionResolverServiceShould {

  private DefaultSmartTVActionResolverService defaultSmartTVActionResolverService;

  @Mock
  private SmartTVService smartTVService;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private DeviceMockProperties deviceMockProperties;

  @BeforeEach
  void setUp() {
    this.defaultSmartTVActionResolverService = new DefaultSmartTVActionResolverService(smartTVService, restTemplate, deviceMockProperties);
  }

  @Test
  void powerSmartTVOn() {
    final var smartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.OFF).build();
    final var updatedSmartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(smartTVService.fetchSmartTVById(1L)).thenReturn(smartTV);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(smartTVService.saveSmartTV(updatedSmartTV)).thenReturn(updatedSmartTV);

    defaultSmartTVActionResolverService.powerSmartTVOn(smartTV.getId());

    verify(smartTVService).saveSmartTV(updatedSmartTV);
  }

  @Test
  void powerSmartTVOff() {
    final var smartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).build();
    final var updatedSmartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.OFF).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(smartTVService.fetchSmartTVById(1L)).thenReturn(smartTV);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(smartTVService.saveSmartTV(updatedSmartTV)).thenReturn(updatedSmartTV);

    defaultSmartTVActionResolverService.powerSmartTVOff(smartTV.getId());

    verify(smartTVService).saveSmartTV(updatedSmartTV);
  }

  @Test
  void startSmartTVRecording() {
    final var recordingRequestDto = RecordingRequestDto.builder().id(1L).channel(1).build();
    final var smartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.OFF).recordingStatus(RecordingStatus.NOT_RECORDING).build();
    final var updatedSmartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).recordingStatus(RecordingStatus.RECORDING).channel(1).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(smartTVService.fetchSmartTVById(1L)).thenReturn(smartTV);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(smartTVService.saveSmartTV(updatedSmartTV)).thenReturn(updatedSmartTV);

    defaultSmartTVActionResolverService.startSmartTVRecording(recordingRequestDto);

    verify(smartTVService).saveSmartTV(updatedSmartTV);
  }

  @Test
  void pauseSmartTVRecording() {
    final var smartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).recordingStatus(RecordingStatus.RECORDING).channel(1).build();
    final var updatedSmartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).recordingStatus(RecordingStatus.PAUSED).channel(1).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(smartTVService.fetchSmartTVById(1L)).thenReturn(smartTV);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(smartTVService.saveSmartTV(updatedSmartTV)).thenReturn(updatedSmartTV);

    defaultSmartTVActionResolverService.pauseSmartTVRecording(smartTV.getId());

    verify(smartTVService).saveSmartTV(updatedSmartTV);
  }

  @Test
  void resumeSmartTVRecording() {
    final var smartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).recordingStatus(RecordingStatus.PAUSED).channel(1).build();
    final var updatedSmartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).recordingStatus(RecordingStatus.RECORDING).channel(1).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(smartTVService.fetchSmartTVById(1L)).thenReturn(smartTV);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(smartTVService.saveSmartTV(updatedSmartTV)).thenReturn(updatedSmartTV);

    defaultSmartTVActionResolverService.resumeSmartTVRecording(smartTV.getId());

    verify(smartTVService).saveSmartTV(updatedSmartTV);
  }

  @Test
  void stopSmartTVRecording() {
    final var smartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).recordingStatus(RecordingStatus.RECORDING).channel(1).build();
    final var updatedSmartTV = SmartTV.builder().id(1L).deviceStatus(DeviceStatus.ON).recordingStatus(RecordingStatus.NOT_RECORDING).channel(1).build();
    final ResponseEntity<String> response = ResponseEntity.accepted().build();

    when(smartTVService.fetchSmartTVById(1L)).thenReturn(smartTV);
    when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(response);
    when(smartTVService.saveSmartTV(updatedSmartTV)).thenReturn(updatedSmartTV);

    defaultSmartTVActionResolverService.stopSmartTVRecording(smartTV.getId());

    verify(smartTVService).saveSmartTV(updatedSmartTV);
  }

}