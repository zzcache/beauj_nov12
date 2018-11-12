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

@WebServlet(urlPatterns = { "/checkout" })
public class CheckoutServlet extends HttpServlet {

	@Inject private Cart cart;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		//Get session object
		HttpSession sess = req.getSession();
		//Destroy the session
		sess.invalidate();

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType(MediaType.TEXT_HTML);

		try (PrintWriter pw = resp.getWriter()) {

			pw.println("<h2>Thank you for shopping with us</h2>");
			pw.println("<h2>Your final items</h2>");
			pw.println("<ul>");
			for (LineItem li: cart.getCart())
				pw.printf("<li>Item: %s, quantity: %d", 
						li.getItem(), li.getQuantity());
			pw.println("</ul>");

		}
	}

	
	
}
