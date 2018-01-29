public class CountTheDays{
   public static void main(String[] args){
      int[] dates = new int[args.length];
      for(int i = 0; i < 6; i++){
        dates[i] = Integer.parseInt(args[i]);
      }
      System.out.println("This program will compute the number of days between two dates.");
      System.out.println("There are " + CalendarStuff.daysBetween(dates[0],
       dates[1], dates[2], dates[3], dates[4], dates[5]) + " days between " +
      args[0] + "/" + args[1] + "/" + args[2] + " and " + args[3] + "/" + args[4] +
      "/" + args[5] + ".");
   }
}
