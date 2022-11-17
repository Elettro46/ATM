package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import code.Business_logic.Euro;

public class TestEuro {

    @ParameterizedTest
    @ValueSource(longs = { 1, 3, 5000, 123456789 })
    public void TestConstructorEuroNoCentsGetter(long e) {
        Euro aux = new Euro(e);
        assertEquals(e * 100, aux.getValore());
    }

    @ParameterizedTest
    @CsvSource({ "1,2", "3,5", "5000,23", "123456789,0" })
    public void TestConstructorEuroAndCentsGetter(long e, long c) {
        Euro aux = new Euro(e, c);
        assertEquals(e * 100 + c, aux.getValore());
    }

    @ParameterizedTest
    @CsvSource({ "1,2", "3,5", "5000,23", "123456789,0" })
    public void TestSumEuro(long startingEuro, long toSum) {
        Euro res = new Euro(startingEuro);
        Euro aux = new Euro(toSum);
        assertEquals(res.getValore() + aux.getValore(), res.somma(aux).getValore());
    }

    @ParameterizedTest
    @CsvSource({ "100,50", "1,1", "500,300", "99999,9" })
    public void TestSubtractEuro(long startingEuro, long toSub) {
        Euro res = new Euro(startingEuro);
        Euro aux = new Euro(toSub);
        assertEquals(res.getValore() - aux.getValore(), res.sottrai(aux).getValore());
    }

    @ParameterizedTest
    @CsvSource({ "100,100", "1,1", "500,500", "9999,9999" })
    public void TestEqualEuros(long e1, long e2) {
        Euro E1 = new Euro(e1);
        Euro E2 = new Euro(e2);
        assertTrue(E1.ugualeA(E2));
        assertTrue(E2.ugualeA(E1));
    }

    @ParameterizedTest
    @CsvSource({ "50,100", "1,2", "499,500", "9,9999" })
    public void TestLessThanEuro(long e1, long e2) {
        Euro E1 = new Euro(e1);
        Euro E2 = new Euro(e2);
        assertTrue(E1.minoreDi(E2));
        assertTrue(!E2.minoreDi(E1));
    }

    @ParameterizedTest
    @CsvSource({ "50,50.0 euro", "1,1.0 euro", "499,499.0 euro", "9,9.0 euro" })
    public void TestStampaEuro(long e, String stamp) {
        Euro E1 = new Euro(e);
        assertEquals(stamp, E1.stampa());
    }

    @ParameterizedTest
    @CsvSource({ "50,3,50.03 euro", "1,4,1.04 euro", "499,50,499.5 euro", "9,99,9.99 euro" })
    public void TestStampaEuroCent(long e, long c, String stamp) {
        Euro E1 = new Euro(e, c);
        assertEquals(stamp, E1.stampa());
    }
}