package carbon.carbon_be.domain.totalstep.repository;

import carbon.carbon_be.domain.totalstep.entity.TotalStep;
import carbon.carbon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalStepRepository extends JpaRepository<TotalStep, Long> {
   Optional<TotalStep> findByUser(User user);
}
