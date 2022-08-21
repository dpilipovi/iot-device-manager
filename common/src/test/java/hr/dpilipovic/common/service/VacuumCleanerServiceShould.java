package hr.dpilipovic.common.service;

import hr.dpilipovic.common.converter.VacuumCleanerConverter;
import hr.dpilipovic.common.dto.VacuumCleanerDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.VacuumCleaner;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.repository.VacuumCleanerRepository;
import hr.dpilipovic.common.service.impl.DefaultVacuumCleanerService;
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
class VacuumCleanerServiceShould {

  private VacuumCleanerService vacuumCleanerService;

  @Mock
  private VacuumCleanerRepository vacuumCleanerRepository;

  @Mock
  private VacuumCleanerConverter vacuumCleanerConverter;

  private static final String NOT_FOUND_MESSAGE = "Vacuum cleaner with ID %s not found";

  @BeforeEach
  void setup() {
    this.vacuumCleanerService = new DefaultVacuumCleanerService(vacuumCleanerRepository, vacuumCleanerConverter);
  }

  @Test
  void getVacuumCleanerById() {
    final var vacuumCleaner = buildVacuumCleaner();
    final var vacuumCleanerDto = buildVacuumCleanerDto();

    when(vacuumCleanerRepository.findById(vacuumCleaner.getId())).thenReturn(Optional.of(vacuumCleaner));
    when(vacuumCleanerConverter.convert(vacuumCleaner)).thenReturn(vacuumCleanerDto);

    assertThat(vacuumCleanerService.getVacuumCleanerById(vacuumCleaner.getId())).isEqualTo(vacuumCleanerDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToFetchVacuumCleanerThatDoesNotExist() {
    final var vacuumCleanerId = 1L;

    try {
      vacuumCleanerService.getVacuumCleanerById(vacuumCleanerId);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, vacuumCleanerId));
    }
  }

  @Test
  void getVacuumCleanerStatusById() {
    final var vacuumCleaner = buildVacuumCleaner();

    when(vacuumCleanerRepository.findById(vacuumCleaner.getId())).thenReturn(Optional.of(vacuumCleaner));

    assertThat(vacuumCleanerService.getVacuumCleanerStatusById(vacuumCleaner.getId())).isEqualTo(vacuumCleaner.getDeviceStatus());
  }

  @Test
  void updateVacuumCleaner() {
    final var updateVacuumCleanerDto = VacuumCleanerDto.builder().id(1L).name("My VacuumCleaner").build();
    final var updatedVacuumCleanerDto = buildVacuumCleanerDto();
    updatedVacuumCleanerDto.setName(updateVacuumCleanerDto.getName());
    final var vacuumCleaner = buildVacuumCleaner();
    final var updatedVacuumCleaner = buildVacuumCleaner();
    updatedVacuumCleaner.setName(updateVacuumCleanerDto.getName());

    when(vacuumCleanerRepository.existsById(updateVacuumCleanerDto.getId())).thenReturn(true);
    when(vacuumCleanerRepository.findById(updateVacuumCleanerDto.getId())).thenReturn(Optional.of(vacuumCleaner));
    when(vacuumCleanerRepository.save(any(VacuumCleaner.class))).thenReturn(updatedVacuumCleaner);
    when(vacuumCleanerConverter.convert(updatedVacuumCleaner)).thenReturn(updatedVacuumCleanerDto);

    assertThat(vacuumCleanerService.updateVacuumCleaner(updateVacuumCleanerDto)).isEqualTo(updatedVacuumCleanerDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToUpdateVacuumCleanerThatDoesNotExist() {
    final var vacuumCleanerDto = buildVacuumCleanerDto();

    try {
      vacuumCleanerService.updateVacuumCleaner(vacuumCleanerDto);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, vacuumCleanerDto.getId()));
    }
  }

  @Test
  void createVacuumCleaner() {
    final var createVacuumCleanerDto = VacuumCleanerDto.builder().id(1L).name("Air conditioner").build();
    final var createdVacuumCleanerDto = buildVacuumCleanerDto();
    final var vacuumCleaner = buildVacuumCleaner();

    when(vacuumCleanerConverter.convert(createVacuumCleanerDto)).thenReturn(vacuumCleaner);
    when(vacuumCleanerRepository.save(vacuumCleaner)).thenReturn(vacuumCleaner);
    when(vacuumCleanerConverter.convert(vacuumCleaner)).thenReturn(createdVacuumCleanerDto);

    assertThat(vacuumCleanerService.createVacuumCleaner(createVacuumCleanerDto)).isEqualTo(createdVacuumCleanerDto);
  }

  @Test
  void deleteVacuumCleanerById() {
    final var vacuumCleanerId = 1L;

    when(vacuumCleanerRepository.existsById(vacuumCleanerId)).thenReturn(true);

    vacuumCleanerService.deleteVacuumCleanerById(vacuumCleanerId);

    verify(vacuumCleanerRepository).deleteById(vacuumCleanerId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToDeleteVacuumCleanerThatDoesNotExist() {
    final var vacuumCleanerId = 1L;

    try {
      vacuumCleanerService.deleteVacuumCleanerById(vacuumCleanerId);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, vacuumCleanerId));
    }
  }

  private VacuumCleaner buildVacuumCleaner() {
    return VacuumCleaner.builder().id(1L).name("VacuumCleaner").deviceStatus(DeviceStatus.OFF).deviceType(DeviceType.VACUUM_CLEANER).build();
  }

  private VacuumCleanerDto buildVacuumCleanerDto() {
    return VacuumCleanerDto.builder().id(1L).name("VacuumCleaner").deviceStatus(DeviceStatus.OFF).deviceType(DeviceType.VACUUM_CLEANER).build();
  }

}