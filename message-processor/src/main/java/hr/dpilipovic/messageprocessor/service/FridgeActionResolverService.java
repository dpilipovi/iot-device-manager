package hr.dpilipovic.messageprocessor.service;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;

public interface FridgeActionResolverService {

  void powerFridgeOn(Long fridgeId);

  void powerFridgeOff(Long fridgeId);

  void changeFridgeTemperature(ChangeTemperatureRequestDto changeTemperatureRequestDto);

}
