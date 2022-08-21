package hr.dpilipovic.common.model;

import hr.dpilipovic.common.action.ChangeTemperature;
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
@Table(name = "fridge")
@EqualsAndHashCode(callSuper = true)
public class Fridge extends Device implements ChangeTemperature {

  @Column(name = "temperature", nullable = false)
  private Integer temperature;

  @Override
  public void setTemperature(final Integer temperature) {
    this.temperature = temperature;
  }

}
