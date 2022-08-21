package hr.dpilipovic.messageprocessor.listener;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;

public interface FridgeActionListener {

  void processPowerOnMessage(Long fridgeId);

  void processPowerOffMessage(Long fridgeId);
  
  void processChangeTemperatureMessage(ChangeTemperatureRequestDto changeTemperatureRequestDto);

}
