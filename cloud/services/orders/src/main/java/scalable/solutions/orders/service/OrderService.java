package scalable.solutions.orders.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scalable.solutions.orders.dao.Order;
import scalable.solutions.orders.dao.OrderDTO;
import scalable.solutions.orders.dao.OrderDao;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public OrderDTO getOrder(long orderId) {
        Order order = orderDao.getOne(orderId);
        return OrderDTO.fromOrder(order);
    }

    public List<OrderDTO> getOrders() {
        List<Order> orders = orderDao.findAll();
        return orders.stream().map(OrderDTO::fromOrder).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO order) {
        Order entity = orderDao.save(new Order(order.getDescription()));
        return OrderDTO.fromOrder(entity);
    }

    @Transactional
    public OrderDTO updateOrder(OrderDTO order) {
        Order entity = orderDao.getOne(order.getId());
        entity.setDescription(order.getDescription());
        Order updatedEntity = orderDao.save(entity);
        return OrderDTO.fromOrder(updatedEntity);
    }

    @Transactional
    public OrderDTO deleteOrder(long orderId) {
        Order order = orderDao.getOne(orderId);
        orderDao.delete(order);
        return OrderDTO.fromOrder(order);
    }
}
