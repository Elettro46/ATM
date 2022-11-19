package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import code.Business_logic.Euro;
import code.Database.Account;
import code.Database.BankDatabase;

public class TestEuro {
    private BankDatabase bd;
    @Test
    public void testEuro() {
        assertTrue(true);
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