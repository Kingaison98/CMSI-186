/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Clock.java
 *  Purpose       :  Provides a class defining methods for the ClockSolver class
 *  @author       :  B.J. Johnson
 *  Date written  :  2017-02-28
 *  Description   :  This class provides a bunch of methods which may be useful for the ClockSolver class
 *                   for Homework 4, part 1.  Includes the following:
 *
 *  Notes         :  None right now.  I'll add some as they occur.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-28  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class Clock {
  /**
   *  Class field definintions go here
   */
   private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 60.0;
   private static final double INVALID_ARGUMENT_VALUE = -1.0;
   private static final double MAXIMUM_DEGREE_VALUE = 360.0;
   private static final double HOUR_HAND_DEGREES_PER_SECOND = 0.00833;
   private static final double MINUTE_HAND_DEGREES_PER_SECOND = 0.1;
   static double timeSlice;
   static double currentTick;
   static double targetAngle;
   private static final int minTimeDigits = 2;
   private static final int maxFracDigits = 3;
   private static NumberFormat timeFormat = NumberFormat.getInstance();

  /**
   *  Constructor goes here
   */
   public Clock () {
     timeSlice = DEFAULT_TIME_SLICE_IN_SECONDS;
     currentTick = 0.0;
     targetAngle = 0.0;
     formatNums();
   }
   public static void formatNums(){
     timeFormat.setMinimumIntegerDigits(minTimeDigits);
     timeFormat.setMaximumFractionDigits(maxFracDigits);
   }

  /**
   *  Methods go here
   *
   *  Method to calculate the next tick from the time increment
   *  @return double-precision value of the current clock tick
   */
   public double tick() {
      this.currentTick += 1;
      return this.currentTick;
   }

  /**
   *  Method to validate the angle argument
   *  @param   argValue  String from the main programs args[0] input
   *  @return  double-precision value of the argument
   *  @throws  NumberFormatException
   */
   public double validateAngleArg( String argValue ) {
      double angle = 0;
      try{
        angle = Double.parseDouble(argValue);
        if(0 > angle || angle >= 360) {
             throw new IllegalArgumentException("Please input a target angle between 0 and 360 degrees.");
        }
      }
      catch(NumberFormatException e){
        System.out.println("Please input a number for your target angle.");
      }
      return angle;
   }

  /**
   *  Method to validate the optional time slice argument
   *  @param  argValue  String from the main programs args[1] input
   *  @return double-precision value of the argument or -1.0 if invalid
   *  note: if the main program determines there IS no optional argument supplied,
   *         I have elected to have it substitute the string "60.0" and call this
   *         method anyhow.  That makes the main program code more uniform, but
   *         this is a DESIGN DECISION, not a requirement!
   *  note: remember that the time slice, if it is small will cause the simulation
   *         to take a VERY LONG TIME to complete!
   */
   public double validateTimeSliceArg( String argValue ) {
      double tSlice;
      try{
        tSlice = Double.parseDouble(argValue);
        if(0.00001 > tSlice || tSlice >= 1800) {
          return -1.0;
        }
      }
      catch(NumberFormatException e){
        System.out.println("Please input a number for your time slice.");
        return -1.0;
      }
      return tSlice;
   }

  /**
   *  Method to calculate and return the current position of the hour hand
   *  @return double-precision value of the hour hand location
   */
   public double getHourHandAngle() {
      double hourAngle = getTotalSeconds() * HOUR_HAND_DEGREES_PER_SECOND;
      return hourAngle;
   }

  /**
   *  Method to calculate and return the current position of the minute hand
   *  @return double-precision value of the minute hand location
   */
   public double getMinuteHandAngle() {
      double minuteAngle = getTotalSeconds() * MINUTE_HAND_DEGREES_PER_SECOND;
      while(minuteAngle >= 360) {
        minuteAngle -= 360;
      }
      return minuteAngle;
   }

   /*public void checkHands(double error) {
     if(Math.abs(getHandAngle() - target.Angle) < error){
       this.toString();
     }
     else{
       System.out.println("The time does not match at " + this.toString() + ". Hour hand is at " + getHourHandAngle() + " degrees and minute is " + getMinuteHandAngle());
     }
   }*/

  /**
   *  Method to calculate and return the angle between the hands
   *  @return double-precision value of the angle between the two hands
   */
   public double getHandAngle() {
      double minuteAngle = getMinuteHandAngle();
      double hourAngle = getHourHandAngle();
      double difference = Math.abs(minuteAngle - hourAngle);
      return difference;
   }

  /**
   *  Method to fetch the total number of seconds
   *   we can use this to tell when 12 hours have elapsed
   *  @return double-precision value the total seconds private variable
   */
   public double getTotalSeconds() {
      double totalSeconds = this.currentTick * this.timeSlice;
      return totalSeconds;
   }

  /**
   *  Method to return a String representation of this clock
   *  @return String value of the current clock
   */
   public String toString() {
      double hours = 0;
      double minutes = 0;
      double seconds = 0;
      double currentTime = getTotalSeconds();
      while(currentTime >= 3600){
        hours += 1;
        currentTime -= 3600;
      }
      while(currentTime >= 60) {
        minutes += 1;
        currentTime -= 60;
      }
      seconds = currentTime;
      return (timeFormat.format(hours) + ":" + timeFormat.format(minutes) + ":" + timeFormat.format(seconds));
   }

  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  be sure to make LOTS of tests!!
   *  remember you are trying to BREAK your code, not just prove it works!
   */
   public static void main( String args[] ) {
      formatNums();
      System.out.println( "\nCLOCK CLASS TESTER PROGRAM\n" +
                          "--------------------------\n" );
      System.out.println( "  Creating a new clock: " );
      Clock clock = new Clock();
      System.out.println( "    New clock created: " + clock.toString() );
      System.out.println( "    Testing validateAngleArg()....");
      System.out.print( "      sending '  0 degrees', expecting double value   0.0" );
      try { System.out.println( (0.0 == clock.validateAngleArg( "0.0" )) ? " - got 0.0" : " - no joy" ); }
      catch( Exception e ) { System.out.println ( " - Exception thrown: " + e.toString() ); }

   }
}
