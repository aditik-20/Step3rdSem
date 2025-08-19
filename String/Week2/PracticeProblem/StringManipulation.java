import java.util.*;
public class StringManipulation {
   public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String str = sc.nextLine();
    String trimmedString = str.trim();
    System.out.println(trimmedString);
    String replacedString = str.replace(" ","_");
   }
    
}
