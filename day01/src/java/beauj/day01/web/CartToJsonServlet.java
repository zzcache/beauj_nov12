package beauj.day01.web;

import beauj.day01.model.Cart;
import beauj.day01.model.LineItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

@WebServlet(urlPatterns = { "/json" })
public class CartToJsonServlet extends HttpServlet {

	@Inject private Cart cart;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		HttpSession sess = req.getSession();

		//Outer object
		JsonObjectBuilder cartBuilder = Json.createObjectBuilder();
		cartBuilder.add("sessionId", sess.getId());
		cartBuilder.add("timestamp", (new Date()).toString());

		JsonArrayBuilder itemBuilder = Json.createArrayBuilder();
		for (LineItem li: cart.getCart()) {
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("item", li.getItem())
					.add("quantity", li.getQuantity());
			itemBuilder.add(builder);
		}
		cartBuilder.add("cart", itemBuilder.build());

		JsonObject jsonCart = cartBuilder.build();

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType(MediaType.APPLICATION_JSON);
		try (PrintWriter pw = resp.getWriter()) {
			pw.println(jsonCart.toString());
		}
	}
}
