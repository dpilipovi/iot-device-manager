package hr.dpilipovic.common.service;

import hr.dpilipovic.common.converter.SmartTVConverter;
import hr.dpilipovic.common.dto.SmartTVDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.SmartTV;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.repository.SmartTVRepository;
import hr.dpilipovic.common.service.impl.DefaultSmartTVService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmartTVServiceShould {

  private SmartTVService smartTVService;

  @Mock
  private SmartTVRepository smartTVRepository;

  @Mock
  private SmartTVConverter smartTVConverter;

  private static final String NOT_FOUND_MESSAGE = "Smart TV with ID %s not found";

  @BeforeEach
  void setup() {
    this.smartTVService = new DefaultSmartTVService(smartTVRepository, smartTVConverter);
  }

  @Test
  void getSmartTVById() {
    final var smartTV = buildSmartTV();
    final var smartTVDto = buildSmartTVDto();

    when(smartTVRepository.findById(smartTV.getId())).thenReturn(Optional.of(smartTV));
    when(smartTVConverter.convert(smartTV)).thenReturn(smartTVDto);

    assertThat(smartTVService.getSmartTVById(smartTV.getId())).isEqualTo(smartTVDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToFetchSmartTVThatDoesNotExist() {
    final var smartTVId = 1L;

    try {
      smartTVService.getSmartTVById(smartTVId);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, smartTVId));
    }
  }

  @Test
  void getSmartTVStatusById() {
    final var smartTV = buildSmartTV();

    when(smartTVRepository.findById(smartTV.getId())).thenReturn(Optional.of(smartTV));

    assertThat(smartTVService.getSmartTVStatusById(smartTV.getId())).isEqualTo(smartTV.getDeviceStatus());
  }

  @Test
  void updateSmartTV() {
    final var updateSmartTVDto = SmartTVDto.builder().id(1L).name("My smart TV").build();
    final var updatedSmartTVDto = buildSmartTVDto();
    updatedSmartTVDto.setName(updateSmartTVDto.getName());
    final var smartTV = buildSmartTV();
    final var updatedSmartTV = buildSmartTV();
    updatedSmartTV.setName(updateSmartTVDto.getName());

    when(smartTVRepository.existsById(updateSmartTVDto.getId())).thenReturn(true);
    when(smartTVRepository.findById(updateSmartTVDto.getId())).thenReturn(Optional.of(smartTV));
    when(smartTVRepository.save(any(SmartTV.class))).thenReturn(updatedSmartTV);
    when(smartTVConverter.convert(updatedSmartTV)).thenReturn(updatedSmartTVDto);

    assertThat(smartTVService.updateSmartTV(updateSmartTVDto)).isEqualTo(updatedSmartTVDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToUpdateSmartTVThatDoesNotExist() {
    final var smartTVDto = buildSmartTVDto();

    try {
      smartTVService.updateSmartTV(smartTVDto);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, smartTVDto.getId()));
    }
  }

  @Test
  void createSmartTV() {
    final var createSmartTVDto = SmartTVDto.builder().id(1L).name("Air conditioner").build();
    final var createdSmartTVDto = buildSmartTVDto();
    final var smartTV = buildSmartTV();

    when(smartTVConverter.convert(createSmartTVDto)).thenReturn(smartTV);
    when(smartTVRepository.save(smartTV)).thenReturn(smartTV);
    when(smartTVConverter.convert(smartTV)).thenReturn(createdSmartTVDto);

    assertThat(smartTVService.createSmartTV(createSmartTVDto)).isEqualTo(createdSmartTVDto);
  }

  @Test
  void deleteSmartTVById() {
    final var smartTVId = 1L;

    when(smartTVRepository.existsById(smartTVId)).thenReturn(true);

    smartTVService.deleteSmartTVById(smartTVId);

    verify(smartTVRepository).deleteById(smartTVId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToDeleteSmartTVThatDoesNotExist() {
    final var smartTVId = 1L;

    try {
      smartTVService.deleteSmartTVById(smartTVId);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, smartTVId));
    }
  }

  private SmartTV buildSmartTV() {
    return SmartTV.builder().id(1L).name("SmartTV").deviceStatus(DeviceStatus.OFF).deviceType(DeviceType.SMART_TV).build();
  }

  private SmartTVDto buildSmartTVDto() {
    return SmartTVDto.builder().id(1L).name("SmartTV").deviceStatus(DeviceStatus.OFF).deviceType(DeviceType.SMART_TV).build();
  }

}