package beauj.day01.model;

import java.io.Serializable;

public class LineItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String item;
	private Integer quantity;

	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return (String.format("item: %s, quantity: %d", item, quantity));
	}

}
