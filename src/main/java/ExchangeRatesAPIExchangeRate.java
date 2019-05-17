import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ExchangeRatesAPIExchangeRate implements ExchangeRate {

    private Client client;
    private WebTarget target;

    private static String baseURI = "https://api.exchangeratesapi.io/latest";

    ExchangeRatesAPIExchangeRate(){
        client  = ClientBuilder.newClient();
        target = client.target(baseURI);
    }

    public double getExchangeRate(Currency base, Currency desired){
        target.queryParam("base", base);
        JSONObject jo = new JSONObject(target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class));
        double desiredRate = jo.getJSONObject("rates").getDouble(desired.toString());
        System.out.println("Base: " + base.toString() + ", Desired: " + desired.toString() + ", Desired rate: " + desiredRate);

        return 1/desiredRate;
    }
}
