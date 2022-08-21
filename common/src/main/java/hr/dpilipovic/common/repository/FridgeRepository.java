package hr.dpilipovic.common.repository;

import hr.dpilipovic.common.model.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {

  boolean existsById(Long fridgeId);

}
