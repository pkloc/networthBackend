import org.junit.jupiter.api.Test;

public class ExchangeRatesAPIExchangeRateTests {

    @Test
    void getExchangeRate(){
        ExchangeRate exchangeRate = new ExchangeRatesAPIExchangeRate();

        System.out.println(exchangeRate.getExchangeRate(Currency.USD, Currency.CAD));
    }
}
