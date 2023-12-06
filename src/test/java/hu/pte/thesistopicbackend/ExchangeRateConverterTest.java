package hu.pte.thesistopicbackend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import hu.pte.thesistopicbackend.service.ExchangeRateConverter;

public class ExchangeRateConverterTest {

    @Test
    public void testExchange() {
        ExchangeRateConverter converter = new ExchangeRateConverter();

        // Tesztelendő adatok
        String fromCurrency = "GBP";
        String toCurrency = "JPY";
        int amount = 25;

        // Tesztelés
        int result = converter.exchange(fromCurrency, toCurrency, amount);

        System.out.print(result);

        assertNotNull(result);
    }
}
