package beauj.day02.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name = "Order.findByCustomerId",
		query = "select o from Order o join o.customer c where c.id = :cid"),
})

@Entity
@Table(name="orders")
public class Order {

	@Id
	private Integer id;

	@Column(name="order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@Column(name="ship_name")
	private String shipName;

	@ManyToOne
	@JoinColumn(name = "customer_id", 
		referencedColumnName = "id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "status_id",
		referencedColumnName = "id")
	private OrderStatus status;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public JsonObject toJson() {
		return (
			Json.createObjectBuilder()
				.add("id", id)
				.add("orderDate", orderDate.toString())
				.add("shipName", shipName)
				.add("status", status.toString())
				.build()
		);

	}

	
}
