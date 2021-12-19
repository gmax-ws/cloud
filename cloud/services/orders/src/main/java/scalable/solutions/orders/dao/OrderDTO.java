package scalable.solutions.orders.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderDTO {
    private long id;
    private String description;

    public static OrderDTO fromOrder(Order order) {
        return new OrderDTO(order.getId(), order.getDescription());
    }
}
