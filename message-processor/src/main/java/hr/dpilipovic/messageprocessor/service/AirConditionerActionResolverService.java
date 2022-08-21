package hr.dpilipovic.messageprocessor.service;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;

public interface AirConditionerActionResolverService {

  void powerAirConditionerOn(Long airConditionerId);

  void powerAirConditionerOff(Long airConditionerId);

  void changeAirConditionerTemperature(ChangeTemperatureRequestDto changeTemperatureRequestDto);

}
