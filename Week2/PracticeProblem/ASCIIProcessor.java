import java.util.*;

public class ASCIIProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        System.out.println("\n--- Character Analysis ---");
        for (char ch : input.toCharArray()) {
            int ascii = (int) ch;
            System.out.println("Character: " + ch + " | ASCII: " + ascii);
            String type = classifyCharacter(ch);
            System.out.println("Type: " + type);

            if (Character.isLetter(ch)) {
                char toggled = toggleCase(ch);
                System.out.println("Toggled Case: " + toggled + " | ASCII: " + (int) toggled);

                int diff = Math.abs((int) Character.toUpperCase(ch) - (int) Character.toLowerCase(ch));
                System.out.println("Upper-Lower ASCII Difference: " + diff);
            }
            System.out.println();
        }

        System.out.println("\n--- ASCII Table (32–126) ---");
        displayASCIITable(32, 126);

        System.out.println("\n--- Caesar Cipher ---");
        System.out.print("Enter shift value: ");
        int shift = scanner.nextInt();
        String encrypted = caesarCipher(input, shift);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + caesarCipher(encrypted, -shift));

        System.out.println("\n--- ASCII Conversion ---");
        int[] asciiArray = stringToASCII(input);
        System.out.println("ASCII Array: " + Arrays.toString(asciiArray));
        String backToString = asciiToString(asciiArray);
        System.out.println("Converted Back: " + backToString);

        scanner.close();
    }

    public static String classifyCharacter(char ch) {
        if (ch >= 'A' && ch <= 'Z') return "Uppercase Letter";
        else if (ch >= 'a' && ch <= 'z') return "Lowercase Letter";
        else if (ch >= '0' && ch <= '9') return "Digit";
        else return "Special Character";
    }

    public static char toggleCase(char ch) {
        if (ch >= 'A' && ch <= 'Z') return (char) (ch + 32);
        else if (ch >= 'a' && ch <= 'z') return (char) (ch - 32);
        else return ch;
    }

    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                result.append((char) ((ch - 'A' + shift + 26) % 26 + 'A'));
            } else if (Character.isLowerCase(ch)) {
                result.append((char) ((ch - 'a' + shift + 26) % 26 + 'a'));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void displayASCIITable(int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.println(i + " -> " + (char) i);
        }
    }

    public static int[] stringToASCII(String text) {
        int[] asciiValues = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            asciiValues[i] = (int) text.charAt(i);
        }
        return asciiValues;
    }

    public static String asciiToString(int[] asciiValues) {
        StringBuilder sb = new StringBuilder();
        for (int val : asciiValues) {
            sb.append((char) val);
        }
        return sb.toString();
    }
}
