/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Field.java
 *  Purpose       :  Provides a class defining methods for the Field class
 *  @author       :  Aison King
 *  Date written  :  2018-03-01
 *  Description   :  This class provides a bunch of methods which may be useful for the SoccerSim class
 *                   for Homework 5, part 1.  Includes the following:
 *                   RunSim(), getAllCollisions(), checkCollision(), CreateBall(), tick(), checkSpeeds()
 *                   midpoint(), getDistance()
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
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Field{

  static ArrayList<Ball> ballList;
  static double timeSlice;
  static double currentTick;
  static Ball pole;
  static long outputDelay;
  private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 1.0;
  private static final double MAXIMUM_DEGREE_VALUE = 360.0;
  private static final int minTimeDigits = 2;
  private static final int maxFracDigits = 3;
  private static NumberFormat timeFormat = NumberFormat.getInstance();
  private static final long DEFAULT_OUTPUT_DELAY_MILLISECONDS = 500;

  public static void formatNums(){
    timeFormat.setMinimumIntegerDigits(minTimeDigits);
    timeFormat.setMaximumFractionDigits(maxFracDigits);
  }

  public Field() {
    ballList = new ArrayList<Ball>();
    timeSlice = DEFAULT_TIME_SLICE_IN_SECONDS;
    outputDelay = DEFAULT_OUTPUT_DELAY_MILLISECONDS;
    currentTick = 0.0;
  }

  static public double getTotalSeconds() {
     double totalSeconds = currentTick * timeSlice;
     return totalSeconds;
  }

  static public String timeToString() {
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

  public Ball CreateBall(double x, double y, double xSpeed, double ySpeed){
    Ball addBall = new Ball(x, y, xSpeed, ySpeed);
    ballList.add(addBall);
    return addBall;
  }

  public Ball CreateBall(Ball ball) {
    ballList.add(ball);
    return ball;
  }

  public double validateArgs( String argValue ) throws IllegalArgumentException {
     double arg = 0;
     try{
       arg = Double.parseDouble(argValue);
     }
     catch(Exception e){
       throw new IllegalArgumentException("Please input a number for all arguments.");
     }
     return arg;
  }

  public double validateTimeSliceArg( double argValue ) throws IllegalArgumentException {
     double tSlice;
     tSlice = argValue;
     if(0.001 > tSlice || tSlice >= 1800) {
       throw new IllegalArgumentException("Please input a number between .001 and 1800 for time slice.");
     }
     return tSlice;
  }


  public static double tick(){                                //tick function progresses time for simulation
    for(int i = 0; i < ballList.size(); i++){                 //runs loop updating speed and position for each ball based on amount of time passed
      ballList.get(i).updatePosition(timeSlice);
      ballList.get(i).updateSpeed(timeSlice);
      if(Math.abs(ballList.get(i).getXSpeed()) < .08333){     //checks speeds and stops ball if moving less than 1 inch per second
        ballList.get(i).ballInfo[2] = 0;
      }
      if(Math.abs(ballList.get(i).getYSpeed()) < .08333){
        ballList.get(i).ballInfo[3] = 0;
      }
    }
    currentTick += timeSlice;                                 //adds the amount of time in timeslice to the current tick, so next tick() progresses in time
    return currentTick;
  }

  public static boolean getAllCollisions(){
    for(int i = 0; i < ballList.size(); i++){
      for(int j = i + 1; j < ballList.size(); j++){
        if(checkCollision(ballList.get(i), ballList.get(j))){
          System.out.println("\n \nFound collision at " + currentTick * timeSlice + " seconds." +
          " Collision at " + midpoint(ballList.get(i), ballList.get(j)).posToString() + " between ball " + i + " and ball " + j + ".");
          return true;
        }
      }
      if(checkCollision(ballList.get(i), pole)) {     //Second loop, includes the pole
        System.out.println("\n \nFound collision at " + currentTick * timeSlice + " seconds." +
        " Collision at " + midpoint(ballList.get(i), pole).posToString() + " between ball " + i + " and the pole.");
        return true;
      }
    }
    return false;
  }

  public static boolean checkCollision(Ball ball1,Ball ball2){      //Using ball radius of .371 feet, compares distance
    if(getDistance(ball1, ball2) <= .742 ){                         //If distance is less than radius * 2, return true for collision
      return true;
    }
    else{
      return false;
    }
  }

  public static Ball midpoint(Ball ball1, Ball ball2) {             //Returns a "ball" whose center is at the midpoint between two balls
    Ball midBall = new Ball(0,0,0,0);
    midBall.ballInfo[0] = ((ball1.getX() + ball2.getX()) / 2);      //midBall's X coordinate is halfway between the two X's
    midBall.ballInfo[1] = ((ball1.getY() + ball2.getY()) / 2);      //midBall's Y coordinate is halfway between the two Y's
    return midBall;
  }

  public static boolean checkSpeeds(){                              //Checks the speed of each ball, returns true only if all balls have stopped
    for(Ball i: ballList){                                          //Used for checking the end of the simulation
      if(i.getXSpeed() != 0 || i.getYSpeed() != 0){
        return false;
      }
    }
    System.out.println("\n \nAll balls have stopped.");
    return true;
  }

  public static double getDistance(Ball ball1, Ball ball2) {              //Uses pythagorean theorem to compute the distance between two ball centers.
    double xDist = Math.pow((Math.abs(ball1.getX() - ball2.getX())), 2);
    double yDist = Math.pow((Math.abs(ball1.getY() - ball2.getY())), 2);
    double totalDist = Math.sqrt(xDist + yDist);
    return totalDist;
  }

  public static void RunSim() throws InterruptedException {

    System.out.println("Running sim:");
    Random rand = new Random();
    pole = new Ball(rand.nextInt(50) + 1, rand.nextInt(50) + 1, 0, 0);      //simulation creates a random pole anywhere between the square (0,0) and (50,50)
    System.out.println("Pole placed at " + pole.posToString());             //pole is outside ballList to prevent its position being reported every tick.
    int ballInt = 0;
    while(!getAllCollisions() && !checkSpeeds()){                           //this sim will run until a collision is found or all balls stop moving
      //Thread.sleep(outputDelay);                                            //adds delay to each step for readability. comment out to remove delay
      System.out.println("\nAt time " + timeToString() + ": \n");
      ballInt = 0;
      for(Ball i: ballList) {                                               //this loop will print each ball's position
        System.out.println("     Ball " + ballInt + " is currently at " + i.posToString());
        ballInt += 1;
      }
      tick();
    }
  }

  public static void main(String args[]){
    /*Field testField = new Field();
    testField.CreateBall(2, 4, 1, 2);
    testField.CreateBall(1, 1, 2, 3);
    System.out.println(testField.ballList.get(0).posToString());
    System.out.println(testField.ballList.get(1).posToString());
    System.out.println(getDistance(testField.ballList.get(0), testField.ballList.get(1)));
    System.out.println(checkCollision(testField.ballList.get(0), testField.ballList.get(1)));
    testField.RunSim();*/
  }
}
