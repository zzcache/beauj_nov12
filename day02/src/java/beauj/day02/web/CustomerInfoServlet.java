package beauj.day02.web;

import beauj.day02.model.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

@WebServlet(urlPatterns = "/information")
public class CustomerInfoServlet extends HttpServlet {

	private static final String QUERY = 
		"select c from Customer c where c.jobTitle like :jt";

	@PersistenceContext
	private EntityManager em;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String title = req.getParameter("title");

		//Ad-hoc query
		TypedQuery<Customer> query = em.createQuery(QUERY, Customer.class);
		query.setParameter("jt", title);

		List<Customer> result = query.getResultList();

		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (Customer c: result)
			builder.add(c.toJson());

		resp.setStatus(200);
		resp.setContentType("application/json");
		try (PrintWriter pw = resp.getWriter()) {
			pw.print(builder.build().toString());
		}
	}

}
