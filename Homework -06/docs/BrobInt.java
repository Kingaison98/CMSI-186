/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  B.J. Johnson
 * Date       :  2017-04-04
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2017-04-04  B.J. Johnson  Initial writing and begin coding
 *  1.1.0  2017-04-13  B.J. Johnson  Completed addByt, addInt, compareTo, equals, toString, Constructor,
 *                                     validateDigits, two reversers, and valueOf methods; revamped equals
 *                                     and compareTo methods to use the Java String methods; ready to
 *                                     start work on subtractByte and subtractInt methods
 *  1.2.0  2018-04-19  Aison King    Completed constructor, addByte, compareTo, subtractByte, multiply,
 *                                     remainder, divide, stringReverser, validateDigits, etc. for Homework06
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.util.Set;
import java.util.LinkedHashSet;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class BrobInt {

   private static final int minDigits = 2;
   private static final int maxFracDigits = 3;
   private static NumberFormat numFormat = NumberFormat.getInstance();

   public static void formatNums(){
     numFormat.setMinimumIntegerDigits(minDigits);
     numFormat.setMaximumFractionDigits(maxFracDigits);
   }

   public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
   public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"
   public static final BrobInt TWO      = new BrobInt(  "2" );      /// Constant for "two"
   public static final BrobInt THREE    = new BrobInt(  "3" );      /// Constant for "three"
   public static final BrobInt FOUR     = new BrobInt(  "4" );      /// Constant for "four"
   public static final BrobInt FIVE     = new BrobInt(  "5" );      /// Constant for "five"
   public static final BrobInt SIX      = new BrobInt(  "6" );      /// Constant for "six"
   public static final BrobInt SEVEN    = new BrobInt(  "7" );      /// Constant for "seven"
   public static final BrobInt EIGHT    = new BrobInt(  "8" );      /// Constant for "eight"
   public static final BrobInt NINE     = new BrobInt(  "9" );      /// Constant for "nine"
   public static final BrobInt TEN      = new BrobInt( "10" );      /// Constant for "ten"
   public static final BrobInt HUNDRED  = new BrobInt("100" );
   public String CHAR_STRING = "1234567890-+";
   public char[] ARRAY = CHAR_STRING.toCharArray();

  /// Some constants for other intrinsic data types
  ///  these can help speed up the math if they fit into the proper memory space
   public static final BrobInt MAX_INT  = new BrobInt( new Integer( Integer.MAX_VALUE ).toString() );
  // public static final BrobInt MIN_INT  = new BrobInt( new Integer( Integer.MIN_VALUE ).toString() );
   public static final BrobInt MAX_LONG = new BrobInt( new Long( Long.MAX_VALUE ).toString() );
  // public static final BrobInt MIN_LONG = new BrobInt( new Long( Long.MIN_VALUE ).toString() );

  /// These are the internal fields
   private String internalValue = "";        // internal String representation of this BrobInt
   private boolean sign          = false;         // "0" is positive, "1" is negative
   private String reversed      = "";        // the backwards version of the internal String representation
   private byte byteVersion[]   = null;      // byte array for storing the string values; uses the reversed string

  /**
   *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
   *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
   *   for later use
   *  @param  value  String value to make into a BrobInt
   */
   public BrobInt( String value ) {
     internalValue = value;
     sign = false;
     if(internalValue.charAt(0) == '-'){
       sign = true;
       internalValue.replace('-',' ');
       internalValue = internalValue.trim();
     }
     internalValue = internalValue.replace('+', ' ');  //hanging plus sign is useless
     internalValue = internalValue.trim();
     try{
       validateDigits();
     }
     catch(IllegalArgumentException e){
       System.out.println(e.toString());
       e.printStackTrace();
     }
     reversed = stringReverse(internalValue);
     reversed = reversed.replace('-', ' ');            //removes sign from string since it's stored in Objec
     reversed = reversed.replace('+', ' ');            //
     reversed = reversed.trim();                       //Gets rid of whitespace left, since array uses length of string
     int j = 0;
     int i = 0;
     double arrayLength = Math.ceil((double)reversed.length() / 2);
     byteVersion = new byte[(int)arrayLength];
     for( ; i <= reversed.length() - 2; i += 2){
       byteVersion[j] = Byte.parseByte(stringReverse(reversed.substring(i , i + 2)));
       j++;
     }
     if(i == reversed.length() - 1){
       byteVersion[j] = Byte.parseByte(String.valueOf(reversed.charAt(i)));
     }
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to validate that all the characters in the value are valid decimal digits
   *  @return  boolean  true if all digits are good
   *  @throws  IllegalArgumentException if something is hinky
   *  note that there is no return false, because of throwing the exception
   *  note also that this must check for the '+' and '-' sign digits
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean validateDigits() {
     String BrobString = new String(internalValue);
     boolean within = false;
     for(int i = 0; i < BrobString.length(); i++){
       for(char c: ARRAY){
         if(BrobString.charAt(i) == c){
           within = true;
         }
       }
       if(within == false){
         throw new IllegalArgumentException("Please input a number that is only decimal digits.");
       }
       within = false;
     }
     return true;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to reverse the value of this BrobInt
   *  @return BrobInt that is the reverse of the value of this BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt reverser() {
     StringBuffer original = new StringBuffer(this.internalValue);
     StringBuffer reverse = new StringBuffer("");
     for(int i = 0; i < original.length(); i++){
       reverse.append(original.charAt(original.length() - (i + 1)));
     }
     return new BrobInt(reverse.toString());
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to reverse any string sequence. Used to initialize BrobInt and print bytes from 1's place up
   *  @param s  String to be reversed
   *  @return String that is the reverse of the string passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static String stringReverse(String s) {
     StringBuffer original = new StringBuffer(s);
     StringBuffer flipped = new StringBuffer("");
     for(int i = 0; i < original.length(); i++) {
       flipped.append(original.charAt(original.length() - (1 + i)));
     }
     return flipped.toString();
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to add the value of a BrobIntk passed as argument to this BrobInt using byte array
   *  @param  gint         BrobInt to add to this
   *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt addByte( BrobInt gint ) {
     StringBuffer result = new StringBuffer("");
     int add = 0;
     String addString = " ";
     BrobInt resultInt = new BrobInt("0");
     Byte carry = b(0);
     if(gint.sign == false && this.sign == false) {                      //if both signs are positive
       if(this.byteVersion.length >= gint.byteVersion.length){                                    //if initial is greater than target, add using target length
         int i = 0;
         while(i < gint.byteVersion.length){
           add = (this.byteVersion[i] + gint.byteVersion[i] + carry);
           carry = b(0);
           while(add > 99){
             add -= 100;
             carry = (byte)(carry + b(1));
           }
           if(add < 10){
             addString = (String.valueOf(add) + "0");
             result.append(String.valueOf(add) + "0");
           }
           else{
             addString = (String.valueOf(add));
             result.append(stringReverse(addString));
           }
           add = 0;
           i++;
         }
         while(i < this.byteVersion.length){                       //add on the rest from initial, since it is greater/longer
           add = (this.byteVersion[i] + carry);
           carry = b(0);
           while(add > 99){
             add -= 100;
             carry = (byte)(carry + b(1));
           }
           if(add < 10){
             addString = (String.valueOf(add) + "0");
             result.append(String.valueOf(add) + "0");
           }
           else{
             addString = (String.valueOf(add));
             result.append(stringReverse(addString));
           }
           i++;
         }
         if(carry != b(0)){
           result.append(Byte.valueOf(carry));
         }
       }
       if(this.byteVersion.length < gint.byteVersion.length){                                    //if initial is less than target, add using initial length
         int i = 0;
         while(i < this.byteVersion.length){
           add = (this.byteVersion[i] + gint.byteVersion[i] + carry);
           carry = b(0);
           while(add > 99){
             add -= 100;
             carry = (byte)(carry + b(1));
           }
           if(add < 10){
             addString = (String.valueOf(add) + "0");
             result.append(String.valueOf(add) + "0");
           }
           else{
             addString = (String.valueOf(add));
             result.append(stringReverse(addString));
           }
           add = 0;
           i++;
         }
         while(i < gint.byteVersion.length){                       //add on the rest from target, since it is greater/longer
           add = (gint.byteVersion[i] + carry);
           carry = b(0);
           while(add > 99){
             add -= 100;
             carry = (byte)(carry + b(1));
           }
           if(add < 10){
             addString = (String.valueOf(add) + "0");
             result.append(String.valueOf(add) + "0");
           }
           else{
             addString = (String.valueOf(add));
             result.append(stringReverse(addString));
           }
           i++;
         }
         if(carry != b(0)){
           result.append(Byte.valueOf(carry));
         }
       }
     }
     if(gint.sign == true && this.sign == true){                         //if both signs are negative
       if(this.byteVersion.length >= gint.byteVersion.length){                                    //if initial is greater than target, add using target length
         int i = 0;
         while(i < gint.byteVersion.length){
           add = (this.byteVersion[i] + gint.byteVersion[i] + carry);
           carry = b(0);
           while(add > 99){
             add -= 100;
             carry = (byte)(carry + b(1));
           }
           if(add < 10){
             addString = (String.valueOf(add) + "0");
             result.append(String.valueOf(add) + "0");
           }
           else{
             addString = (String.valueOf(add));
             result.append(stringReverse(addString));
           }
           add = 0;
           i++;
         }
         while(i < this.byteVersion.length){                       //add on the rest from initial, since it is greater/longer
           add = (this.byteVersion[i] + carry);
           carry = b(0);
           while(add > 99){
             add -= 100;
             carry = (byte)(carry + b(1));
           }
           if(add < 10){
             addString = (String.valueOf(add) + "0");
             result.append(String.valueOf(add) + "0");
           }
           else{
             addString = (String.valueOf(add));
             result.append(stringReverse(addString));
           }
           i++;
         }
         if(carry != b(0)){
           result.append(Byte.valueOf(carry));
         }
       }
       if(this.byteVersion.length < gint.byteVersion.length){                                    //if initial is less than target, add using initial length
         int i = 0;
         while(i < this.byteVersion.length){
           add = (this.byteVersion[i] + gint.byteVersion[i] + carry);
           carry = b(0);
           while(add > 99){
             add -= 100;
             carry = (byte)(carry + b(1));
           }
           if(add < 10){
             addString = (String.valueOf(add) + "0");
             result.append(String.valueOf(add) + "0");
           }
           else{
             addString = (String.valueOf(add));
             result.append(stringReverse(addString));
           }
           add = 0;
           i++;
         }
         while(i < gint.byteVersion.length){                       //add on the rest from target, since it is greater/longer
           add = (gint.byteVersion[i] + carry);
           carry = b(0);
           while(add > 99){
             add -= 100;
             carry = (byte)(carry + b(1));
           }
           if(add < 10){
             addString = (String.valueOf(add) + "0");
             result.append(String.valueOf(add) + "0");
           }
           else{
             addString = (String.valueOf(add));
             result.append(stringReverse(addString));
           }
           i++;
         }
         if(carry != b(0)){
           result.append(Byte.valueOf(carry));
         }
       }
       result.append("-");
     }
     if(this.sign == true && gint.sign == false){          //pos plus neg is same as subtraction
       return gint.subtractByte(this);
     }
     if(gint.sign == true && this.sign == false){          //neg plus pos is same as subtraction, swapping this and gint
       return this.subtractByte(gint);
     }
     resultInt = new BrobInt(result.reverse().toString());
     return resultInt;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to call any byte value for comparison.
   *  @param i      integer to be coerced to byte value
   *  @return byte value equal to the value of passed in integer
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static byte b(int i){
     return (byte) i;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using bytes
   *  @param  gint         BrobInt to subtract from this
   *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt subtractByte( BrobInt gint ) {
     StringBuffer result = new StringBuffer("");
     int sub = 0;
     int add = 0;
     Byte pull = b(0);
     Byte carry = b(0);
     boolean signChange = false;
     int i = 0;
     BrobInt resultInt = new BrobInt("0");
     if(this.sign == false && gint.sign == false){                    //if both are positive, subtract gint
       if(this.compareTo(gint) >= 0){
         while(i < gint.byteVersion.length){
           sub = (this.byteVersion[i] - (gint.byteVersion[i] + pull));
           pull = b(0);
           if(sub < 0){
             pull = b(1);
             sub += 100;
           }
           if(sub < 10){
             result.append(String.valueOf(sub) + "0");
           }
           else{
             result.append(stringReverse(String.valueOf(sub)));
           }
           i++;
         }
         while(i < this.byteVersion.length){
           sub = (this.byteVersion[i] - pull);
           pull = b(0);
           if(sub < 0){
             pull = b(1);
             sub += 100;
           }
           if(sub < 10){
             result.append(String.valueOf(sub) + "0");
           }
           else{
             result.append(stringReverse(String.valueOf(sub)));
           }
           i++;
         }
         resultInt = new BrobInt(result.reverse().toString());
         return resultInt;
       }
       if(this.compareTo(gint) < 0){
         while(i < this.byteVersion.length){
           sub = (this.byteVersion[i] - (gint.byteVersion[i] + pull));
           pull = b(0);
           if(sub < 0){
             if(i < this.byteVersion.length - 1){
               pull = b(1);
               sub += 100;
             }
             else{
               sub *= -1;
             }
           }
           if(sub < 10){
             result.append(String.valueOf(sub) + "0");
           }
           else{
             result.append(stringReverse(String.valueOf(sub)));
           }
           i++;
         }
         while(i < gint.byteVersion.length){
           sub = gint.byteVersion[i];
           if(sub < 10){
             result.append(String.valueOf(sub) + "0");
           }
           else{
             result.append(stringReverse(String.valueOf(sub)));
           }
           i++;
         }
         result.append("-");
         resultInt = new BrobInt(result.reverse().toString());
         return resultInt;
       }
     }
     if(this.sign == true && gint.sign == false){                    //if this is negative and gint is positive, subtract gint from this
       if(this.compareTo(gint) >= 0){
         this.sign = false;
         resultInt = gint.addByte(this);
         resultInt.sign = true;
         return resultInt;
       }
       if(this.compareTo(gint) < 0){
         this.sign = false;
         resultInt = gint.addByte(this);
         resultInt.sign = true;
         return resultInt;
       }
     }
     if(this.sign == false && gint.sign == true){                   //if this is positive and gint is negative, add gint to this
       if(this.compareTo(gint) >= 0){
         gint.sign = false;
         resultInt = this.addByte(gint);
         gint.sign = true;
         return resultInt;
       }
       if(this.compareTo(gint) < 0){
         gint.sign = false;
         resultInt = this.addByte(gint);
         gint.sign = true;
         return resultInt;
       }
     }
     if(this.sign == true && gint.sign == true){                   //if both signs are negative, subtract this from gint
       this.sign = false;
       gint.sign = false;
       if(this.compareTo(gint) >= 0){
         resultInt = this.subtractByte(gint);
         resultInt.sign = true;
         this.sign = true;
         gint.sign = true;
         return resultInt;
       }
       if(this.compareTo(gint) < 0){
         resultInt = gint.subtractByte(this);
         this.sign = true;
         gint.sign = true;
         return resultInt;
       }
     }
     return this;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
   *  @param  gint         BrobInt to multiply by this
   *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt multiply( BrobInt gint ) {
     StringBuffer resultString = new StringBuffer();
     boolean posNeg = false;
     if(this.sign == true ^ gint.sign == true){
       posNeg = true;
     }
     if(this.byteVersion.length >= gint.byteVersion.length){
       byte[] a = new byte[this.byteVersion.length];           //initializing arrays
       byte[] b = new byte[gint.byteVersion.length];
       int[] c = new int[a.length + b.length + 1]; //result array
       int carry = 0;
       int i = 0;
       int k = 0;
       for(byte aByte: this.byteVersion){
         a[i] = aByte;
         i++;
       }
       i = 0;
       for(byte bByte: gint.byteVersion){
         b[i] = bByte;
         i++;
       }
       for(int cByte: c){
         cByte = 0;
       }
       for(i = 0; i < b.length; i++){
         k = i;
         for(int j = 0; j < a.length; j++){
           c[k] = (b[i] * a[j]) + (carry + c[k]);
           if(c[k] > 99){
             carry = (c[k] / 100);
             c[k] = (c[k] % 100);
           }
           else{
             carry = 0;
           }
           k++;
         }
         if(carry > 0){
           c[k] = carry;
         }
       }
       for(int check: c){
         if(Integer.toString(check).length() < 2){
           resultString.append(Integer.toString(check) + "0");
         }
         else{
           resultString.append(stringReverse(Integer.toString(check)));
         }
       }
     }
     if(this.byteVersion.length < gint.byteVersion.length){
       byte[] a = new byte[gint.byteVersion.length];
       byte[] b = new byte[this.byteVersion.length];
       int[] c = new int[a.length + b.length + 1];
       int i = 0;
       int k = 0;
       int carry = 0;
       for(byte aByte: gint.byteVersion){
         a[i] = aByte;
         i++;
       }
       i = 0;
       for(byte bByte: this.byteVersion){
         b[i] = bByte;
         i++;
       }
       i = 0;
       for(int cByte: c){
         cByte = 0;
       }
       for(i = 0; i < b.length; i++){
         k = i;
         for(int j = 0; j < a.length; j++){
           c[k] = (b[i] * a[j]) + (carry + c[k]);
           if(c[k] > 99){
             carry = (c[k] / 100);
             c[k] = (c[k] % 100);
           }
           else{
             carry = 0;
           }
           k++;
         }
         if(carry > 0){
           c[k] = carry;
         }
       }
       for(int check: c){
         if(Integer.toString(check).length() < 2){
           resultString.append(Integer.toString(check) + "0");
         }
         else{
           resultString.append(stringReverse(Integer.toString(check)));
         }
       }
     }
     while(resultString.charAt(resultString.length() - 1) == '0'){
       resultString.deleteCharAt(resultString.length() - 1);
     }
     if(posNeg == true){
       resultString.append("-");
     }
     return new BrobInt(stringReverse(resultString.toString()));
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
   *  @param  gint         BrobInt to divide this by
   *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt divide( BrobInt gint ) {
     if(this.equals(gint)){
       return ONE;
     }
     if(this.compareTo(gint) < 0){
       return ZERO;
     }
     else{
       BrobInt d1 = new BrobInt(gint.toString());    //divisor (gint)
       BrobInt d2 = new BrobInt(this.toString());    //dividend (this)
       BrobInt d3 = new BrobInt("0");              //current dividend
       BrobInt quotient = new BrobInt("0");
       int stringLength = (d1.toString().length());
       BrobInt extract = new BrobInt("0");
       d3 = new BrobInt(d2.toString().substring(0, stringLength));
       if(d1.compareTo(d3) > 0){
         stringLength ++;
         d3 = new BrobInt(d2.toString().substring(0, stringLength));
       }
       while(stringLength <= d2.toString().length()){
         while(d3.compareTo(d1) >= 0){
           d3 = d3.subtractByte(d1);
           quotient = quotient.addByte(ONE);
         }
         if(stringLength == d2.toString().length()){
           break;
         }
         stringLength ++;
         d3 = d3.multiply(TEN);
         quotient = quotient.multiply(TEN);
         extract = new BrobInt(d2.toString().substring(stringLength - 1, stringLength));
         d3 = d3.addByte(extract);
       }
       return quotient;
     }
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to get the remainder of division of this BrobInt by the one passed as argument
   *  @param  gint         BrobInt to divide this one by
   *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt remainder( BrobInt gint ) {
     BrobInt resultInt = new BrobInt("0");
     resultInt = this.subtractByte(gint.multiply(this.divide(gint)));
     return resultInt;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to compare a BrobInt passed as argument to this BrobInt
   *  @param  gint  BrobInt to add to this
   *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
   *  NOTE: this method performs a lexicographical comparison using the java String "compareTo()" method
   *        THAT was easy.....
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public int compareTo( BrobInt gint ) {
     if(this.sign == false && gint.sign == true){             //catches pos/neg values
       return 1;
     }
     if(this.sign == true && gint.sign == false){             //catches pos/neg values
       return -1;
     }
     if(this.toString().length() > gint.toString().length()){
       return 1;
     }
     if(this.toString().length() < gint.toString().length()){
       return -1;
     }
     return (this.toString().compareTo( gint.toString() ));
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to check if a BrobInt passed as argument is equal to this BrobInt
   *  @param  gint     BrobInt to compare to this
   *  @return boolean  that is true if they are equal and false otherwise
   *  NOTE: this method performs a similar lexicographical comparison as the "compareTo()" method above
   *        also using the java String "equals()" method -- THAT was easy, too..........
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public boolean equals( BrobInt gint ) {
     return (internalValue.equals( gint.toString() ));
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to return a BrobInt given a long value passed as argument
   *  @param  value         long type number to make into a BrobInt
   *  @return BrobInt  which is the BrobInt representation of the long
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static BrobInt valueOf( long value ) throws NumberFormatException {
     BrobInt gi = null;
     try {
       gi = new BrobInt( new Long( value ).toString() );
     }
     catch( NumberFormatException nfe ) {
       System.out.println( "\n  Sorry, the value must be numeric of type long." );
     }
     return gi;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to return a String representation of this BrobInt
   *  @return String  which is the String representation of this BrobInt
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public String toString() {
     String byteVersionOutput = "";
     String byteBuffer = "";
     int i = 0;
     for( ; i < byteVersion.length - 1; i++ ) {                       //this loop adds bytes on from 1's place up
       if(byteVersion[i] < b(10)){                                  //adds on placeholder 0's
         byteBuffer = (byteVersion[i] + "0");
       }
       else{
         byteBuffer = stringReverse(Byte.toString( byteVersion[i]));
       }
       byteVersionOutput = byteVersionOutput.concat(byteBuffer);     //adds buffer to current string
     }
     byteVersionOutput = byteVersionOutput.concat(
       stringReverse(Byte.toString( byteVersion[i])));
     byteVersionOutput = new String(
       new StringBuffer(
         byteVersionOutput.concat(
           ((this.sign)? "-" : "")) ).reverse() ).trim();             //adds on sign at the end, and reverses string for proper order
     while(byteVersionOutput.charAt(0) == '0'){                       //this loop trims hanging 0's
       if(byteVersionOutput.length() == 1){                           //if 0 is the last character, break the loop
         break;
       }
       byteVersionOutput = new String(byteVersionOutput.substring(1));//cuts out the leading 0
     }
     return byteVersionOutput;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to display an Array representation of this BrobInt as its bytes
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static void toArray( byte[] d ) {
     System.out.println( Arrays.toString( d ) );
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  the main method redirects the user to the test class
   *  @param  args  String array which contains command line arguments
   *  note:  we don't really care about these
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public static void main( String[] args ) {
     System.exit( 0 );
   }
}
