package beauj.day01.web;

import beauj.day01.model.Rates;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@WebServlet(urlPatterns = { "/rates" })
public class FXServlet extends HttpServlet {

	private static final String APIKEY = "__YOUR_FIXER.IO_API_KEY_HERE__";

	@Inject private Rates rates;

	//Threadsafe
	private Client client;

	@Override
	public void init() throws ServletException {
		System.out.println("Initializing Client");
		client = ClientBuilder.newClient();
	}

	@Override
	public void destroy() {
		System.out.println("Closing Client");
		client.close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String fromCache = req.getParameter("cached");
		boolean getFromCache = Objects.isNull(fromCache);
		JsonObject result = null;

		if (getFromCache) {
			System.out.println("Getting from cache");
			result = rates.getRates();
		}

		if (result == null) {

			System.out.println("Making call to target");

			//Create a target to your url
			WebTarget target = client.target("http://data.fixer.io/api/latest");
			//Configure the target viz. set one or more query params
			target = target.queryParam("access_key", APIKEY);
			//target = target.queryParam("base", "SGD");

			//Create an invocation specifying the response media type
			Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);

			//Decide on the method you are going to use for the invocation
			//specifying the object that you are expecting
			//this should match the request() above
			result = builder.get(JsonObject.class);
			rates.setRates(result);
		}

		//After the call, how you handle it will depend on the result
		//Read the doc for your API
		System.out.println(">> from source: " + result);

		if (result.getBoolean("success")) {
			resp.setStatus(200);
			resp.setContentType("text/html");
			JsonObject rates = result.getJsonObject("rates");
			try (PrintWriter pw = resp.getWriter()) {
				pw.println("<h1>Rates</h1>");
				pw.println("<ol>");
				for (String key: rates.keySet()) {
					pw.printf("<li>Currency: %s = %f", key, rates.getJsonNumber(key).doubleValue());
				}
				pw.println("</ol>");
			}

		} else {
			resp.setStatus(400);
			resp.setContentType("text/plain");
			try (PrintWriter pw = resp.getWriter()) {
				pw.println("Something has gone wrong. Please check with administrator");
			}
		}
	}
	
	
}
