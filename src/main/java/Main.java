import static spark.Spark.get;
import static spark.Spark.post;

public class Main {

    public static void main(String[] args){
        post("/data", (req, res) ->
                {
                    System.out.println(req.body());
                    //JSONObject jo = new JSONObject(req.body());
                    //JSONArray assets = jo.getJSONArray("assets");

                    return "Hello World";
                }
                );

        get("/hello/:name", (req,res)-> "Hello, "+ req.params(":name"));
    }

}
