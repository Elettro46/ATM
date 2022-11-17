package code.Business_logic;
// CashDispenser.java

// Represents the cash dispenser of the ATM

public class CashDispenser {
   // the default initial number of bills in the cash dispenser
   private final static Euro INITIAL_COUNT = new Euro(500);
   private Euro count; // number of $20 bills remaining

   // no-argument CashDispenser constructor initializes count to default
   public CashDispenser() {
      count = new Euro(INITIAL_COUNT.getValore()); // set count attribute to default
   } // end CashDispenser constructor

   // simulates dispensing of specified amount of cash
   public void dispenseCash(Euro amount) {
      Euro billsRequired = new Euro(amount.getValore() / 20); // number of $20 bills required
      count.sottrai(billsRequired); // update the count of bills
   } // end method dispenseCash

   // indicates whether cash dispenser can dispense desired amount
   public boolean isSufficientCashAvailable(Euro amount) {
      Euro billsRequired = new Euro(amount.getValore() / 20); // number of $20 bills required

      if (count.getValore() >= billsRequired.getValore())
         return true; // enough bills available
      else
         return false; // not enough bills available
   } // end method isSufficientCashAvailable
} // end class CashDispenser

/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and *
 * Pearson Education, Inc. All Rights Reserved. *
 * *
 * DISCLAIMER: The authors and publisher of this book have used their *
 * best efforts in preparing the book. These efforts include the *
 * development, research, and testing of the theories and programs *
 * to determine their effectiveness. The authors and publisher make *
 * no warranty of any kind, expressed or implied, with regard to these *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or *
 * consequential damages in connection with, or arising out of, the *
 * furnishing, performance, or use of these programs. *
 *************************************************************************/