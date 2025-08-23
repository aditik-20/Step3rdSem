import java.util.*;

public class FindReplaceSubstring {

    public static List<Integer> findOccurrences(String text, String findStr) {
        List<Integer> positions = new ArrayList<>();
        int index = text.indexOf(findStr);
        while (index != -1) {
            positions.add(index);
            index = text.indexOf(findStr, index + 1);
        }
        return positions;
    }

    public static String manualReplace(String text, String findStr, String replaceStr) {
        StringBuilder result = new StringBuilder();
        int i = 0, len = findStr.length();

        while (i < text.length()) {
            if (i <= text.length() - len && text.substring(i, i + len).equals(findStr)) {
                result.append(replaceStr);
                i += len;
            } else {
                result.append(text.charAt(i));
                i++;
            }
        }
        return result.toString();
    }

    public static boolean compareWithBuiltIn(String text, String findStr, String replaceStr, String manualResult) {
        String builtInResult = text.replace(findStr, replaceStr);
        return builtInResult.equals(manualResult);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the main text: ");
        String text = sc.nextLine();

        System.out.print("Enter substring to find: ");
        String findStr = sc.nextLine();

        System.out.print("Enter replacement substring: ");
        String replaceStr = sc.nextLine();

        List<Integer> positions = findOccurrences(text, findStr);
        System.out.println("Occurrences of \"" + findStr + "\" found at positions: " + positions);

        String manualResult = manualReplace(text, findStr, replaceStr);
        System.out.println("Manual Replacement Result: " + manualResult);

        String builtInResult = text.replace(findStr, replaceStr);
        System.out.println("Built-in Replacement Result: " + builtInResult);

        boolean isSame = compareWithBuiltIn(text, findStr, replaceStr, manualResult);
        System.out.println("Does manual match built-in? " + isSame);
    }
}
