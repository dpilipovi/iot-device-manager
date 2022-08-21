package hr.dpilipovic.common.service.impl;

import hr.dpilipovic.common.converter.FridgeConverter;
import hr.dpilipovic.common.dto.FridgeDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.Fridge;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.repository.FridgeRepository;
import hr.dpilipovic.common.service.FridgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultFridgeService implements FridgeService {

  private final FridgeRepository fridgeRepository;
  private final FridgeConverter fridgeConverter;
  private static final String NOT_FOUND_MESSAGE = "Fridge with ID %s not found";

  @Override
  public FridgeDto getFridgeById(final Long fridgeId) {
    return fridgeConverter.convert(fetchFridgeById(fridgeId));
  }

  @Override
  public DeviceStatus getFridgeStatusById(final Long fridgeId) {
    return fetchFridgeById(fridgeId).getDeviceStatus();
  }

  @Override
  public FridgeDto updateFridge(final FridgeDto fridgeDto) {
    if (!fridgeExistsById(fridgeDto.getId())) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, fridgeDto.getId()));
    }

    final var fridge = fetchFridgeById(fridgeDto.getId());
    fridge.setName(fridgeDto.getName());

    return fridgeConverter.convert(saveFridge(fridge));
  }

  @Override
  public FridgeDto createFridge(final FridgeDto fridgeDto) {
    final var fridge = fridgeConverter.convert(fridgeDto);
    fridge.setDeviceType(DeviceType.FRIDGE);
    fridge.setDeviceStatus(DeviceStatus.OFF);
    fridge.setTemperature(4);

    return fridgeConverter.convert(saveFridge(fridge));
  }

  @Override
  @Transactional
  public void deleteFridgeById(final Long fridgeId) {
    if (!fridgeExistsById(fridgeId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, fridgeId));
    }

    fridgeRepository.deleteById(fridgeId);
  }

  @Override
  public Fridge saveFridge(final Fridge fridge) {
    return fridgeRepository.save(fridge);
  }

  @Override
  public boolean fridgeExistsById(final Long fridgeId) {
    return fridgeRepository.existsById(fridgeId);
  }

  @Override
  public Fridge fetchFridgeById(final Long fridgeId) {
    return fridgeRepository.findById(fridgeId)
        .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, fridgeId)));
  }

}
