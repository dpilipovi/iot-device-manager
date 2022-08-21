package hr.dpilipovic.common.service;

import hr.dpilipovic.common.dto.FridgeDto;
import hr.dpilipovic.common.model.Fridge;
import hr.dpilipovic.common.model.codebook.DeviceStatus;

public interface FridgeService {

  FridgeDto getFridgeById(Long fridgeId);

  DeviceStatus getFridgeStatusById(Long fridgeId);

  FridgeDto updateFridge(FridgeDto fridgeDto);

  FridgeDto createFridge(FridgeDto fridgeDto);

  void deleteFridgeById(Long fridgeId);

  Fridge saveFridge(Fridge fridge);

  boolean fridgeExistsById(Long fridgeId);

  Fridge fetchFridgeById(Long fridgeId);

}
