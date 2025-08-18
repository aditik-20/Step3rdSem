import java.util.Scanner;

public class CharacterTypeChecker {

    public static String classifyChar(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            ch = (char)(ch + 32); // Convert uppercase to lowercase using ASCII
        }

        if (ch >= 'a' && ch <= 'z') {
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                return "Vowel";
            } else {
                return "Consonant";
            }
        }

        return "Not a Letter";
    }

    public static String[][] analyzeCharacters(String str) {
        int length = 0;
        try {
            while (true) {
                str.charAt(length);
                length++;
            }
        } catch (Exception e) {
            // end of string reached
        }

        String[][] result = new String[length][2];

        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);
            result[i][0] = String.valueOf(ch);
            result[i][1] = classifyChar(ch);
        }

        return result;
    }

    public static void displayCharacterTypes(String[][] charTypes) {
        System.out.println("Character\tType");
        System.out.println("-------------------------");

        for (int i = 0; i < charTypes.length; i++) {
            System.out.println(charTypes[i][0] + "\t\t" + charTypes[i][1]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[][] result = analyzeCharacters(input);
        displayCharacterTypes(result);
    }
}
