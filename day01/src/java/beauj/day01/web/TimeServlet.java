package beauj.day01.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@WebServlet(urlPatterns = { "/time" })
public class TimeServlet extends HttpServlet {

	//Perform servlet initialization
	@Override
	public void init(ServletConfig config) 
			throws ServletException {
		System.out.println("In init method");
	}

	@Override
	public void destroy() {
		System.out.println("In Destroy method");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String time = (new Date()).toString();
		String ua = req.getHeader("User-Agent");

		//Set the status
		resp.setStatus(HttpServletResponse.SC_OK);

		//Set the content type / Media type / MIME
		resp.setContentType(MediaType.TEXT_HTML);

		//Send the actual data back/payload
		try (PrintWriter pw = resp.getWriter()) {
			pw.print("<h2>The current time is " + time + "</h2>");
			pw.print("<h2>" + ua + "</h2>");
		}
	}

	
}
