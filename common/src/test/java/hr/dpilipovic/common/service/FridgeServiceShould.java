package hr.dpilipovic.common.service;

import hr.dpilipovic.common.converter.FridgeConverter;
import hr.dpilipovic.common.dto.FridgeDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.Fridge;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.repository.FridgeRepository;
import hr.dpilipovic.common.service.impl.DefaultFridgeService;
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
class FridgeServiceShould {

  private FridgeService fridgeService;

  @Mock
  private FridgeRepository fridgeRepository;

  @Mock
  private FridgeConverter fridgeConverter;

  private static final String NOT_FOUND_MESSAGE = "Fridge with ID %s not found";

  @BeforeEach
  void setup() {
    this.fridgeService = new DefaultFridgeService(fridgeRepository, fridgeConverter);
  }

  @Test
  void getFridgeById() {
    final var fridge = buildFridge();
    final var fridgeDto = buildFridgeDto();

    when(fridgeRepository.findById(fridge.getId())).thenReturn(Optional.of(fridge));
    when(fridgeConverter.convert(fridge)).thenReturn(fridgeDto);

    assertThat(fridgeService.getFridgeById(fridge.getId())).isEqualTo(fridgeDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToFetchFridgeThatDoesNotExist() {
    final var fridgeId = 1L;

    try {
      fridgeService.getFridgeById(fridgeId);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, fridgeId));
    }
  }

  @Test
  void getFridgeStatusById() {
    final var fridge = buildFridge();

    when(fridgeRepository.findById(fridge.getId())).thenReturn(Optional.of(fridge));

    assertThat(fridgeService.getFridgeStatusById(fridge.getId())).isEqualTo(fridge.getDeviceStatus());
  }

  @Test
  void updateFridge() {
    final var updateFridgeDto = FridgeDto.builder().id(1L).name("My fridge").build();
    final var updatedFridgeDto = buildFridgeDto();
    updatedFridgeDto.setName(updateFridgeDto.getName());
    final var fridge = buildFridge();
    final var updatedFridge = buildFridge();
    updatedFridge.setName(updateFridgeDto.getName());

    when(fridgeRepository.existsById(updateFridgeDto.getId())).thenReturn(true);
    when(fridgeRepository.findById(updateFridgeDto.getId())).thenReturn(Optional.of(fridge));
    when(fridgeRepository.save(any(Fridge.class))).thenReturn(updatedFridge);
    when(fridgeConverter.convert(updatedFridge)).thenReturn(updatedFridgeDto);

    assertThat(fridgeService.updateFridge(updateFridgeDto)).isEqualTo(updatedFridgeDto);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToUpdateFridgeThatDoesNotExist() {
    final var fridgeDto = buildFridgeDto();

    try {
      fridgeService.updateFridge(fridgeDto);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, fridgeDto.getId()));
    }
  }

  @Test
  void createFridge() {
    final var createFridgeDto = FridgeDto.builder().id(1L).name("Air conditioner").build();
    final var createdFridgeDto = buildFridgeDto();
    final var fridge = buildFridge();

    when(fridgeConverter.convert(createFridgeDto)).thenReturn(fridge);
    when(fridgeRepository.save(fridge)).thenReturn(fridge);
    when(fridgeConverter.convert(fridge)).thenReturn(createdFridgeDto);

    assertThat(fridgeService.createFridge(createFridgeDto)).isEqualTo(createdFridgeDto);
  }

  @Test
  void deleteFridgeById() {
    final var fridgeId = 1L;

    when(fridgeRepository.existsById(fridgeId)).thenReturn(true);

    fridgeService.deleteFridgeById(fridgeId);

    verify(fridgeRepository).deleteById(fridgeId);
  }

  @Test
  void throwEntityNotFoundException_whenTryingToDeleteFridgeThatDoesNotExist() {
    final var fridgeId = 1L;

    try {
      fridgeService.deleteFridgeById(fridgeId);
      failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
    } catch (final EntityNotFoundException e) {
      assertThat(e.getMessage()).isEqualTo(String.format(NOT_FOUND_MESSAGE, fridgeId));
    }
  }

  private Fridge buildFridge() {
    return Fridge.builder().id(1L).name("Fridge").deviceStatus(DeviceStatus.OFF).deviceType(DeviceType.FRIDGE).build();
  }

  private FridgeDto buildFridgeDto() {
    return FridgeDto.builder().id(1L).name("Fridge").deviceStatus(DeviceStatus.OFF).deviceType(DeviceType.FRIDGE).build();
  }

}