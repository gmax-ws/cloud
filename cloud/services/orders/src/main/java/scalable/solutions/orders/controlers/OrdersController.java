package scalable.solutions.orders.controlers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import scalable.solutions.orders.dao.OrderDTO;
import scalable.solutions.orders.service.OrderService;

import java.util.Collection;

@RestController
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Get all orders")
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @GetMapping(value = "/orders")
    public ResponseEntity<Collection<OrderDTO>> orders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get order", response = OrderDTO.class)
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @GetMapping(value = "/order/{orderId}")
    public ResponseEntity<OrderDTO> order(@PathVariable long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @ApiOperation(value = "Create new order", response = OrderDTO.class)
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @PostMapping(value = "/order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update order", response = OrderDTO.class)
    @PreAuthorize("hasAnyRole('role_admin', 'role_user', 'role_developer')")
    @PutMapping(value = "/order")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO order) {
        return ResponseEntity.ok(orderService.updateOrder(order));
    }

    @ApiOperation(value = "Delete order", response = OrderDTO.class)
    @PreAuthorize("hasRole('role_admin')")
    @DeleteMapping(value = "/order/{orderId}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable long orderId) {
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }
}
