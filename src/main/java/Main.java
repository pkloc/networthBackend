import org.json.JSONObject;

import static spark.Spark.post;

public class Main {

    // For testing:
    //   curl -d @request.json -H "Content-Type: application/json" http://localhost:4567/data
    //
    //     where request.json is file containing json data
    //

    public static void main(String[] args){
        post("/data", (req, res) ->
                {
                    System.out.println(req.body());
                    JSONObject jo = new JSONObject(req.body());
                    JSONHelper.updateTotalsInJSONObject(jo);

                    return "Hello World";
                }
                );
    }
}
