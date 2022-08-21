package hr.dpilipovic.api.service.action;

public interface PowerActionInitiatorService {

  void turnOn(Long deviceId);

  void turnOff(Long deviceId);

}
