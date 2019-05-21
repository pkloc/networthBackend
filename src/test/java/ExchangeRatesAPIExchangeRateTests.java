import org.junit.jupiter.api.Test;

class ExchangeRatesAPIExchangeRateTests {

    @Test
    void getExchangeRate(){
        ExchangeRate exchangeRate = new ExchangeRatesAPIExchangeRate();

        System.out.println(exchangeRate.getExchangeRate("CAD", "USD"));
    }
}
