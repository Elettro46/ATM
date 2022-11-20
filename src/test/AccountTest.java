package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import code.Business_logic.Euro;
import code.Database.Account;

public class AccountTest {

    private Account acc;

	@BeforeEach 
	public void SetUp(){
		acc = new Account( 12345, 54321, new Euro(1000.0), new Euro(1200.0));
	}

	@AfterEach 
	public void TearDown() {
		acc=null;
	}

    //single tests
    @Test
    public void testGetAccountNumber() {
		assertEquals(acc.getAccountNumber(), 12345);
	}

	@Test
		public void TestValidatePIN() {
			assertTrue(acc.validatePIN(54321));
		}
    
    @Test
	public void testGetAvailableBalance() {
		var aux = new Euro(1000);
		assertTrue(acc.getAvailableBalance().ugualeA(aux));
	}

    @Test
	public void testGetTotalBalance() {
		var aux = new Euro(1200);
		assertTrue(acc.getTotalBalance().ugualeA(aux));
	}

    // Constructor Tests
    @ParameterizedTest
    @CsvSource({ "98765,56789,15000,20000", "11111,11111,20,500", "22222,22222,0.5,2000.5", "33333,33333,400.05,400.05"  })
    public void ConstructorTest(int AccNum, int PIN, double available, double total){
        Account test_acc = new Account(AccNum, PIN, new Euro(available), new Euro(total));
        var aux_avail = new Euro(available);
		var aux_tot = new Euro(total);
        Assertions.assertAll(
            ()->assertEquals(test_acc.getAccountNumber(), AccNum),
            ()->assertTrue(test_acc.validatePIN(PIN)),
			()->assertTrue(test_acc.getAvailableBalance().ugualeA(aux_avail)),
			()->assertTrue(test_acc.getTotalBalance().ugualeA(aux_tot))
		);
    }

    //Balance Operations Tests
    @ParameterizedTest
    @CsvSource({"100,1300", 
    "500,1700", 
    "0.01,1200.01", "250.99,1450.99"})
	public void testCredit(double add, double total) { 
        acc.credit(new Euro(add));
		var aux = new Euro(total);
        assertTrue(acc.getTotalBalance().ugualeA(aux));
	}

    @ParameterizedTest
    @CsvSource({"100,900,1100", "500,500,700", "0.01,999.99,1199.99", "250.99,749.01,949.01"})
	public void testDebit(double sub, double available, double total) {
		//Account acc = new Account( 11111, 11111, new Euro(1000.0), new Euro(1200.0));
        acc.debit(new Euro(sub));
		var aux_avail = new Euro(available);
		var aux_tot = new Euro(total);
        Assertions.assertAll(
			()->assertTrue(acc.getAvailableBalance().ugualeA(aux_avail)),
			()->assertTrue(acc.getTotalBalance().ugualeA(aux_tot))
		);
	}

}
