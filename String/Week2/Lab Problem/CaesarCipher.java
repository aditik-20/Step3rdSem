import java.util.*;

public class CaesarCipher {

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                char newCh = (char) ((ch - 'A' + shift) % 26 + 'A');
                result.append(newCh);
            } else if (ch >= 'a' && ch <= 'z') {
                char newCh = (char) ((ch - 'a' + shift) % 26 + 'a');
                result.append(newCh);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - (shift % 26));
    }

    public static void displayAsciiValues(String text, String label) {
        System.out.println("\n" + label + " Text: " + text);
        System.out.print(label + " ASCII : ");
        for (int i = 0; i < text.length(); i++) {
            System.out.print((int) text.charAt(i) + " ");
        }
        System.out.println();
    }

    public static boolean validate(String original, String decrypted) {
        return original.equals(decrypted);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.print("Enter shift value: ");
        int shift = sc.nextInt();

        displayAsciiValues(text, "Original");

        String encrypted = encrypt(text, shift);
        displayAsciiValues(encrypted, "Encrypted");

        String decrypted = decrypt(encrypted, shift);
        displayAsciiValues(decrypted, "Decrypted");

        System.out.println("\nValidation: " + (validate(text, decrypted) ? "Successful ✅" : "Failed ❌"));
    }
}
