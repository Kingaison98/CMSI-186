/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  DiceSet.java
 *  Purpose       :  Provides a class describing a set of dice
 *  Author        :  B.J. Johnson
 *  Date          :  2017-02-09
 *  Description   :  This class provides everything needed (pretty much) to describe a set of dice.  The
 *                   idea here is to have an implementing class that uses the Die.java class.  Includes
 *                   the following:
 *                   public DiceSet( int k, int n );                  // Constructor for a set of k dice each with n-sides
 *                   public int sum();                                // Returns the present sum of this set of dice
 *                   public void roll();                              // Randomly rolls all of the dice in this set
 *                   public void rollIndividual( int i );             // Randomly rolls only the ith die in this set
 *                   public int getIndividual( int i );               // Gets the value of the ith die in this set
 *                   public String toString();                        // Returns a stringy representation of this set of dice
 *                   public static String toString( DiceSet ds );     // Classwide version of the preceding instance method
 *                   public boolean isIdentical( DiceSet ds );        // Returns true iff this set is identical to the set ds
 *                   public static void main( String[] args );        // The built-in test program for this class
 *
 *  Notes         :  Stolen from Dr. Dorin pretty much verbatim, then modified to show some interesting
 *                   things about Java, and to add this header block and some JavaDoc comments.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the number of sides or pips is out of range
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-09  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
public class DiceSet {

  /**
   * private instance data
   */
   private int count;
   private int sides;
   private Die[] setArray;

   // public constructor:
  /**
   * constructor
   * @param  count int value containing total dice count
   * @param  sides int value containing the number of pips on each die
   * @throws IllegalArgumentException if one or both arguments don't make sense
   * @note   parameters are checked for validity; invalid values throw "IllegalArgumentException"
   */
   public DiceSet( int count, int sides ) {
      if(count < 0){
        throw new IllegalArgumentException("At least 1 die in the set.");
      }
      if(sides < 4){
        throw new IllegalArgumentException("Too few sides. 4 minimum.");
      }
      setArray = new Die[count];
      for(int i = 0; i < setArray.length; i++) {
        setArray[i] = new Die(sides);
      }
   }

  /**
   * Method that allows you to change any individual die within a dice set, class-wide
   * @param set DiceSet value name of the dice set being changed
   * @param index int value containing the index within the set being changed
   * @param sides int value containing the number of sides the new die has
   */
   public static Die ChangeDie(DiceSet set, int index, int sides) {
     set.setArray[index] = new Die(sides);
     return set.setArray[index];
   }
   /**
    * Method that allows you to change any individual die within a dice set
    * @param set DiceSet value name of the dice set being changed
    * @param index int value containing the index within the set being changed
    * @param sides int value containing the number of sides the new die has
    */
   public Die ChangeDie(int index, int sides) {
     setArray[index] = new Die(sides);
     return setArray[index];
   }

  /**
   * @return the sum of all the dice values in the set
   */
   public int sum() {
     int setSum = 0;
     for(int i = 0; i < setArray.length; i++) {
       setSum += setArray[i].getValue();
     }
     return setSum;
   }

  /**
   * Randomly rolls all of the dice in this set
   *  NOTE: you will need to use one of the "toString()" methosetArray to obtain
   *  the values of the dice in the set
   */
   public void roll() {
     for(int i = 0; i < setArray.length; i++) {
       setArray[i].roll();
     }
   }

  /**
   * Randomly rolls a single die of the dice in this set indexed by 'dieIndex'
   * @param  dieIndex int of which die to roll
   * @return the integer value of the newly rolled die
   * @throws IllegalArgumentException if the index is out of range
   */
   public int rollIndividual( int dieIndex ) {
      if(dieIndex > setArray.length){
        throw new IllegalArgumentException("Index out of range of dice set.");
      }
      setArray[dieIndex].roll();
      return setArray[dieIndex].getValue();
   }

  /**
   * Gets the value of the die in this set indexed by 'dieIndex'
   * @param  dieIndex int of which die to roll
   * @throws IllegalArgumentException if the index is out of range
   */
   public int getIndividual( int dieIndex ) {
     if(dieIndex > setArray.length){
       throw new IllegalArgumentException("Index out of range of dice set.");
     }
     return setArray[dieIndex].getValue();
   }

  /**
   * @return Public Instance method that returns a String representation of the DiceSet instance
   */
   public String toString() {
      StringBuffer setName = new StringBuffer();
      for(int i = 0; i < setArray.length; i++){
        setName.append(setArray[i].toString());
      }
      String result = setName.toString();
      return result;
   }

   public String SidesToString() {
      StringBuffer setName = new StringBuffer();
      for(int i = 0; i < setArray.length; i++){
        setName.append(setArray[i].SidesToString());
      }
      String result = setName.toString();
      return result;
   }

  /**
   * @return Class-wide version of the preceding instance method
   */
   public static String toString( DiceSet set ) {
      return set.toString();
   }

  /**
   * @return  tru iff this set is identical to the set passed as an argument
   */
   public boolean isIdentical( DiceSet set) {
     /*for(int i = 0; i < setArray.length; i ++){
       if(!setArray[i].SidesToString().equals(set.setArray[i].SidesToString())){
         return false;
       }
       return true;
     }*/
     if(SidesToString().equals(set.SidesToString())){
       if(sum() == set.sum()){
         return true;
       }
     }
     return false;
   }

  /**
   * Returns the number of dice in a set, classwide
   * @param set the name of DiceSet in question
   * @return the length of the DiceSet setArray.
   */
   public static int AccessArrayLength(DiceSet set ) {
     return set.setArray.length;
   }


  /**
   * Returns the string representation of a set, Classwide
   * @param set the name of the DiceSet in question
   * @return the values of the dice in the set as a string
   */
   public static String AccessArrayString(DiceSet set ) {
     return set.toString();
   }
  /**
   * Allows access to any one die in any set, to reference dice outside of DiceSet.Java
   * @param set the name of the DiceSet in question
   * @param index the index of the die being accessed
   * @return the die specified by set and index
   */
   public static Die AccessDie(DiceSet set, int index ) {
     return set.setArray[index];
   }
  /**
   * A little test main to check things out
   */
   public static void main( String[] args ) {
      /*DiceSet TestSet = new DiceSet(10, 5);
      System.out.println(TestSet.toString());
      DiceSet AnotherSet = new DiceSet(10, 5);
      System.out.println(TestSet.toString());
      System.out.println(AnotherSet.toString());
      System.out.println(TestSet.isIdentical(AnotherSet));
      //TestSet.ChangeDie(0, 4);
      ChangeDie(TestSet, 2, 10);
      System.out.println(TestSet.toString());
      System.out.println(TestSet.isIdentical(AnotherSet));*/

   }

}
