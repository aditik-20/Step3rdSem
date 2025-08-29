import java.util.*;
import java.text.SimpleDateFormat;

class FileInfo {
    String originalName;
    String name;
    String extension;
    String category;
    String newName;
    String subCategory;
    boolean valid;
}

public class FileOrganizer {
    static Scanner sc = new Scanner(System.in);

    static FileInfo extractFileInfo(String filename) {
        FileInfo info = new FileInfo();
        int dot = filename.lastIndexOf(".");
        if (dot == -1 || dot == filename.length() - 1) {
            info.valid = false;
            info.originalName = filename;
            info.name = filename;
            info.extension = "";
            return info;
        }
        info.originalName = filename;
        info.name = filename.substring(0, dot);
        info.extension = filename.substring(dot + 1);
        info.valid = true;
        return info;
    }

    static void categorize(FileInfo file) {
        String ext = file.extension.toLowerCase();
        if (ext.equals("txt") || ext.equals("doc")) file.category = "Document";
        else if (ext.equals("jpg") || ext.equals("png")) file.category = "Image";
        else if (ext.equals("mp3") || ext.equals("wav")) file.category = "Audio";
        else if (ext.equals("mp4") || ext.equals("mkv")) file.category = "Video";
        else file.category = "Unknown";
    }

    static void generateNewName(FileInfo file, Map<String, Integer> counters) {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String base = file.category + "_" + date;
        int count = counters.getOrDefault(base, 0) + 1;
        counters.put(base, count);
        file.newName = base + "_" + count + "." + file.extension;
    }

    static void analyzeContent(FileInfo file) {
        if (file.category.equals("Document")) {
            String lower = file.name.toLowerCase();
            if (lower.contains("resume")) file.subCategory = "Resume";
            else if (lower.contains("report")) file.subCategory = "Report";
            else if (lower.contains("code")) file.subCategory = "Code";
            else file.subCategory = "General";
        } else file.subCategory = "-";
    }

    static void report(List<FileInfo> files) {
        System.out.printf("%-20s %-10s %-20s %-15s\n", "Original Name", "Category", "New Name", "SubCategory");
        for (FileInfo f : files) {
            System.out.printf("%-20s %-10s %-20s %-15s\n", f.originalName, f.category, f.newName, f.subCategory);
        }
        Map<String, Integer> counts = new HashMap<>();
        for (FileInfo f : files) counts.put(f.category, counts.getOrDefault(f.category, 0) + 1);
        System.out.println("\nCategory Counts:");
        for (String k : counts.keySet()) System.out.println(k + ": " + counts.get(k));
    }

    static void batchRename(List<FileInfo> files) {
        System.out.println("\nBatch Rename Commands:");
        for (FileInfo f : files) {
            if (!f.originalName.equals(f.newName))
                System.out.println("rename " + f.originalName + " -> " + f.newName);
        }
    }

    public static void main(String[] args) {
        List<FileInfo> files = new ArrayList<>();
        System.out.println("Enter file names (end with empty line):");
        while (true) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) break;
            FileInfo info = extractFileInfo(input);
            categorize(info);
            files.add(info);
        }
        Map<String, Integer> counters = new HashMap<>();
        for (FileInfo f : files) {
            generateNewName(f, counters);
            analyzeContent(f);
        }
        report(files);
        batchRename(files);
    }
}
