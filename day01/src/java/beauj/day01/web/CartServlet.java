package beauj.day01.web;

import beauj.day01.model.Cart;
import beauj.day01.model.LineItem;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

// GET /day01/cart
@WebServlet(urlPatterns = { "/cart" })
public class CartServlet extends HttpServlet {

	//Managed object
	@Inject private Cart cart;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		HttpSession sess = req.getSession();
		sess.setMaxInactiveInterval(60 * 10);

		System.out.println(">>> cart: " + cart.getClass().getName());

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType(MediaType.TEXT_HTML);

		try (PrintWriter pw = resp.getWriter()) {

			if (cart.getCart().size() <= 0) {
				pw.println("<h2>You have nothing in your cart</h2>");
				return;
			}

			pw.println("<h2>My Shopping Cart</h2>");
			pw.println("<ul>");
			for (LineItem li: cart.getCart())
				pw.printf("<li>Item: %s, quantity: %d", 
						li.getItem(), li.getQuantity());
			pw.println("</ul>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String item = req.getParameter("item");
		Integer quantity = Integer.parseInt(req.getParameter("quantity"));

		LineItem lineItem = new LineItem();
		lineItem.setItem(item);
		lineItem.setQuantity(quantity);

		cart.addItem(lineItem);

		System.out.println(">>> contents: " + cart.getCart());
		
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		resp.setContentType(MediaType.TEXT_HTML);

		try (PrintWriter pw = resp.getWriter()) {
			pw.printf("<div>You have added %d of %s into your cart</div>", 
					quantity, item);
			pw.println("Thank you");
		}
	}
	
}
