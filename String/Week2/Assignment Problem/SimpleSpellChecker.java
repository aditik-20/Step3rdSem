import java.util.*;

public class SimpleSpellChecker {

    public static List<String> extractWords(String sentence) {
        List<String> words = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (ch == ' ' || ch == '.' || ch == ',' || ch == '!' || ch == '?') {
                if (start < i) {
                    words.add(sentence.substring(start, i));
                }
                start = i + 1;
            }
        }
        if (start < sentence.length()) {
            words.add(sentence.substring(start));
        }
        return words;
    }

    public static int stringDistance(String w1, String w2) {
        int len1 = w1.length();
        int len2 = w2.length();
        int minLen = Math.min(len1, len2);
        int distance = Math.abs(len1 - len2);

        for (int i = 0; i < minLen; i++) {
            if (w1.charAt(i) != w2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    public static String findClosestWord(String word, String[] dictionary) {
        int minDistance = Integer.MAX_VALUE;
        String closest = word;
        for (String dictWord : dictionary) {
            int dist = stringDistance(word.toLowerCase(), dictWord.toLowerCase());
            if (dist < minDistance) {
                minDistance = dist;
                closest = dictWord;
            }
        }
        return (minDistance <= 2) ? closest : word;
    }

    public static void displayResults(List<String> words, String[] dictionary) {
        System.out.printf("%-15s %-15s %-10s %-12s\n", "Word", "Suggestion", "Distance", "Status");
        System.out.println("-------------------------------------------------------------");

        for (String word : words) {
            String suggestion = findClosestWord(word, dictionary);
            int distance = stringDistance(word.toLowerCase(), suggestion.toLowerCase());
            String status = (Arrays.asList(dictionary).contains(word.toLowerCase())) ? "Correct" :
                            (suggestion.equals(word) ? "Misspelled" : "Corrected");

            System.out.printf("%-15s %-15s %-10d %-12s\n", word, suggestion, distance, status);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] dictionary = {"java", "program", "spell", "checker", "simple", "string", "distance", "correct"};
        String sentence = sc.nextLine();
        List<String> words = extractWords(sentence);
        displayResults(words, dictionary);
    }
}
