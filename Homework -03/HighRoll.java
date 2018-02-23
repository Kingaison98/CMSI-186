/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  MainProgLoopDemo.java
 *  Purpose       :  Demonstrates the use of input from a command line for use with Yahtzee
 *  Author        :  B.J. Johnson
 *  Date          :  2017-02-14
 *  Description   :
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-14  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HighRoll{
   static boolean GameState = true;
   static DiceSet GameSet = new DiceSet(5, 6);
   static int HighScore;
   public static void main( String args[] ) {
      System.out.println("\nWelcome to High Roller!!\n" );
      System.out.println("Type a number 1 - 6!" );

     // This line uses the two classes to assemble an "input stream" for the user to type
     // text into the program
      BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );
      while( GameState ) {
         System.out.println("\n\n1. Roll all dice. \n2. Roll a single die.");
         System.out.println("3. Calculate score for this set. \n4. Save this score as high score");
         System.out.println("5. Display the high score. \n6. Quit (you can also type q to quit)\n\n");
         System.out.print( ">>" );
         String inputLine = null;
         try {
            inputLine = input.readLine();
            if( 0 == inputLine.length() ) {
               System.out.println("enter some text!:");
            }
            System.out.println( "You typed: " + inputLine + "\n\n");
            switch(inputLine) {
              case "1":
                  GameSet.roll();
                  System.out.println("You rolled a " + GameSet.sum() + "!");
                  break;
              case "2":
                  System.out.println("Choose a die to roll!");
                  for(int i = 0; i < DiceSet.AccessArrayLength(GameSet); i++) {
                    System.out.println(i + ". [" + GameSet.getIndividual(i) + "]");
                  }
                  System.out.print("\n>>");
                  inputLine = input.readLine();
                  try{
                    int parsed = Integer.parseInt(inputLine);
                    GameSet.rollIndividual(parsed);
                    System.out.println("\n Rolled a " + GameSet.getIndividual(parsed) + ".");
                    break;
                  }
                  catch(NumberFormatException e) {
                    System.out.println("Please print a number.");
                    break;
                  }
              case "3":
                  System.out.println("Current value of set is " + GameSet.sum() + "!");
                  break;
              case "4":
                  System.out.println("Saving " + GameSet.sum() + " as the high score.");
                  HighScore = GameSet.sum();
                  break;
              case "5":
                  System.out.println("The current high score is " + HighScore + ".");
                  break;
              case "6":
              case "Q":
              case "q":
                System.out.println("Quitting! Bye!\n\n");
                GameState = false;
                break;
              default:
                System.out.println("Please input a number 1 - 6 or Q.");
                break;
            }
          }
         catch( IOException ioe ) {
            System.out.println( "Caught IOException" );
         }
      }
   }
}
