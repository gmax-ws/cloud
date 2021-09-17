package scalable.solutions.orders.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> {

	Order findByDescription(String description);
}