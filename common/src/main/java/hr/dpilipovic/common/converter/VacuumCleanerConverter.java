package hr.dpilipovic.common.converter;

import hr.dpilipovic.common.dto.VacuumCleanerDto;
import hr.dpilipovic.common.model.VacuumCleaner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VacuumCleanerConverter implements Converter<VacuumCleaner, VacuumCleanerDto> {

  @Override
  public VacuumCleanerDto convert(final VacuumCleaner vacuumCleaner) {
    return VacuumCleanerDto.builder()
        .id(vacuumCleaner.getId())
        .name(vacuumCleaner.getName())
        .deviceStatus(vacuumCleaner.getDeviceStatus())
        .deviceType(vacuumCleaner.getDeviceType())
        .lastActivityTime(vacuumCleaner.getLastActivityTime())
        .home(vacuumCleaner.getHome())
        .build();
  }

  public VacuumCleaner convert(final VacuumCleanerDto vacuumCleanerDto) {
    return VacuumCleaner.builder()
        .name(vacuumCleanerDto.getName())
        .deviceStatus(vacuumCleanerDto.getDeviceStatus())
        .deviceType(vacuumCleanerDto.getDeviceType())
        .lastActivityTime(vacuumCleanerDto.getLastActivityTime())
        .home(vacuumCleanerDto.getHome())
        .build();
  }

}
