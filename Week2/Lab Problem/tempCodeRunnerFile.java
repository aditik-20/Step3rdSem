import java.util.*;

public class EmailAnalysis {

    public static boolean isValidEmail(String email) {
        int atPos = email.indexOf('@');
        int lastAtPos = email.lastIndexOf('@');
        if (atPos == -1 || atPos != lastAtPos) return false;

        int dotPos = email.indexOf('.', atPos);
        if (dotPos == -1) return false;

        String username = email.substring(0, atPos);
        String domain = email.substring(atPos + 1);

        if (username.isEmpty() || domain.isEmpty()) return false;
        return true;
    }

    public static Map<String, String> extractParts(String email) {
        Map<String, String> parts = new HashMap<>();
        int atPos = email.indexOf('@');
        String username = email.substring(0, atPos);
        String domain = email.substring(atPos + 1);

        int dotPos = domain.lastIndexOf('.');
        String domainName = (dotPos != -1) ? domain.substring(0, dotPos) : domain;
        String extension = (dotPos != -1) ? domain.substring(dotPos + 1) : "";

        parts.put("username", username);
        parts.put("domain", domain);
        parts.put("domainName", domainName);
        parts.put("extension", extension);

        return parts;
    }

    public static void analyzeStatistics(List<String> emails, List<Boolean> validity, List<Map<String, String>> partsList) {
        int validCount = 0, invalidCount = 0, totalUsernameLen = 0;
        Map<String, Integer> domainFrequency = new HashMap<>();

        for (int i = 0; i < emails.size(); i++) {
            if (validity.get(i)) {
                validCount++;
                String username = partsList.get(i).get("username");
                totalUsernameLen += username.length();

                String domain = partsList.get(i).get("domain");
                domainFrequency.put(domain, domainFrequency.getOrDefault(domain, 0) + 1);
            } else {
                invalidCount++;
            }
        }

        String mostCommonDomain = "N/A";
        int maxCount = 0;
        for (String d : domainFrequency.keySet()) {
            if (domainFrequency.get(d) > maxCount) {
                maxCount = domainFrequency.get(d);
                mostCommonDomain = d;
            }
        }

        double avgUsernameLen = (validCount > 0) ? (double) totalUsernameLen / validCount : 0;

        System.out.println("\n------------------- Analysis Summary -------------------");
        System.out.println("Total Emails     : " + emails.size());
        System.out.println("Valid Emails     : " + validCount);
        System.out.println("Invalid Emails   : " + invalidCount);
        System.out.println("Most Common Domain : " + mostCommonDomain);
        System.out.printf("Average Username Length : %.2f\n", avgUsernameLen);
    }

    public static void displayResults(List<String> emails, List<Boolean> validity, List<Map<String, String>> partsList) {
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.printf("%-25s %-15s %-25s %-20s %-10s %-10s\n", "Email", "Username", "Domain", "Domain Name", "Extension", "Valid");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (int i = 0; i < emails.size(); i++) {
            String email = emails.get(i);
            if (validity.get(i)) {
                Map<String, String> parts = partsList.get(i);
                System.out.printf("%-25s %-15s %-25s %-20s %-10s %-10s\n",
                        email,
                        parts.get("username"),
                        parts.get("domain"),
                        parts.get("domainName"),
                        parts.get("extension"),
                        "Yes");
            } else {
                System.out.printf("%-25s %-15s %-25s %-20s %-10s %-10s\n",
                        email, "-", "-", "-", "-", "No");
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> emails = new ArrayList<>();

        System.out.print("Enter number of emails: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter email " + (i + 1) + ": ");
            emails.add(sc.nextLine());
        }

        List<Boolean> validity = new ArrayList<>();
        List<Map<String, String>> partsList = new ArrayList<>();

        for (String email : emails) {
            boolean valid = isValidEmail(email);
            validity.add(valid);
            if (valid) {
                partsList.add(extractParts(email));
            } else {
                partsList.add(new HashMap<>());
            }
        }

        displayResults(emails, validity, partsList);
        analyzeStatistics(emails, validity, partsList);
    }
}
