package scalable.solutions.orders.controlers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import scalable.solutions.orders.dto.Order;
import scalable.solutions.orders.dto.OrderDTO;
import scalable.solutions.orders.dto.OrderDao;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrderDao orderDao;

    @ApiOperation(value = "Get all orders")
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @GetMapping(value = "/orders")
    public ResponseEntity<Collection<OrderDTO>> orders() {
        List<Order> orders = orderDao.findAll();
        List<OrderDTO> result = orders.stream().map(OrderDTO::fromOrder).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get order", response = OrderDTO.class)
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @GetMapping(value = "/order/{orderId}")
    public ResponseEntity<OrderDTO> order(@PathVariable long orderId) {
        Order order = orderDao.getOne(orderId);
        return ResponseEntity.ok(OrderDTO.fromOrder(order));
    }

    @ApiOperation(value = "Create new order", response = OrderDTO.class)
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @PostMapping(value = "/order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        Order entity = orderDao.save(new Order(order.getDescription()));
        return new ResponseEntity<>(OrderDTO.fromOrder(entity), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update order", response = OrderDTO.class)
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @PutMapping(value = "/order")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO order) {
        Order entity = orderDao.getOne(order.getId());
        entity.setDescription(order.getDescription());
        entity = orderDao.save(entity);
        return ResponseEntity.ok(OrderDTO.fromOrder(entity));
    }

    @ApiOperation(value = "Delete order", response = OrderDTO.class)
    @PreAuthorize("hasRole('role_admin')")
    @DeleteMapping(value = "/order/{orderId}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable long orderId) {
        Order order = orderDao.getOne(orderId);
        orderDao.delete(order);
        return ResponseEntity.ok(OrderDTO.fromOrder(order));
    }
}
