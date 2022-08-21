package hr.dpilipovic.common.service;

import hr.dpilipovic.common.dto.VacuumCleanerDto;
import hr.dpilipovic.common.model.VacuumCleaner;
import hr.dpilipovic.common.model.codebook.DeviceStatus;

public interface VacuumCleanerService {

  VacuumCleanerDto getVacuumCleanerById(Long vacuumCleanerId);

  DeviceStatus getVacuumCleanerStatusById(Long vacuumCleanerId);

  VacuumCleanerDto updateVacuumCleaner(VacuumCleanerDto vacuumCleanerDto);

  VacuumCleanerDto createVacuumCleaner(VacuumCleanerDto vacuumCleanerDto);

  void deleteVacuumCleanerById(Long vacuumCleanerId);

  VacuumCleaner saveVacuumCleaner(VacuumCleaner vacuumCleaner);

  boolean vacuumCleanerExistsById(Long vaccumCleanerId);

  VacuumCleaner fetchVacuumCleanerById(Long vacuumCleanerId);

}
