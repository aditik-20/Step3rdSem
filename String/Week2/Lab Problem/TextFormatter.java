import java.util.*;

public class TextFormatter {

    // Split words manually using charAt and substring
    public static List<String> splitWords(String text) {
        List<String> words = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                if (i > start) {
                    words.add(text.substring(start, i));
                }
                start = i + 1;
            }
        }
        if (start < text.length()) {
            words.add(text.substring(start));
        }
        return words;
    }

    // Justify text using StringBuilder
    public static List<String> justifyText(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        int i = 0;
        while (i < words.size()) {
            int lineLen = words.get(i).length();
            int j = i + 1;

            while (j < words.size() && lineLen + 1 + words.get(j).length() <= width) {
                lineLen += 1 + words.get(j).length();
                j++;
            }

            int gaps = j - i - 1;
            StringBuilder sb = new StringBuilder();

            if (j == words.size() || gaps == 0) { // last line or single word
                for (int k = i; k < j; k++) {
                    sb.append(words.get(k));
                    if (k < j - 1) sb.append(" ");
                }
                while (sb.length() < width) sb.append(" "); // pad right
            } else {
                int totalSpaces = width - (lineLen - gaps);
                int spacePerGap = totalSpaces / gaps;
                int extraSpaces = totalSpaces % gaps;

                for (int k = i; k < j; k++) {
                    sb.append(words.get(k));
                    if (k < j - 1) {
                        for (int s = 0; s < spacePerGap; s++) sb.append(" ");
                        if (extraSpaces > 0) {
                            sb.append(" ");
                            extraSpaces--;
                        }
                    }
                }
            }

            lines.add(sb.toString());
            i = j;
        }
        return lines;
    }

    // Center-align text
    public static List<String> centerAlign(List<String> words, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (sb.length() + word.length() + 1 > width) {
                String line = sb.toString().trim();
                int padding = (width - line.length()) / 2;
                StringBuilder centered = new StringBuilder();
                for (int i = 0; i < padding; i++) centered.append(" ");
                centered.append(line);
                while (centered.length() < width) centered.append(" ");
                lines.add(centered.toString());
                sb.setLength(0);
            }
            sb.append(word).append(" ");
        }
        if (sb.length() > 0) {
            String line = sb.toString().trim();
            int padding = (width - line.length()) / 2;
            StringBuilder centered = new StringBuilder();
            for (int i = 0; i < padding; i++) centered.append(" ");
            centered.append(line);
            while (centered.length() < width) centered.append(" ");
            lines.add(centered.toString());
        }
        return lines;
    }

    // Performance comparison
    public static void comparePerformance(List<String> words, int width) {
        long start1 = System.nanoTime();
        justifyText(words, width);
        long end1 = System.nanoTime();
        long sbTime = end1 - start1;

        long start2 = System.nanoTime();
        // Same with String concatenation (inefficient)
        List<String> lines = new ArrayList<>();
        int i = 0;
        while (i < words.size()) {
            String line = "";
            while (i < words.size() && line.length() + words.get(i).length() + 1 <= width) {
                line += words.get(i) + " ";
                i++;
            }
            lines.add(line.trim());
        }
        long end2 = System.nanoTime();
        long stringTime = end2 - start2;

        System.out.println("\n--- Performance Analysis ---");
        System.out.println("StringBuilder Time: " + sbTime + " ns");
        System.out.println("String Concatenation Time: " + stringTime + " ns");
        System.out.println("Faster Method: " + (sbTime < stringTime ? "StringBuilder ✅" : "String Concatenation ❌ (rare)"));
    }

    // Display with line numbers and char count
    public static void displayLines(List<String> lines, String label) {
        System.out.println("\n--- " + label + " ---");
        for (int i = 0; i < lines.size(); i++) {
            System.out.printf("Line %2d (%2d chars): %s\n", i + 1, lines.get(i).length(), lines.get(i));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.print("Enter line width: ");
        int width = sc.nextInt();

        List<String> words = splitWords(text);

        List<String> justified = justifyText(words, width);
        List<String> centered = centerAlign(words, width);

        System.out.println("\nOriginal Text:\n" + text);

        displayLines(justified, "Justified Text");
        displayLines(centered, "Center-Aligned Text");

        comparePerformance(words, width);
    }
}
