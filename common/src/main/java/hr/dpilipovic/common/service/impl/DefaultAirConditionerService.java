package hr.dpilipovic.common.service.impl;

import hr.dpilipovic.common.converter.AirConditionerConverter;
import hr.dpilipovic.common.dto.AirConditionerDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.AirConditioner;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.repository.AirConditionerRepository;
import hr.dpilipovic.common.service.AirConditionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultAirConditionerService implements AirConditionerService {

  private final AirConditionerRepository airConditionerRepository;
  private final AirConditionerConverter airConditionerConverter;
  private static final String NOT_FOUND_MESSAGE = "Air conditioner with ID %s not found";

  @Override
  public AirConditionerDto getAirConditionerById(final Long airConditionerId) {
    return airConditionerConverter.convert(fetchAirConditionerById(airConditionerId));
  }

  @Override
  public DeviceStatus getAirConditionerStatusById(final Long airConditionerId) {
    return fetchAirConditionerById(airConditionerId).getDeviceStatus();
  }

  @Override
  public AirConditionerDto updateAirConditioner(final AirConditionerDto airConditionerDto) {
    if (!airConditionerExistsById(airConditionerDto.getId())) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, airConditionerDto.getId()));
    }

    final var airConditioner = fetchAirConditionerById(airConditionerDto.getId());
    airConditioner.setName(airConditionerDto.getName());

    return airConditionerConverter.convert(saveAirConditioner(airConditioner));
  }

  @Override
  public AirConditionerDto createAirConditioner(final AirConditionerDto airConditionerDto) {
    final var airConditioner = airConditionerConverter.convert(airConditionerDto);
    airConditioner.setDeviceType(DeviceType.AIR_CONDITIONER);
    airConditioner.setDeviceStatus(DeviceStatus.OFF);
    airConditioner.setTemperature(22);

    return airConditionerConverter.convert(saveAirConditioner(airConditioner));
  }

  @Override
  @Transactional
  public void deleteAirConditionerById(final Long airConditionerId) {
    if (!airConditionerExistsById(airConditionerId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, airConditionerId));
    }

    airConditionerRepository.deleteById(airConditionerId);
  }

  @Override
  public AirConditioner saveAirConditioner(final AirConditioner airConditioner) {
    return airConditionerRepository.save(airConditioner);
  }

  @Override
  public boolean airConditionerExistsById(final Long airConditionerId) {
    return airConditionerRepository.existsById(airConditionerId);
  }

  @Override
  public AirConditioner fetchAirConditionerById(final Long airConditionerId) {
    return airConditionerRepository.findById(airConditionerId)
        .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, airConditionerId)));
  }

}
