import java.util.Scanner;

public class ShortestLongestWord {

    public static int findLength(String str) {
        int count = 0;
        try {
            while (true) {
                str.charAt(count);
                count++;
            }
        } catch (Exception e) {
            return count;
        }
    }

    public static String[] splitText(String text) {
        int len = findLength(text);
        int spaceCount = 0;
        for (int i = 0; i < len; i++) {
            if (text.charAt(i) == ' ') {
                spaceCount++;
            }
        }

        int[] spaceIndexes = new int[spaceCount + 2];
        spaceIndexes[0] = -1;
        int index = 1;

        for (int i = 0; i < len; i++) {
            if (text.charAt(i) == ' ') {
                spaceIndexes[index++] = i;
            }
        }

        spaceIndexes[index] = len;

        String[] words = new String[spaceCount + 1];
        for (int i = 0; i < words.length; i++) {
            int start = spaceIndexes[i] + 1;
            int end = spaceIndexes[i + 1];
            String word = "";
            for (int j = start; j < end; j++) {
                word += text.charAt(j);
            }
            words[i] = word;
        }

        return words;
    }

    public static String[][] getWordLengthArray(String[] words) {
        String[][] result = new String[words.length][2];
        for (int i = 0; i < words.length; i++) {
            result[i][0] = words[i];
            result[i][1] = String.valueOf(findLength(words[i]));
        }
        return result;
    }

    public static int[] findShortestAndLongest(String[][] wordLengthArray) {
        int minIndex = 0;
        int maxIndex = 0;
        int minLen = Integer.parseInt(wordLengthArray[0][1]);
        int maxLen = Integer.parseInt(wordLengthArray[0][1]);

        for (int i = 1; i < wordLengthArray.length; i++) {
            int currentLen = Integer.parseInt(wordLengthArray[i][1]);
            if (currentLen < minLen) {
                minLen = currentLen;
                minIndex = i;
            }
            if (currentLen > maxLen) {
                maxLen = currentLen;
                maxIndex = i;
            }
        }

        return new int[]{minIndex, maxIndex};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] words = splitText(input);
        String[][] wordLengthArray = getWordLengthArray(words);
        int[] resultIndexes = findShortestAndLongest(wordLengthArray);

        String shortestWord = wordLengthArray[resultIndexes[0]][0];
        int shortestLength = Integer.parseInt(wordLengthArray[resultIndexes[0]][1]);

        String longestWord = wordLengthArray[resultIndexes[1]][0];
        int longestLength = Integer.parseInt(wordLengthArray[resultIndexes[1]][1]);

        System.out.println("Shortest word: " + shortestWord + " (Length: " + shortestLength + ")");
        System.out.println("Longest word: " + longestWord + " (Length: " + longestLength + ")");
    }
}
