package hr.dpilipovic.common.service;

import hr.dpilipovic.common.dto.SmartTVDto;
import hr.dpilipovic.common.model.SmartTV;
import hr.dpilipovic.common.model.codebook.DeviceStatus;

public interface SmartTVService {

  SmartTVDto getSmartTVById(Long smartTVId);

  DeviceStatus getSmartTVStatusById(Long smartTVId);

  SmartTVDto updateSmartTV(SmartTVDto smartTVDto);

  SmartTVDto createSmartTV(SmartTVDto smartTVDto);

  void deleteSmartTVById(Long smartTVId);

  SmartTV saveSmartTV(SmartTV smartTV);

  boolean smartTVExistsById(Long smartTVId);

  SmartTV fetchSmartTVById(Long smartTVId);

}
