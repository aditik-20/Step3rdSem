import java.util.*;
public class TextCaseConversion {
    public static char toUpper(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return (char)(ch - 32);
        }
        return ch;
    }
    public static char toLower(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return (char)(ch + 32);
        }
        return ch;
    }

    public static String convertToUpper(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toUpper(text.charAt(i)));
        }
        return result.toString();
    }

    public static String convertToLower(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toLower(text.charAt(i)));
        }
        return result.toString();
    }

    public static String convertToTitle(String text) {
        StringBuilder result = new StringBuilder();
        boolean newWord = true;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch == ' ') {
                result.append(ch);
                newWord = true;
            } else {
                if (newWord) {
                    result.append(toUpper(ch));
                    newWord = false;
                } else {
                    result.append(toLower(ch));
                }
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        String manualUpper = convertToUpper(text);
        String manualLower = convertToLower(text);
        String manualTitle = convertToTitle(text);

        String builtInUpper = text.toUpperCase();
        String builtInLower = text.toLowerCase();

        System.out.printf("%-20s %-25s\n", "Conversion", "Result");
        System.out.printf("%-20s %-25s\n", "Manual Uppercase", manualUpper);
        System.out.printf("%-20s %-25s\n", "Built-in Uppercase", builtInUpper);
        System.out.printf("%-20s %-25s\n", "Manual Lowercase", manualLower);
        System.out.printf("%-20s %-25s\n", "Built-in Lowercase", builtInLower);
        System.out.printf("%-20s %-25s\n", "Manual Title Case", manualTitle);
    }
}
