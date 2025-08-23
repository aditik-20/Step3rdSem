import java.util.Scanner;

public class StringLengthFinder {
    
    public static int findLength(String str) {
        int count = 0;
        try {
            while (true) {
                str.charAt(count);
                count++;
            }
        } catch (Exception e) {
            return count;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.next();
        
        int calculatedLength = findLength(input);
        int builtInLength = input.length();
        
        System.out.println("Length using user-defined method: " + calculatedLength);
        System.out.println("Length using built-in length() method: " + builtInLength);
    }
}
