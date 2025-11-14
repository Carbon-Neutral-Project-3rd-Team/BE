package carbon.carbon_be.domain.point.repository;

import carbon.carbon_be.domain.point.entity.PointBalance;
import carbon.carbon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointBalanceRepository extends JpaRepository<PointBalance, Long> {
    Optional<PointBalance> findByUser(User user);
}
