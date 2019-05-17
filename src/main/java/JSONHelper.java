import org.json.JSONArray;
import org.json.JSONObject;

public interface JSONHelper {

    static void updateTotalsInJSONObject(JSONObject jsonObject){
        JSONArray items = jsonObject.getJSONArray("items");

        double totalAssets = 0.0;
        double totalLiabilities = 0.0;

        for(int i = 0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);
            if(item.getString("type").equalsIgnoreCase("asset")){
                totalAssets += item.optDouble("amount", 0.0f);
            }else{
                totalLiabilities += item.optDouble("amount", 0.0f);
            }
        }

        jsonObject.put("totalAssets", String.format("%.2f", totalAssets));
        jsonObject.put("totalLiabilities", String.format("%.2f", totalLiabilities));
        jsonObject.put("netWorth", String.format("%.2f", totalAssets - totalLiabilities));
    }

    static void updateCurrencyInJSONObject(JSONObject jsonObject, ExchangeRate exchangeRate){

    }

    static boolean shouldUpdateCurrency(JSONObject jsonObject){
        return !jsonObject.getString("currentCurrency").equalsIgnoreCase(jsonObject.getString("newCurrency"));
    }
}
