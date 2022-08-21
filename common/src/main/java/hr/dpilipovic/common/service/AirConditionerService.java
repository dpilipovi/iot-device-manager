package hr.dpilipovic.common.service;

import hr.dpilipovic.common.dto.AirConditionerDto;
import hr.dpilipovic.common.model.AirConditioner;
import hr.dpilipovic.common.model.codebook.DeviceStatus;

public interface AirConditionerService {

  AirConditionerDto getAirConditionerById(Long airConditionerId);

  DeviceStatus getAirConditionerStatusById(Long airConditionerId);

  AirConditionerDto updateAirConditioner(AirConditionerDto airConditionerDto);

  AirConditionerDto createAirConditioner(AirConditionerDto airConditionerDto);

  void deleteAirConditionerById(Long airConditionerId);

  AirConditioner saveAirConditioner(AirConditioner airConditioner);

  boolean airConditionerExistsById(Long airConditionerId);

  AirConditioner fetchAirConditionerById(Long airConditionerId);

}
