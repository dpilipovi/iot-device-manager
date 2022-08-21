package hr.dpilipovic.common.repository;

import hr.dpilipovic.common.model.AirConditioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirConditionerRepository extends JpaRepository<AirConditioner, Long> {

  boolean existsById(Long airConditionerId);

}
