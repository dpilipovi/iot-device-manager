package hr.dpilipovic.common.service.impl;

import hr.dpilipovic.common.converter.VacuumCleanerConverter;
import hr.dpilipovic.common.dto.VacuumCleanerDto;
import hr.dpilipovic.common.exception.EntityNotFoundException;
import hr.dpilipovic.common.model.VacuumCleaner;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import hr.dpilipovic.common.repository.VacuumCleanerRepository;
import hr.dpilipovic.common.service.VacuumCleanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultVacuumCleanerService implements VacuumCleanerService {

  private final VacuumCleanerRepository vacuumCleanerRepository;
  private final VacuumCleanerConverter vacuumCleanerConverter;
  private static final String NOT_FOUND_MESSAGE = "Vacuum cleaner with ID %s not found";

  @Override
  public VacuumCleanerDto getVacuumCleanerById(final Long vacuumCleanerId) {
    return vacuumCleanerConverter.convert(fetchVacuumCleanerById(vacuumCleanerId));
  }

  @Override
  public DeviceStatus getVacuumCleanerStatusById(final Long vacuumCleanerId) {
    return fetchVacuumCleanerById(vacuumCleanerId).getDeviceStatus();
  }

  @Override
  public VacuumCleanerDto updateVacuumCleaner(final VacuumCleanerDto vacuumCleanerDto) {
    if (!vacuumCleanerExistsById(vacuumCleanerDto.getId())) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, vacuumCleanerDto.getId()));
    }

    final var vacuumCleaner = fetchVacuumCleanerById(vacuumCleanerDto.getId());
    vacuumCleaner.setName(vacuumCleanerDto.getName());
    vacuumCleaner.setHome(vacuumCleanerDto.getHome());

    return vacuumCleanerConverter.convert(saveVacuumCleaner(vacuumCleaner));
  }

  @Override
  public VacuumCleanerDto createVacuumCleaner(final VacuumCleanerDto vacuumCleanerDto) {
    final var vacuumCleaner = vacuumCleanerConverter.convert(vacuumCleanerDto);
    vacuumCleaner.setDeviceType(DeviceType.VACUUM_CLEANER);
    vacuumCleaner.setDeviceStatus(DeviceStatus.OFF);

    return vacuumCleanerConverter.convert(saveVacuumCleaner(vacuumCleaner));
  }

  @Override
  @Transactional
  public void deleteVacuumCleanerById(final Long vacuumCleanerId) {
    if (!vacuumCleanerExistsById(vacuumCleanerId)) {
      throw new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, vacuumCleanerId));
    }

    vacuumCleanerRepository.deleteById(vacuumCleanerId);
  }

  @Override
  public VacuumCleaner saveVacuumCleaner(final VacuumCleaner vacuumCleaner) {
    return vacuumCleanerRepository.save(vacuumCleaner);
  }

  @Override
  public boolean vacuumCleanerExistsById(final Long vacuumCleanerId) {
    return vacuumCleanerRepository.existsById(vacuumCleanerId);
  }

  @Override
  public VacuumCleaner fetchVacuumCleanerById(final Long vacuumCleanerId) {
    return vacuumCleanerRepository.findById(vacuumCleanerId)
        .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_MESSAGE, vacuumCleanerId)));
  }

}
