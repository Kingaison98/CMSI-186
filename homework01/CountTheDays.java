import java.util.Scanner;
public class CountTheDays{
   private static Scanner scanner = new Scanner(System.in);
   public static void main(String[] args){
      System.out.println("This program will compute the number of days between two dates.");
      System.out.println("Please input the month of the first date as a number:");
      String entry = scanner.nextLine();
      int month1 = Integer.parseInt( entry );
      System.out.println("Please input the day of the first date as a number:");
      entry = scanner.nextLine();
      int day1 = Integer.parseInt( entry );
      System.out.println("Please input the year of the first date as a number:");
      entry = scanner.nextLine();
      int year1 = Integer.parseInt( entry );
      System.out.println("Please input the month of the second date as a number:");
      entry = scanner.nextLine();
      int month2 = Integer.parseInt( entry );
      System.out.println("Please input the day of the second date as a number:");
      entry = scanner.nextLine();
      int day2 = Integer.parseInt( entry );
      System.out.println("Please input the year of the second date as a number:");
      entry = scanner.nextLine();
      int year2 = Integer.parseInt( entry );
      System.out.println("There are " + CalendarStuff.daysBetween(month1, day1, year1, month2, day2, year2)
       + " days between " + month1 + "/" + day1 + "/" + year1 + " and " + month2 +
       "/" + day2 + "/" + year2 + ".");
   }
}
