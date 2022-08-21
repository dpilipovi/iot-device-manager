package hr.dpilipovic.messageprocessor.listener;

public interface VacuumCleanerActionListener {

  void processPowerOnMessage(Long vacuumCleanerId);

  void processPowerOffMessage(Long vacuumCleanerId);
  

}
