import java.util.*;

public class StringPerformanceAnalysis {

    public static long[] testStringConcat(int iterations, String sample) {
        long start = System.currentTimeMillis();
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += sample; 
        }
        long end = System.currentTimeMillis();
        return new long[]{end - start, result.length()};
    }

    public static long[] testStringBuilder(int iterations, String sample) {
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(sample);
        }
        long end = System.currentTimeMillis();
        return new long[]{end - start, sb.length()};
    }

    public static long[] testStringBuffer(int iterations, String sample) {
        long start = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append(sample);
        }
        long end = System.currentTimeMillis();
        return new long[]{end - start, sb.length()};
    }

    public static void displayResults(long[] stringResult, long[] builderResult, long[] bufferResult) {
        System.out.printf("%-15s %-20s %-20s\n", "Method", "Time (ms)", "Final Length");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-15s %-20d %-20d\n", "String", stringResult[0], stringResult[1]);
        System.out.printf("%-15s %-20d %-20d\n", "StringBuilder", builderResult[0], builderResult[1]);
        System.out.printf("%-15s %-20d %-20d\n", "StringBuffer", bufferResult[0], bufferResult[1]);
        System.out.println("-------------------------------------------------------------");

        String fastest = (stringResult[0] < builderResult[0] && stringResult[0] < bufferResult[0]) ? "String"
                        : (builderResult[0] < bufferResult[0]) ? "StringBuilder" : "StringBuffer";
        System.out.println("Fastest Method: " + fastest);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of iterations (e.g., 1000, 10000, 100000): ");
        int iterations = sc.nextInt();

        String sample = "abc";

        long[] stringResult = testStringConcat(iterations, sample);
        long[] builderResult = testStringBuilder(iterations, sample);
        long[] bufferResult = testStringBuffer(iterations, sample);

        displayResults(stringResult, builderResult, bufferResult);
    }
}
