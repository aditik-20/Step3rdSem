import java.util.*;

public class TextCompression {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        char[] chars = getUniqueChars(text);
        int[] freq = getFrequencies(text, chars);
        String[][] mapping = generateCodes(chars, freq);
        String compressed = compress(text, mapping);
        String decompressed = decompress(compressed, mapping);
        displayAnalysis(text, chars, freq, mapping, compressed, decompressed);
    }

    static char[] getUniqueChars(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (sb.indexOf(String.valueOf(c)) == -1) sb.append(c);
        }
        char[] arr = new char[sb.length()];
        for (int i = 0; i < sb.length(); i++) arr[i] = sb.charAt(i);
        return arr;
    }

    static int[] getFrequencies(String text, char[] chars) {
        int[] freq = new int[chars.length];
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == c) freq[j]++;
            }
        }
        return freq;
    }

    static String[][] generateCodes(char[] chars, int[] freq) {
        String[][] map = new String[chars.length][2];
        Integer[] idx = new Integer[chars.length];
        for (int i = 0; i < chars.length; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> freq[b] - freq[a]);
        for (int i = 0; i < idx.length; i++) {
            map[idx[i]][0] = String.valueOf(chars[idx[i]]);
            map[idx[i]][1] = String.valueOf(i);
        }
        return map;
    }

    static String compress(String text, String[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            String c = String.valueOf(text.charAt(i));
            for (String[] m : map) {
                if (m[0].equals(c)) {
                    sb.append(m[1]);
                    break;
                }
            }
        }
        return sb.toString();
    }

    static String decompress(String compressed, String[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < compressed.length(); i++) {
            String c = String.valueOf(compressed.charAt(i));
            for (String[] m : map) {
                if (m[1].equals(c)) {
                    sb.append(m[0]);
                    break;
                }
            }
        }
        return sb.toString();
    }

    static void displayAnalysis(String original, char[] chars, int[] freq, String[][] map,
                                String compressed, String decompressed) {
        System.out.println("Character Frequency Table:");
        for (int i = 0; i < chars.length; i++)
            System.out.println(chars[i] + " -> " + freq[i]);
        System.out.println("\nCompression Mapping:");
        for (String[] m : map)
            System.out.println(m[0] + " -> " + m[1]);
        System.out.println("\nOriginal Text: " + original);
        System.out.println("Compressed Text: " + compressed);
        System.out.println("Decompressed Text: " + decompressed);
        double ratio = ((double) compressed.length() / original.length()) * 100;
        System.out.println("Compression Efficiency: " + (100 - ratio) + "%");
    }
}
