/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Ball.java
 *  Purpose       :  Provides a class defining methods for the Ball class
 *  @author       :  Aison King
 *  Date written  :  2018-03-01
 *  Description   :  This class provides a bunch of methods which may be useful for the SoccerSim class
 *                   for Homework 5, part 1.  Includes the following:
 *                   getX(), getY(), getXSpeed(), getYSpeed(), posToString()
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
import java.text.NumberFormat;

public class Ball{

  private static final double FRICTION = .01;
  private static final double BALL_RADIUS = .371;
  private static final double DEGREES_TO_RADIANS = .01745;
  private static final double DEFAULT_TIME_SLICE_SECONDS = 1;
  double[] ballInfo;
  private static final int maxFracDigits = 3;
  static final NumberFormat NumFormat = NumberFormat.getInstance();

  public Ball (double xArg, double yArg, double xSpeedArg, double ySpeedArg) {
    ballInfo = new double[4];
    ballInfo[0] = xArg;
    ballInfo[1] = yArg;
    ballInfo[2] = xSpeedArg;
    ballInfo[3] = ySpeedArg;
  }

  public void formatNums(){
    NumFormat.setMaximumFractionDigits(maxFracDigits);
  }

  public void updatePosition(double timeSlice) {
    ballInfo[0] = ballInfo[0] + (ballInfo[2] * timeSlice);
    ballInfo[1] = ballInfo[1] + (ballInfo[3] * timeSlice);
  }

  public void updateSpeed(double timeSlice) {
    if(ballInfo[2] > 0){
      ballInfo[2] -= (FRICTION * timeSlice);
    }
    if(ballInfo[2] < 0){
      ballInfo[2] += (FRICTION * timeSlice);
    }
    if(ballInfo[3] > 0){
      ballInfo[3] -= (FRICTION * timeSlice);
    }
    if(ballInfo[3] < 0){
      ballInfo[3] += (FRICTION * timeSlice);
    }
  }

  public double getX() {
    return ballInfo[0];
  }

  public double getY() {
    return ballInfo[1];
  }

  public double getXSpeed() {
    return ballInfo[2];
  }

  public double getYSpeed() {
    return ballInfo[3];
  }

  public String posToString(){
    return ("(" + NumFormat.format(getX()) + ", " + NumFormat.format(getY()) + ")");
  }

  public static void main(String args[]){
    /*Ball testBall = new Ball(3, 2, 65, 5);
    System.out.println("Ball starting position: " + testBall.posToString());
    System.out.println("TestBall position is " + testBall.posToString());
    testBall.updatePosition();
    System.out.println("TestBall position is " + testBall.posToString());
    */

  }
}
