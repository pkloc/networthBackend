import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JSONHelperTests
{
    @Test
    void updateTotalsInJSONObject_givenExpectedJSONvalues_updatesTotalsInTheGivenJSONObject(){

        // Arrange
        JSONObject jo = new JSONObject();
        jo.put("totalAssets", "1234.56");
        jo.put("totalLiabilities", "234.56");
        jo.put("netWorth", "1234.56");

        JSONArray items = new JSONArray();

        JSONObject item1 = new JSONObject();
        item1.put("type", "asset");
        item1.put("amount", "100.14");
        JSONObject item2 = new JSONObject();
        item2.put("type", "asset");
        item2.put("amount", "40000.50");
        JSONObject item3 = new JSONObject();
        item3.put("type", "liability");
        item3.put("amount", "200.34");
        JSONObject item4 = new JSONObject();
        item4.put("type", "liability");
        item4.put("amount", "4000.50");
        JSONObject item5 = new JSONObject();
        item5.put("type", "liability");
        item5.put("amount", "");

        items.put(item1);
        items.put(item2);
        items.put(item3);
        items.put(item4);
        items.put(item5);

        jo.put("items", items);

        // Act
        JSONHelper.updateTotalsInJSONObject(jo);

        // Assert
        assertEquals("40100.64", jo.getString("totalAssets"));
        assertEquals("4200.84", jo.getString("totalLiabilities"));
        assertEquals("35899.80", jo.getString("netWorth"));
    }

    @Test
    void updateAmountsWithExchangeRate(){
        // Arrange
        JSONObject jo = new JSONObject();
        jo.put("currentCurrency", "CAD");
        jo.put("newCurrency", "USD");
        jo.put("totalAssets", "1234.56");
        jo.put("totalLiabilities", "234.56");
        jo.put("netWorth", "1234.56");

        JSONArray items = new JSONArray();

        JSONObject item1 = new JSONObject();
        item1.put("amount", "100.14");
        JSONObject item2 = new JSONObject();
        item2.put("amount", "40000.50");

        items.put(item1);
        items.put(item2);

        jo.put("items", items);

        // Act
        //JSONHelper.updateAmountsWithExchangeRate(rateMultiplier, jo);

        // Assert

    }

    @Test
    void shouldUpdateCurrency_currentCurrencyAndNewCurrencyTheSame(){
        // Arrange
        JSONObject jo = new JSONObject();
        jo.put("currentCurrency", "CAD");
        jo.put("newCurrency", "CAD");

        // Act
        boolean applyRate = JSONHelper.shouldUpdateCurrency(jo);

        // Assert
        assertFalse(applyRate);
    }

    @Test
    void shouldUpdateCurrency_currentCurrencyAndNewCurrencyDifferent(){
        // Arrange
        JSONObject jo = new JSONObject();
        jo.put("currentCurrency", "CAD");
        jo.put("newCurrency", "USD");

        // Act
        boolean applyRate = JSONHelper.shouldUpdateCurrency(jo);

        // Assert
        assertTrue(applyRate);
    }

    @Test
    void updateCurrencyInJSONObject(){

    }
}
