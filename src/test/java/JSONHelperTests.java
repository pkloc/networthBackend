import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        // Arrange
        String currentCurrency = "CAD";
        String desiredCurrency = "USD";
        JSONObject jo = new JSONObject();
        jo.put("currentCurrency", currentCurrency);
        jo.put("newCurrency", desiredCurrency);
        jo.put("totalAssets", "100.50");
        jo.put("totalLiabilities", "50.00");
        jo.put("netWorth", "50.50");

        JSONArray items = new JSONArray();
        JSONObject item1 = new JSONObject();
        item1.put("amount", "200.00");
        JSONObject item2 = new JSONObject();
        item2.put("amount", "1000.50");
        JSONObject item3 = new JSONObject();
        item3.put("amount", "");
        JSONObject item4 = new JSONObject();
        item4.put("amount", "0.0");
        items.put(item1);
        items.put(item2);
        items.put(item3);
        items.put(item4);

        jo.put("items", items);

        ExchangeRate exchangeRate = mock(ExchangeRate.class);
        when(exchangeRate.getExchangeRate(currentCurrency, desiredCurrency)).thenReturn(1.5);

        // Act
        JSONHelper.updateCurrencyInJSONObject(jo, exchangeRate);

        // Assert
        assertEquals("USD", jo.getString("currentCurrency"));
        assertEquals("150.75", jo.getString("totalAssets"));
        assertEquals("75.00", jo.getString("totalLiabilities"));
        assertEquals("75.75", jo.getString("netWorth"));
        JSONArray newItems = jo.getJSONArray("items");

        assertEquals("300.00", newItems.getJSONObject(0).getString("amount"));
        assertEquals("1500.75", newItems.getJSONObject(1).getString("amount"));
        assertEquals("", newItems.getJSONObject(2).getString("amount"));
        assertEquals("0.00", newItems.getJSONObject(3).getString("amount"));
    }
}
