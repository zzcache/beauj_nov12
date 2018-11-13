package beauj.day02.web;

import beauj.day02.model.Customer;
import beauj.day02.model.Order;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@WebServlet(urlPatterns = { "/order" })
public class OrderServlet extends HttpServlet {

	@PersistenceContext
	private EntityManager em;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		Integer custId = Integer.parseInt(req.getParameter("custId"));

		TypedQuery<Order> query = em.createNamedQuery(
				"Order.findByCustomerId", Order.class);
		query.setParameter("cid", custId);

		/*
		Customer customer = em.find(Customer.class, custId);

		if (null == customer) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			try (PrintWriter pw = resp.getWriter()) {
				pw.print("Not found");
				return;
			}
		}

		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (Order o: customer.getOrders()) {
			builder.add(o.toJson());
		}
		*/

		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (Order o: query.getResultList()) {
			builder.add(o.toJson());
		}

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType(MediaType.APPLICATION_JSON);
		try (PrintWriter pw = resp.getWriter()) {
			pw.print(builder.build().toString());
		}
	}
	
}
