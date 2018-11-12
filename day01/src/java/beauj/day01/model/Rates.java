package beauj.day01.model;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;

@ApplicationScoped
public class Rates {

	private JsonObject rates = null;

	public JsonObject getRates() {
		return rates;
	}
	public void setRates(JsonObject rates) {
		this.rates = rates;
	}

	
}
