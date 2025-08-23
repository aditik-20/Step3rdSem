import java.util.*;

public class StringManipulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sentence with mixed formatting: ");
        String input = scanner.nextLine();

        String trimmed = input.trim();
        System.out.println("\nTrimmed: " + trimmed);

        String replaced = trimmed.replace(" ", "_");
        System.out.println("Spaces Replaced: " + replaced);

        String noDigits = trimmed.replaceAll("\\d", "");
        System.out.println("Removed Digits: " + noDigits);

        String[] words = trimmed.split("\\s+");
        System.out.println("Split Words: " + Arrays.toString(words));

        String rejoined = String.join(" | ", words);
        System.out.println("Rejoined: " + rejoined);

        System.out.println("\n--- Additional Processing ---");
        System.out.println("Without Punctuation: " + removePunctuation(trimmed));
        System.out.println("Capitalized Words: " + capitalizeWords(trimmed));
        System.out.println("Reversed Word Order: " + reverseWordOrder(trimmed));

        System.out.println("\nWord Frequency:");
        countWordFrequency(trimmed);

        scanner.close();
    }

    public static String removePunctuation(String text) {
        return text.replaceAll("\\p{Punct}", "");
    }

    public static String capitalizeWords(String text) {
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                result.append(Character.toUpperCase(w.charAt(0)))
                      .append(w.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        return result.toString().trim();
    }

    public static String reverseWordOrder(String text) {
        String[] words = text.split("\\s+");
        Collections.reverse(Arrays.asList(words));
        return String.join(" ", words);
    }

    public static void countWordFrequency(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        String[] uniqueWords = new String[words.length];
        int[] counts = new int[words.length];
        int uniqueCount = 0;

        for (String w : words) {
            w = w.replaceAll("\\p{Punct}", "");
            if (w.isEmpty()) continue;

            boolean found = false;
            for (int i = 0; i < uniqueCount; i++) {
                if (uniqueWords[i].equals(w)) {
                    counts[i]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                uniqueWords[uniqueCount] = w;
                counts[uniqueCount] = 1;
                uniqueCount++;
            }
        }

        for (int i = 0; i < uniqueCount; i++) {
            System.out.println(uniqueWords[i] + " → " + counts[i]);
        }
    }
}
