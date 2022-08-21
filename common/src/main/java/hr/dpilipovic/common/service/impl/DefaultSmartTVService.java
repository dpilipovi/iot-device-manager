package hr.dpilipovic.common.service.impl;

import hr.dpilipovic.common.converter.SmartTVConverter;
import hr.dpilipovic.common.dto.SmartTVDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.SmartTV;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.model.codebook.RecordingStatus;
import hr.dpilipovic.common.repository.SmartTVRepository;
import hr.dpilipovic.common.service.SmartTVService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultSmartTVService implements SmartTVService {

  private final SmartTVRepository smartTVRepository;
  private final SmartTVConverter smartTVConverter;
  private static final String NOT_FOUND_MESSAGE = "Smart TV with ID %s not found";

  @Override
  public SmartTVDto getSmartTVById(final Long smartTVId) {
    return smartTVConverter.convert(fetchSmartTVById(smartTVId));
  }

  @Override
  public DeviceStatus getSmartTVStatusById(final Long smartTVId) {
    return fetchSmartTVById(smartTVId).getDeviceStatus();
  }

  @Override
  public SmartTVDto updateSmartTV(final SmartTVDto smartTVDto) {
    if (!smartTVExistsById(smartTVDto.getId())) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, smartTVDto.getId()));
    }

    final var smartTV = fetchSmartTVById(smartTVDto.getId());
    smartTV.setName(smartTVDto.getName());

    return smartTVConverter.convert(saveSmartTV(smartTV));
  }

  @Override
  public SmartTVDto createSmartTV(final SmartTVDto smartTVDto) {
    final var smartTV = smartTVConverter.convert(smartTVDto);
    smartTV.setDeviceType(DeviceType.SMART_TV);
    smartTV.setDeviceStatus(DeviceStatus.OFF);
    smartTV.setRecordingStatus(RecordingStatus.NOT_RECORDING);
    smartTV.setChannel(1);

    return smartTVConverter.convert(saveSmartTV(smartTV));
  }

  @Override
  @Transactional
  public void deleteSmartTVById(final Long smartTVId) {
    if (!smartTVExistsById(smartTVId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, smartTVId));
    }

    smartTVRepository.deleteById(smartTVId);
  }

  @Override
  public SmartTV saveSmartTV(final SmartTV smartTV) {
    return smartTVRepository.save(smartTV);
  }

  @Override
  public boolean smartTVExistsById(final Long smartTVId) {
    return smartTVRepository.existsById(smartTVId);
  }

  @Override
  public SmartTV fetchSmartTVById(final Long smartTVId) {
    return smartTVRepository.findById(smartTVId)
        .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, smartTVId)));
  }

}
