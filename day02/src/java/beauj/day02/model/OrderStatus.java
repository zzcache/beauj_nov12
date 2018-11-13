package beauj.day02.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders_status")
public class OrderStatus {

	public enum Status { New, Invoiced, Shipped, Closed }

	@Id
	private Integer id;

	@Column(name = "status_name")
	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToMany(mappedBy = "status")
	private List<Order> orders;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}


	
}
