package hr.dpilipovic.common.repository;

import hr.dpilipovic.common.model.VacuumCleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacuumCleanerRepository extends JpaRepository<VacuumCleaner, Long> {

  boolean existsById(Long vacuumCleanerId);

}
