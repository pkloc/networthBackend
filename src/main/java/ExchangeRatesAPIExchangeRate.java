import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeRatesAPIExchangeRate implements ExchangeRate {

    private WebTarget target;

    protected static ClientConfig createClientConfig() {
        ClientConfig config = new ClientConfig();
        config.register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));
        return config;
    }

    ExchangeRatesAPIExchangeRate(){

        Client client = ClientBuilder.newClient(createClientConfig());
        String baseURI = "https://api.exchangeratesapi.io/latest";
        target = client.target(baseURI);
    }

    public double getExchangeRate(String base, String desired){

        JSONObject jo = new JSONObject(
                target.queryParam("base", base)
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .get(String.class));

        double desiredRate = jo.getJSONObject("rates").getDouble(desired);
        System.out.println("Base: " + base + ", Desired: " + desired + ", Desired rate: " + desiredRate);

        return desiredRate;
    }
}
