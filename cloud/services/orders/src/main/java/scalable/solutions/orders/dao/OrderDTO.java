package scalable.solutions.orders.dao;

public class OrderDTO {
	private long id;
	private String description;

	public OrderDTO(long id, String description) {
		this.id = id;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static OrderDTO fromOrder(Order order) {
		return new OrderDTO(order.getId(), order.getDescription());
	}
}
