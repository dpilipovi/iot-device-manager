package hr.dpilipovic.common.repository;

import hr.dpilipovic.common.model.SmartTV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartTVRepository extends JpaRepository<SmartTV, Long> {

  boolean existsById(Long smartTVId);

}
