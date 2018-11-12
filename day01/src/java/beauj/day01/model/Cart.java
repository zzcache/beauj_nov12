package beauj.day01.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<LineItem> cart = new LinkedList<>();

	@PostConstruct
	private void init() {
		System.out.println(">> session is created");
	}

	@PreDestroy
	private void destroy() {
		System.out.println(">> session is about to be destroyed");
	}

	public List<LineItem> getCart() {
		return cart;
	}
	public void setCart(List<LineItem> cart) {
		this.cart = cart;
	}

	public void addItem(LineItem li) {
		cart.add(li);
	}
	
}
