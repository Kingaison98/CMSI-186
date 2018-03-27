/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Ball.java
 *  Purpose       :  Provides a class defining methods for the SoccerSim class
 *  @author       :  Aison King
 *  Date written  :  2018-03-15
 *  Description   :  This class provides a bunch of methods which may be useful for the SoccerSim class
 *                   for Homework 5, part 1.  Includes the following:
 *
 *  Notes         :  
 *  Warnings      :  None
 *  Exceptions    :  
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2018-03-01  Aison King    Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

public class SoccerSim {

  public SoccerSim() {
    super();
  }

  public void handleArgs(String[] args, Field field) {
    System.out.println("\n     Welcome to SoccerSim! \n");    //Validate args as numbers
    int getArgs = args.length;                                //If args are divisible by four, push to new balls.
    double[] doubleArgs = new double[getArgs];                //If args minus one divisible by four, push to new balls and timeSlice.
    try{
      for(int i = 0; i < args.length; i++) {
        doubleArgs[i] = field.validateArgs(args[i]);
      }
    }
    catch(IllegalArgumentException e){
        System.out.println(e);
        System.out.println("Quitting.");
        System.exit( 0 );
    }
    if(args.length % 4 == 0) {
      for(int j = 0; j < args.length; j += 4) {
        field.CreateBall(doubleArgs[j], doubleArgs[j + 1], doubleArgs[j + 2], doubleArgs[j + 3]);
      }
    }
    else if((args.length - 1) % 4 == 0) {                 //catches optional timeSlice argument
      for(int j = 0; j < doubleArgs.length - 1; j += 4) {
        field.CreateBall(doubleArgs[j], doubleArgs[j + 1], doubleArgs[j + 2], doubleArgs[j + 3]);
      }
      field.timeSlice = field.validateTimeSliceArg(doubleArgs[doubleArgs.length - 1]);
    }
    else{
      System.out.println("Please input four arguments for each ball, and one more for (optional) time slice. \n");
      System.exit( 1 );
    }
  }

  public static void main(String[] args) throws InterruptedException{
    SoccerSim ss = new SoccerSim();
    Field simField = new Field();
    ss.handleArgs(args, simField);
    Field.formatNums();
    boolean programState = true;
    while(programState == true){
      simField.RunSim();
      programState = false;
    }
  }
}
