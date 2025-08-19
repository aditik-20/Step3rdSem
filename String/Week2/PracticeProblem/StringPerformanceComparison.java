public class StringPerformanceComparison {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        String result1 = concatenateWithString(1000);
        long endTime = System.nanoTime();
        System.out.println("String concatenation time: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        String result2 = concatenateWithStringBuilder(1000);
        endTime = System.nanoTime();
        System.out.println("StringBuilder concatenation time: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        String result3 = concatenateWithStringBuffer(1000);
        endTime = System.nanoTime();
        System.out.println("StringBuffer concatenation time: " + (endTime - startTime) + " ns");

        System.out.println("\n=== STRINGBUILDER METHODS DEMO ===");
        demonstrateStringBuilderMethods();

        System.out.println("\n=== THREAD SAFETY DEMO ===");
        demonstrateThreadSafety();

        System.out.println("\n=== STRING COMPARISON METHODS ===");
        compareStringComparisonMethods();

        System.out.println("\n=== MEMORY EFFICIENCY DEMO ===");
        demonstrateMemoryEfficiency();
    }

    public static String concatenateWithString(int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "Java " + i + " ";
        }
        return result;
    }

    public static String concatenateWithStringBuilder(int iterations) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("Java ").append(i).append(" ");
        }
        return sb.toString();
    }

    public static String concatenateWithStringBuffer(int iterations) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("Java ").append(i).append(" ");
        }
        return sb.toString();
    }

    public static void demonstrateStringBuilderMethods() {
        StringBuilder sb = new StringBuilder("Hello World");
        System.out.println("Original: " + sb);

        sb.append("!!!");
        System.out.println("append(): " + sb);

        sb.insert(5, " Java");
        System.out.println("insert(): " + sb);

        sb.delete(5, 10);
        System.out.println("delete(): " + sb);

        sb.deleteCharAt(0);
        System.out.println("deleteCharAt(): " + sb);

        sb.reverse();
        System.out.println("reverse(): " + sb);
        sb.reverse();

        sb.replace(0, 5, "Hi");
        System.out.println("replace(): " + sb);

        sb.setCharAt(0, 'Y');
        System.out.println("setCharAt(): " + sb);

        System.out.println("capacity(): " + sb.capacity());
        sb.ensureCapacity(100);
        System.out.println("ensureCapacity(100): " + sb.capacity());

        sb.trimToSize();
        System.out.println("trimToSize(): " + sb.capacity());
    }

    public static void demonstrateThreadSafety() {
        StringBuffer safeBuffer = new StringBuffer("Safe");
        StringBuilder unsafeBuilder = new StringBuilder("Unsafe");

        Runnable bufferTask = () -> {
            for (int i = 0; i < 5; i++) {
                safeBuffer.append("X");
            }
        };

        Runnable builderTask = () -> {
            for (int i = 0; i < 5; i++) {
                unsafeBuilder.append("X");
            }
        };

        Thread t1 = new Thread(bufferTask);
        Thread t2 = new Thread(bufferTask);
        t1.start();
        t2.start();

        Thread t3 = new Thread(builderTask);
        Thread t4 = new Thread(builderTask);
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("StringBuffer (thread-safe): " + safeBuffer);
        System.out.println("StringBuilder (not thread-safe): " + unsafeBuilder);
    }

    public static void compareStringComparisonMethods() {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");

        System.out.println("== operator (str1 == str2): " + (str1 == str2));  // true (same pool reference)
        System.out.println("== operator (str1 == str3): " + (str1 == str3));  // false (different object)

        System.out.println("equals(): " + str1.equals(str3)); // true (content same)
        System.out.println("equalsIgnoreCase(): " + str1.equalsIgnoreCase("hello")); // true

        System.out.println("compareTo(): " + str1.compareTo(str3)); // 0
        System.out.println("compareToIgnoreCase(): " + str1.compareToIgnoreCase("hello")); // 0
    }

    public static void demonstrateMemoryEfficiency() {
        String a = "Hello";
        String b = "Hello"; // refers to same object in string pool
        String c = new String("Hello"); // new object

        System.out.println("a == b (string pool): " + (a == b));
        System.out.println("a == c (new object): " + (a == c));

        StringBuilder sb = new StringBuilder();
        System.out.println("Initial capacity: " + sb.capacity());

        for (int i = 0; i < 50; i++) {
            sb.append("Java");
        }
        System.out.println("Capacity after appending: " + sb.capacity());

        sb.trimToSize();
        System.out.println("Capacity after trimToSize(): " + sb.capacity());
    }
}
