package hr.dpilipovic.api.service.action;

import hr.dpilipovic.common.dto.ChangeTemperatureRequestDto;

public interface ChangeTemperatureActionInitiatorService {

  void setTemperature(ChangeTemperatureRequestDto changeTemperatureRequestDto);

}
