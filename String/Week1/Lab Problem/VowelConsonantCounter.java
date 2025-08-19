import java.util.Scanner;

public class VowelConsonantCounter {

    public static String classifyChar(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            ch = (char)(ch + 32);
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

    public static int[] countVowelsAndConsonants(String str) {
        int vowels = 0;
        int consonants = 0;
        int index = 0;

        try {
            while (true) {
                char ch = str.charAt(index);
                String result = classifyChar(ch);

                if (result.equals("Vowel")) {
                    vowels++;
                } else if (result.equals("Consonant")) {
                    consonants++;
                }

                index++;
            }
        } catch (Exception e) {
            return new int[]{vowels, consonants};
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        int[] result = countVowelsAndConsonants(input);

        System.out.println("Number of Vowels: " + result[0]);
        System.out.println("Number of Consonants: " + result[1]);
    }
}
