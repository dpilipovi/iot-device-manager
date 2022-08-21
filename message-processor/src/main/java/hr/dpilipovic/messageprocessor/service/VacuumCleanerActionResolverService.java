package hr.dpilipovic.messageprocessor.service;

public interface VacuumCleanerActionResolverService {

  void powerVacuumCleanerOn(Long vacuumCleanerId);

  void powerVacuumCleanerOff(Long vacuumCleanerId);

}
