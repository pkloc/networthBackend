import org.json.JSONObject;

import static spark.Spark.*;

public class Main {

    // For testing:
    //   curl -d @request.json -H "Content-Type: application/json" http://localhost:4567/data
    //
    //     where request.json is file containing json data
    //

    public static void main(String[] args){

        ExchangeRate exchangeRate = new ExchangeRatesAPIExchangeRate();

        // To take care of CORS
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        post("/data", (request, response) ->
                {
                    response.type("application/json");

                    JSONObject jo = new JSONObject(request.body());
                    JSONHelper.updateTotalsInJSONObject(jo);

                    if(JSONHelper.shouldUpdateCurrency(jo)){
                        JSONHelper.updateCurrencyInJSONObject(jo, exchangeRate);
                    }
                    return jo;
                }
                );
    }
}
