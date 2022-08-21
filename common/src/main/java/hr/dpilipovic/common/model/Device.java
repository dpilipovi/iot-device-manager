package hr.dpilipovic.common.model;

import hr.dpilipovic.common.action.Power;
import hr.dpilipovic.common.model.codebook.DeviceStatus;
import hr.dpilipovic.common.model.codebook.DeviceType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Device implements Power {

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "id_seq", strategy = GenerationType.SEQUENCE)
  protected Long id;

  @Column(name = "name", nullable = false)
  protected String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "device_type", nullable = false, updatable = false)
  protected DeviceType deviceType;

  @Enumerated(EnumType.STRING)
  @Column(name = "device_status", nullable = false)
  protected DeviceStatus deviceStatus;

  @UpdateTimestamp
  @Column(name = "last_activity_time")
  protected LocalDateTime lastActivityTime;

  @Override
  public void turnOn() {
    this.deviceStatus = DeviceStatus.ON;
  }

  @Override
  public void turnOff() {
    this.deviceStatus = DeviceStatus.OFF;
  }

}
