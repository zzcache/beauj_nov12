package beauj.day03.web;

import beauj.day03.business.CustomerBean;
import beauj.day03.business.CustomerException;
import beauj.day03.model.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

@WebServlet(urlPatterns = { "/customer" })
public class CustomerServlet extends HttpServlet {

	@EJB private CustomerBean customerBean;

	//Inject in the transaction resource
	@Resource UserTransaction ut;

	private Customer createCustomer(HttpServletRequest req) {
		Customer cust = new Customer();
		cust.setCustomerId(Integer.parseInt(req.getParameter("customerId")));
		cust.setName(req.getParameter("name"));
		cust.setAddressline1(req.getParameter("addressline1"));
		cust.setAddressline2(req.getParameter("addressline2"));
		cust.setCity(req.getParameter("city"));
		cust.setState(req.getParameter("state"));
		cust.setZip(req.getParameter("zip"));
		cust.setPhone(req.getParameter("phone"));
		cust.setFax(req.getParameter("fax"));
		cust.setEmail(req.getParameter("email"));
		cust.setCreditLimit(Integer.parseInt(req.getParameter("creditLimit")));
		cust.setDiscountCode(
				Customer.DiscountCode.valueOf(req.getParameter("discountCode")));
		return (cust);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		Customer newCustomer = createCustomer(req);

		//Tx will start
		try {
			ut.begin();
			try {
				newCustomer = customerBean.createCustomer(newCustomer);
			} catch (CustomerException ex) {
				ut.rollback();
				resp.setStatus(400);
				resp.setContentType("text/plain");
				try (PrintWriter pw = resp.getWriter()) {
					pw.println("ERROR " + ex.getMessage());
					return;
				}
			}
			//tx will commit/rollback
			ut.commit();
		} catch (Exception ex) {
			log(ex.getMessage());
			return;
		}

		resp.setStatus(HttpServletResponse.SC_CREATED);
		resp.setContentType("text/plain");
		try (PrintWriter pw = resp.getWriter()) {
			pw.println(newCustomer.toString());
		}
	}
	
}
