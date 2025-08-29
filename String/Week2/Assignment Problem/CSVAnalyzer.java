import java.util.*;

public class CSVAnalyzer {
    public static String[][] parseCSV(String input) {
        List<String[]> rows = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == '\"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                fields.add(field.toString());
                field.setLength(0);
            } else if (ch == '\n' && !inQuotes) {
                fields.add(field.toString());
                rows.add(fields.toArray(new String[0]));
                fields.clear();
                field.setLength(0);
            } else {
                field.append(ch);
            }
        }
        if (field.length() > 0) fields.add(field.toString());
        if (!fields.isEmpty()) rows.add(fields.toArray(new String[0]));
        return rows.toArray(new String[0][0]);
    }

    public static String[][] cleanData(String[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != null) {
                    data[i][j] = data[i][j].trim();
                    if (data[i][j].isEmpty()) data[i][j] = "MISSING";
                }
            }
        }
        return data;
    }

    public static void analyzeData(String[][] data) {
        if (data.length < 2) return;
        int cols = data[0].length;
        for (int j = 0; j < cols; j++) {
            boolean numeric = true;
            List<Double> nums = new ArrayList<>();
            Set<String> unique = new HashSet<>();
            for (int i = 1; i < data.length; i++) {
                String val = data[i][j];
                if (val.equals("MISSING")) continue;
                if (isNumeric(val)) nums.add(Double.parseDouble(val));
                else numeric = false;
                unique.add(val);
            }
            if (numeric && !nums.isEmpty()) {
                double min = Collections.min(nums);
                double max = Collections.max(nums);
                double sum = 0;
                for (double n : nums) sum += n;
                double avg = sum / nums.size();
                System.out.printf("Column %s: Min=%.2f, Max=%.2f, Avg=%.2f%n", data[0][j], min, max, avg);
            } else {
                System.out.printf("Column %s: Unique Values=%d%n", data[0][j], unique.size());
            }
        }
    }

    public static boolean isNumeric(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(c >= '0' && c <= '9') && c != '.') return false;
        }
        return true;
    }

    public static void formatTable(String[][] data) {
        int cols = data[0].length;
        int[] colWidths = new int[cols];
        for (int j = 0; j < cols; j++) {
            int max = 0;
            for (int i = 0; i < data.length; i++) {
                if (data[i][j] != null) max = Math.max(max, data[i][j].length());
            }
            colWidths[j] = max + 2;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%-" + colWidths[j] + "s", data[i][j]));
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void summaryReport(String[][] data) {
        int total = data.length - 1;
        int missing = 0;
        for (int i = 1; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j].equals("MISSING")) missing++;
            }
        }
        int fields = (data.length - 1) * data[0].length;
        double completeness = 100.0 * (fields - missing) / fields;
        System.out.println("Summary Report:");
        System.out.println("Total Records: " + total);
        System.out.println("Missing Fields: " + missing);
        System.out.printf("Data Completeness: %.2f%%%n", completeness);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter CSV data (end with empty line):");
        StringBuilder input = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.isEmpty()) break;
            input.append(line).append("\n");
        }

        String[][] parsed = parseCSV(input.toString());
        parsed = cleanData(parsed);
        formatTable(parsed);
        analyzeData(parsed);
        summaryReport(parsed);
    }
}
