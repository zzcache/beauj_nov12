package beauj.day02.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

	@Resource(lookup = "jdbc/northwind")
	private DataSource ds;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		Integer custId = Integer.parseInt(req.getParameter("custId"));

		try (Connection conn = ds.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"select * from customers where id = ?");
			ps.setInt(1, custId);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.setContentType("text/plain");
				try (PrintWriter pw = resp.getWriter()) {
					pw.println("Not found");
				}
				return;
			}

			JsonObjectBuilder objBuilder = Json.createObjectBuilder();
			JsonObject record = objBuilder.add("custId", rs.getInt("id"))
					.add("company", rs.getString("company"))
					.add("firstName", rs.getString("first_name"))
					.add("lastName", rs.getString("last_name"))
					.build();
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType(MediaType.APPLICATION_JSON);
			try (PrintWriter pw = resp.getWriter()) {
				pw.print(record.toString());
			}

		} catch (SQLException ex) {
			log(ex.getMessage());
			throw new IOException(ex);
		}
	}
	
}
