import java.util.*;

public class TextProcessor {
    public static String cleanInput(String input) {
        input = input.trim().replaceAll("\\s+", " ");
        String[] words = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1).toLowerCase())
              .append(" ");
        }
        return sb.toString().trim();
    }

    public static void analyzeText(String text) {
        String cleanText = text.replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = cleanText.split("\\s+");

        int wordCount = words.length;
        int sentenceCount = text.split("[.!?]").length;
        int charCount = text.replaceAll("\\s", "").length();

        String longestWord = "";
        Map<Character, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
            for (char c : word.toCharArray()) {
                freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
            }
        }

        char mostCommonChar = ' ';
        int maxFreq = 0;
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                mostCommonChar = entry.getKey();
            }
        }

        System.out.println("Word count: " + wordCount);
        System.out.println("Sentence count: " + sentenceCount);
        System.out.println("Character count (no spaces): " + charCount);
        System.out.println("Longest word: " + longestWord);
        System.out.println("Most common character: " + mostCommonChar);
    }

    public static String[] getWordsSorted(String text) {
        String cleanText = text.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
        String[] words = cleanText.split("\\s+");
        Arrays.sort(words);
        return words;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TEXT PROCESSOR ===");
        System.out.print("Enter a paragraph: ");
        String input = scanner.nextLine();

        String cleaned = cleanInput(input);
        System.out.println("\nCleaned Text: " + cleaned);

        System.out.println("\n--- Analysis ---");
        analyzeText(cleaned);

        System.out.println("\n--- Words Sorted ---");
        String[] sortedWords = getWordsSorted(cleaned);
        for (String word : sortedWords) {
            System.out.println(word);
        }

        System.out.print("\nEnter a word to search: ");
        String search = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (String word : sortedWords) {
            if (word.equals(search)) {
                found = true;
                break;
            }
        }
        System.out.println("Word '" + search + "' found: " + found);

        scanner.close();
    }
}
