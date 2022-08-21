package hr.dpilipovic.common.service;

import hr.dpilipovic.common.converter.AirConditionerConverter;
import hr.dpilipovic.common.dto.AirConditionerDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.AirConditioner;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.repository.AirConditionerRepository;
import hr.dpilipovic.common.service.impl.DefaultAirConditionerService;
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
class AirConditionerServiceShould {

  private AirConditionerService airConditionerService;

  @Mock
  private AirConditionerRepository airConditionerRepository;

  @Mock
  private AirConditionerConverter airConditionerConverter;

  private static final String NOT_FOUND_MESSAGE = "Air conditioner with ID %s not found";

  @BeforeEach
  void setup() {
    this.airConditionerService = new DefaultAirConditionerService(airConditionerRepository, airConditionerConverter);
  }

  @Test
  void getAirConditionerById() {
    final var airConditioner = buildAirConditioner();
    final var airConditionerDto = buildAirConditionerDto();

    when(airConditionerRepository.findById(airConditioner.getId())).thenReturn(Optional.of(airConditioner));
    when(airConditionerConverter.convert(airConditioner)).thenReturn(airConditionerDto);

    assertThat(airConditionerService.getAirConditionerById(airConditioner.getId())).isEqualTo(airConditionerDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToFetchAirConditionerThatDoesNotExist() {
    final var airConditionerId = 1L;

    try {
      airConditionerService.getAirConditionerById(airConditionerId);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, airConditionerId));
    }
  }

  @Test
  void getAirConditionerStatusById() {
    final var airConditioner = buildAirConditioner();

    when(airConditionerRepository.findById(airConditioner.getId())).thenReturn(Optional.of(airConditioner));

    assertThat(airConditionerService.getAirConditionerStatusById(airConditioner.getId())).isEqualTo(airConditioner.getDeviceStatus());
  }

  @Test
  void updateAirConditioner() {
    final var updateAirConditionerDto = AirConditionerDto.builder().id(1L).name("My air conditioner").build();
    final var updatedAirConditionerDto = buildAirConditionerDto();
    updatedAirConditionerDto.setName(updateAirConditionerDto.getName());
    final var airConditioner = buildAirConditioner();
    final var updatedAirConditioner = buildAirConditioner();
    updatedAirConditioner.setName(updateAirConditionerDto.getName());

    when(airConditionerRepository.existsById(updateAirConditionerDto.getId())).thenReturn(true);
    when(airConditionerRepository.findById(updateAirConditionerDto.getId())).thenReturn(Optional.of(airConditioner));
    when(airConditionerRepository.save(any(AirConditioner.class))).thenReturn(updatedAirConditioner);
    when(airConditionerConverter.convert(updatedAirConditioner)).thenReturn(updatedAirConditionerDto);

    assertThat(airConditionerService.updateAirConditioner(updateAirConditionerDto)).isEqualTo(updatedAirConditionerDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToUpdateAirConditionerThatDoesNotExist() {
    final var airConditionerDto = buildAirConditionerDto();

    try {
      airConditionerService.updateAirConditioner(airConditionerDto);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, airConditionerDto.getId()));
    }
  }

  @Test
  void createAirConditioner() {
    final var createAirConditionerDto = AirConditionerDto.builder().id(1L).name("Air conditioner").build();
    final var createdAirConditionerDto = buildAirConditionerDto();
    final var airConditioner = buildAirConditioner();

    when(airConditionerConverter.convert(createAirConditionerDto)).thenReturn(airConditioner);
    when(airConditionerRepository.save(airConditioner)).thenReturn(airConditioner);
    when(airConditionerConverter.convert(airConditioner)).thenReturn(createdAirConditionerDto);

    assertThat(airConditionerService.createAirConditioner(createAirConditionerDto)).isEqualTo(createdAirConditionerDto);
  }

  @Test
  void deleteAirConditionerById() {
    final var airConditionerId = 1L;

    when(airConditionerRepository.existsById(airConditionerId)).thenReturn(true);

    airConditionerService.deleteAirConditionerById(airConditionerId);

    verify(airConditionerRepository).deleteById(airConditionerId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToDeleteAirConditionerThatDoesNotExist() {
    final var airConditionerId = 1L;

    try {
      airConditionerService.deleteAirConditionerById(airConditionerId);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, airConditionerId));
    }
  }

  private AirConditioner buildAirConditioner() {
    return AirConditioner.builder().id(1L).name("Air conditioner").deviceStatus(DeviceStatus.OFF).deviceType(DeviceType.AIR_CONDITIONER).build();
  }

  private AirConditionerDto buildAirConditionerDto() {
    return AirConditionerDto.builder().id(1L).name("Air conditioner").deviceStatus(DeviceStatus.OFF).deviceType(DeviceType.AIR_CONDITIONER).build();
  }

}