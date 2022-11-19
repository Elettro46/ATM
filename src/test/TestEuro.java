package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import code.Business_logic.Euro;
import code.Database.BankDatabase;
import org.junit.jupiter.params.provider.ValueSource;

public class TestEuro {
    private BankDatabase bd;

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

    @BeforeEach
    public void SetUp(){
        bd = new BankDatabase();
    }

    @AfterEach
    public void TearDown(){
        bd = null;
    }

    @ParameterizedTest
    @CsvSource({"12345,54321", "98765,56789"})
    public void BankDatabase_authenticateUser_CorrectUsersAreAuthenticated(int userAccountNumber, int userPIN){
        assertTrue(bd.authenticateUser(userAccountNumber, userPIN));
    }

    @ParameterizedTest
    @CsvSource({"69,42", "45453466,0"})
    public void BankDatabase_authenticateUser_IncorrectUsersAreNotAuthenticated(int userAccountNumber, int userPIN){
        assertTrue(!bd.authenticateUser(userAccountNumber, userPIN));
    }

    @ParameterizedTest
    @CsvSource({"12345, 50", "12345, 69", "12345, 300", "98765, 60", "98765,42", "98765, 420"})
    public void BankDatabase_credit_AccreditedCorrectAmount(int userAccountNumber, int amount){
        Euro toSum = new Euro(amount);
        var prevBalance = bd.getTotalBalance(userAccountNumber).getValore();
        bd.credit(userAccountNumber, toSum);
        assertEquals(prevBalance+toSum.getValore(), bd.getTotalBalance(userAccountNumber).getValore());
    }

    @ParameterizedTest
    @CsvSource({"12345, 3", "12345, 69", "12345, 43", "98765, 60", "98765,42", "98765, 420"})
    public void BankDatabase_debit_SubtractsCorrectAmount(int userAccountNumber, int amount){
        Euro toSub = new Euro(amount);
        var prevTotalBalance = bd.getTotalBalance(userAccountNumber).getValore();
        var prevAvailableBalance = bd.getAvailableBalance(userAccountNumber).getValore();
        bd.debit(userAccountNumber, toSub);
        assertAll(
            () -> assertEquals(prevTotalBalance-toSub.getValore(), bd.getTotalBalance(userAccountNumber).getValore()),
            () -> assertEquals(prevAvailableBalance-toSub.getValore(), bd.getAvailableBalance(userAccountNumber).getValore())
        );
    }
}