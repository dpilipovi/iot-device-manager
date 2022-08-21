package hr.dpilipovic.common.rabbit.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class RabbitQueueProperties {

  public String airConditionerPowerOnQueue() {
    return "air_conditioner_power_on";
  }
  
  public String airConditionerPowerOffQueue() {
    return "air_conditioner_power_off";
  }

  public String airConditionerChangeTemperatureQueue() {
    return "air_conditioner_change_temperature";
  }

  public String fridgePowerOnQueue() {
    return "fridge_power_on";
  }

  public String fridgePowerOffQueue() {
    return "fridge_power_off";
  }

  public String fridgeChangeTemperatureQueue() {
    return "fridge_change_temperature";
  }

  public String smartTVPowerOnQueue() {
    return "smart_tv_power_on";
  }

  public String smartTVPowerOffQueue() {
    return "smart_tv_power_off";
  }

  public String smartTVStartRecordingQueue() {
    return "smart_tv_start_recording";
  }

  public String smartTVPauseRecordingQueue() {
    return "smart_tv_pause_recording";
  }

  public String smartTVResumeRecordingQueue() {
    return "smart_tv_resume_recording";
  }

  public String smartTVStopRecordingQueue() {
    return "smart_tv_stop_recording";
  }

  public String vacuumCleanerPowerOnQueue() {
    return "vacuum_cleaner_power_on";
  }

  public String vacuumCleanerPowerOffQueue() {
    return "vacuum_cleaner_power_off";
  }
  
}
