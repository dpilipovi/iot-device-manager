package hr.dpilipovic.messageprocessor.listener;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;

public interface AirConditionerActionListener {

  void processPowerOnMessage(Long airConditionerId);

  void processPowerOffMessage(Long airConditionerId);

  void processChangeTemperatureMessage(ChangeTemperatureRequestDto changeTemperatureRequestDto);

}
