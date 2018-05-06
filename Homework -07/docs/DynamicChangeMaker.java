/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  DynamicChangeMaker.java
 * Purpose    :  Program to give the most efficient set of coins to reach a value based on specific denominations
 * @author    :  Aison King
 * Date       :  2018-04-20
 * Description:
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.1.0  2018-04-23  Aison King    Initial creation. Started wtih public class constructor and beginnings of algorithm
 *  1.2.0  2018-05-03  Aison King    Final version, added JavaDocs comments.
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

public class DynamicChangeMaker {

  private static int value = 0;
  private static int[] denomArray = new int[0];

  public DynamicChangeMaker() {
    super();
  }

  /***
   * Method to validate arguments passed in for changemaker algorithm. Throws an error if any two
   * numbers in den repeat, are negative, or are 0. Throws an error if value is less than or equal to zero
   *
   * @param den integer array of values to be turned into denom sets
   * @param value integer that represents the total change being made
   *
   **/

  public static boolean validateArgs(int[] den, int value) throws IllegalArgumentException {
    int i = 0;
    if(value <= 0){
      throw new IllegalArgumentException("BAD DATA: Can't make a negative/zero cent value.");
    }
    for(i = 0; i < den.length; i++){
      if(den[i] <= 0){
        throw new IllegalArgumentException("BAD DATA: Please use only positive non-zero integers.");
      }
    }
    for(i = 0; i < den.length - 1; i++){
      for(int j = i + 1; j < den.length; j++){
        if(den[i] == den[j]){
          throw new IllegalArgumentException("BAD DATA: Repeating integers.");
        }
      }
    }
    return true;
  }

  /***
   * Static method that looks for the best way to make exact change given a total and set of denominations.
   * Uses dynamic programming algorithm and Tuple class to reach a solution.
   *
   * @param den integer array that represents the denominations of coins used to make change
   * @param value integer that represents the total change being made
   *
   **/

  public static Tuple makeChangeWithDynamicProgramming(int[] den, int value){
    try{
      validateArgs(den, value);
      Tuple coinTuple = new Tuple(den);
      int rowCount = coinTuple.length();
      int columnCount = value;
      Tuple resultTuple = new Tuple(coinTuple.length());
      Tuple[][] testArray = new Tuple[rowCount][columnCount + 1];
      for(int i = 0; i < rowCount; i++) {
        for(int j = 0; j < columnCount; j++){
          testArray[i][j] = new Tuple(coinTuple.length());
        }
      }
      for(int row = 0; row < rowCount; row++){
        for(int column = 0; column < columnCount + 1; column++){
          if( column == 0){                                       //all first columns will be 0 total
          }
          else{
            /*System.out.println("Comparing " + coinTuple.getElement(row) + " to " +
            column);*/
            if((int)coinTuple.getElement(row) <= (int)column){          //if current element can go into column count
              Tuple cellTuple = new Tuple(coinTuple.length());
              cellTuple.setElement(row, 1);                       //add 1 to denomination lining up with current row
              //System.out.println("Goes in, this cell now " + cellTuple.toString());
              if(column - coinTuple.getElement(row) >= 0){        //if possible, look backwards
                Tuple backTuple = testArray[row][column - coinTuple.getElement(row)];
                //System.out.println("Looking backwards...");
                if(backTuple.isImpossible() == false){                    //if backwards is not impossible
                  cellTuple = cellTuple.add(backTuple);           //add backwards to current cell
                  /*System.out.println("Adding back cell " + backTuple.toString() + ". Current cell now " +
                  cellTuple.toString());*/
                }
                if(backTuple.isImpossible() == true){                     //if backwards is impossible
                  cellTuple = Tuple.IMPOSSIBLE;                   //make current cell impossible
                  //System.out.println("Last cell impossible, this cell impossible");
                }
              }
              if(row != 0){                                       //if past row 1
                Tuple upTuple = testArray[row - 1][column];       //look up
                //System.out.println("Looking at up cell: " + upTuple.toString());
                if(!upTuple.isImpossible()){                          //if up tuple isn't impossible
                  if(!cellTuple.isImpossible()){
                    if(upTuple.total() < cellTuple.total()){                   //and uptuple has less coins than this tuple
                      cellTuple = new Tuple(upTuple);                          //bring down upTuple
                      //System.out.println("Bringing down above cell. Current cell now " + cellTuple.toString());
                    }
                  }
                  else{
                    cellTuple = new Tuple(upTuple);                          //bring down upTuple
                    //System.out.println("Bringing down above cell. Current cell now " + cellTuple.toString());
                  }
                }
              }
              testArray[row][column] = cellTuple;                 //assign this tuple to it's place in the array
            }
            else{
              Tuple cellTuple = new Tuple(coinTuple.length());
              cellTuple = Tuple.IMPOSSIBLE;
              if(column - coinTuple.getElement(row) >= 0){        //if possible, look backwards
                Tuple backTuple = testArray[row][column - coinTuple.getElement(row)];
                //System.out.println("Looking backwards...");
                if(backTuple.isImpossible() == false){                    //if backwards is not impossible
                  cellTuple = cellTuple.add(backTuple);           //add backwards to current cell
                  /*System.out.println("Adding back cell " + backTuple.toString() + ". Current cell now " +
                  cellTuple.toString());*/
                }
                if(backTuple.isImpossible() == true){                     //if backwards is impossible
                  cellTuple = Tuple.IMPOSSIBLE;                   //make current cell impossible
                  //System.out.println("Last cell impossible, this cell impossible");
                }
              }
              if(row != 0){                                       //if past row 1
                Tuple upTuple = testArray[row - 1][column];       //look up
                //System.out.println("Looking at up cell: " + upTuple.toString());
                if(upTuple.isImpossible() == false){                          //if up tuple isn't impossible
                  if(cellTuple.isImpossible() == false){
                    if(upTuple.total() < cellTuple.total()){                   //and uptuple has less coins than this tuple
                      cellTuple = new Tuple(upTuple);                          //bring down upTuple
                      //System.out.println("Bringing down above cell. Current cell now " + cellTuple.toString());
                    }
                  }
                  if(cellTuple.isImpossible() == true){
                    cellTuple = new Tuple(upTuple);                          //bring down upTuple
                    //System.out.println("Bringing down above cell. Current cell now " + cellTuple.toString());
                  }
                }
              }
              testArray[row][column] = cellTuple;                 //assign this tuple to it's place in the array
              //System.out.println("This cell is now " + cellTuple.toString());
            }
          }
        }
      }
      resultTuple = new Tuple(testArray[rowCount - 1][columnCount]);
      if(resultTuple.total() == 0){
        resultTuple = Tuple.IMPOSSIBLE;
      }
      return resultTuple;                                         //return best solution
    }
    catch(Exception e){
      System.out.println(e.toString());
      e.printStackTrace();
    }
    return Tuple.IMPOSSIBLE;
  }

  /***
   * Method that checks to make sure all the arguments passed in running DynamicChangeMaker Class
   * are actually integer values.
   *
   * @param args Array of string values taken from the first argument of cmd entry to run Class
   * @param value String taken from the second argument of cmd entry to run class
   *
   * @throws NumberFormatException if any argument input is not an integer
   **/
  private void validateNums(String[] args, String value ){
    String[] argArray = args;
    this.denomArray = new int[argArray.length];
    try{
      for(int i = 0; i < argArray.length; i ++){
        this.denomArray[i] = Integer.parseInt(argArray[i]);
      }
      this.value = Integer.parseInt(value);
    }
    catch(Exception e){
      System.out.println(e.toString());
      e.printStackTrace();
    }
  }

  /**
   * Running the main function allows user to input two arguments on a command line and make change with those values
   * Validates arguments, then calls makeChangeWithDynamicProgramming() as a static function.
   **/

  public static void main(String[] args){
    DynamicChangeMaker dcm = new DynamicChangeMaker();
    if(args.length != 2){
      System.out.println("Please input two arguments. First for denominations and second for value.");
      System.exit( 0 );
    }
    if(args.length == 2){
      dcm.validateNums(args[0].split(","), args[1]);
      Tuple outputTuple = dcm.makeChangeWithDynamicProgramming(denomArray, value);
      if(outputTuple != Tuple.IMPOSSIBLE){
        System.out.println(dcm.value + " cents can be made with " + outputTuple.total() + " coins as follows: ");
        for(int i = 0; i < outputTuple.length(); i++){
          System.out.println(outputTuple.getElement(i) + " coins of " + dcm.denomArray[i] + " cents.");
        }
      }
    }
    System.exit(0);
  }

}
