package String.PracticeProblem2;
public class StringBuiltInMethods {

    public static void main(String[] args) {
        String sampleText = " Java Programming is Fun and Challenging! ";

        System.out.println("Original string length: " + sampleText.length());

        String trimmedText = sampleText.trim();
        System.out.println("Trimmed string: \"" + trimmedText + "\"");
        System.out.println("Trimmed string length: " + trimmedText.length());

        System.out.println("Character at index 5: " + sampleText.charAt(5));

        int startIndex = sampleText.indexOf("Programming");
        String subString = sampleText.substring(startIndex, startIndex + "Programming".length());
        System.out.println("Extracted substring: \"" + subString + "\"");

        System.out.println("Index of 'Fun': " + sampleText.indexOf("Fun"));

        System.out.println("Contains 'Java' (case-sensitive): " + sampleText.contains("Java"));

        System.out.println("Starts with 'Java' (after trimming): " + trimmedText.startsWith("Java"));

        System.out.println("Ends with '!': " + sampleText.endsWith("!"));
        System.out.println("Uppercase string: " + sampleText.toUpperCase());
        System.out.println("Lowercase string: " + sampleText.toLowerCase());
        System.out.println("Number of vowels in trimmed text: " + countVowels(trimmedText));
        System.out.print("Occurrences of 'a' in trimmed text: ");
        findAllOccurrences(trimmedText, 'a');
    }

    public static int countVowels(String text) {
        int count = 0;
        String lowerCaseText = text.toLowerCase();
        for (int i = 0; i < lowerCaseText.length(); i++) {
            char ch = lowerCaseText.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }
        return count;
    }

    public static void findAllOccurrences(String text, char target) {
        StringBuilder positions = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == target) {
                positions.append(i).append(" ");
            }
        }
        if (positions.length() == 0) {
            System.out.println("No occurrences found.");
        } else {
            System.out.println(positions.toString().trim());
        }
    }
}
