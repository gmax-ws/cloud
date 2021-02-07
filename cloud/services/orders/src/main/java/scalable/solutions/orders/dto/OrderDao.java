package scalable.solutions.orders.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderDao extends JpaRepository<Order, Long> {

	Order findByDescription(String description);
}