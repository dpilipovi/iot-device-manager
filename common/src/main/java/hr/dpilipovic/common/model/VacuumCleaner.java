package hr.dpilipovic.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacuum_cleaner")
@EqualsAndHashCode(callSuper = true)
public class VacuumCleaner extends Device {

  @Column(name = "home", nullable = false)
  private String home;

}
