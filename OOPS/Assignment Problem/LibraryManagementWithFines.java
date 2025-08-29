import java.util.*;

class BookL {
    String bookId;
    String title;
    String author;
    String isbn;
    String category;
    boolean isIssued;
    String issueDate;
    String dueDate;
    static int totalBooks = 0;

    public BookL(String title, String author, String isbn, String category) {
        this.bookId = String.format("LB%04d", ++totalBooks);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.isIssued = false;
    }
}

class MemberL {
    String memberId;
    String memberName;
    String memberType;
    BookL[] booksIssued;
    double totalFines;
    String membershipDate;
    static int totalMembers = 0;
    static String libraryName = "City Library";
    static double finePerDay = 2.0;
    static int maxBooksAllowed = 3;

    public MemberL(String memberName, String memberType, String membershipDate) {
        this.memberId = String.format("LM%04d", ++totalMembers);
        this.memberName = memberName;
        this.memberType = memberType;
        this.membershipDate = membershipDate;
        int cap = memberType.equalsIgnoreCase("Faculty") ? 10 : memberType.equalsIgnoreCase("Student") ? 5 : 3;
        this.booksIssued = new BookL[cap];
        this.totalFines = 0;
    }

    public boolean issueBook(BookL b, String issueDate, String dueDate) {
        if (b == null || b.isIssued) return false;
        int cnt = 0;
        for (BookL x : booksIssued) if (x != null) cnt++;
        if (cnt >= booksIssued.length) return false;
        for (int i = 0; i < booksIssued.length; i++) if (booksIssued[i] == null) { booksIssued[i] = b; break; }
        b.isIssued = true; b.issueDate = issueDate; b.dueDate = dueDate;
        return true;
    }

    public boolean returnBook(String bookId, String returnDate) {
        for (int i = 0; i < booksIssued.length; i++) {
            BookL b = booksIssued[i];
            if (b != null && b.bookId.equals(bookId)) {
                int overdue = daysBetween(b.dueDate, returnDate);
                if (overdue > 0) totalFines += overdue * finePerDay;
                b.isIssued = false; b.issueDate = null; b.dueDate = null;
                booksIssued[i] = null;
                return true;
            }
        }
        return false;
    }

    public boolean renewBook(String bookId, String newDueDate) {
        for (BookL b : booksIssued) if (b != null && b.bookId.equals(bookId)) { b.dueDate = newDueDate; return true; }
        return false;
    }

    public BookL[] searchBooks(BookL[] all, String keyword) {
        BookL[] tmp = new BookL[all.length];
        int k = 0;
        for (BookL b : all) if (b != null && (b.title.toLowerCase().contains(keyword.toLowerCase()) || b.author.toLowerCase().contains(keyword.toLowerCase()))) tmp[k++] = b;
        BookL[] res = new BookL[k];
        for (int i = 0; i < k; i++) res[i] = tmp[i];
        return res;
    }

    public boolean reserveBook(BookL b) {
        return !b.isIssued;
    }

    private int daysBetween(String d1, String d2) {
        try {
            String[] a = d1.split("-");
            String[] b = d2.split("-");
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.set(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
            String[] bb = b;
            c2.set(Integer.parseInt(bb[0]), Integer.parseInt(bb[1]), Integer.parseInt(bb[2]));
            long diff = (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000 * 60 * 60 * 24);
            return (int) diff;
        } catch (Exception e) { return 0; }
    }

    public static void generateLibraryReport(BookL[] books, MemberL[] members) {
        int issued = 0;
        for (BookL b : books) if (b != null && b.isIssued) issued++;
        double totalF = 0;
        for (MemberL m : members) if (m != null) totalF += m.totalFines;
        System.out.println("Library: " + libraryName);
        System.out.println("Total Books: " + BookL.totalBooks + " | Total Members: " + totalMembers);
        System.out.println("Issued Books: " + issued + " | Total Fines: " + totalF);
    }

    public static BookL[] getOverdueBooks(BookL[] books, String today) {
        BookL[] tmp = new BookL[books.length];
        int k = 0;
        for (BookL b : books) if (b != null && b.isIssued) {
            String dd = b.dueDate;
            String[] a = dd.split("-");
            String[] t = today.split("-");
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.set(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
            c2.set(Integer.parseInt(t[0]), Integer.parseInt(t[1]), Integer.parseInt(t[2]));
            if (c2.after(c1)) tmp[k++] = b;
        }
        BookL[] res = new BookL[k];
        for (int i = 0; i < k; i++) res[i] = tmp[i];
        return res;
    }

    public static String[] getMostPopularBooks(BookL[] books) {
        String[] ids = new String[Math.min(5, books.length)];
        int k = 0;
        for (BookL b : books) if (b != null && k < ids.length) ids[k++] = b.bookId;
        return ids;
    }
}

public class LibraryManagementWithFines {
    public static void main(String[] args) {
        BookL[] books = {
            new BookL("Java Basics","Gosling","ISBN1","CS"),
            new BookL("C++ Primer","Bjarne","ISBN2","CS"),
            new BookL("Data Structures","Weiss","ISBN3","CS"),
            new BookL("Algorithms","CLRS","ISBN4","CS")
        };

        MemberL s = new MemberL("Aditi","Student","2024-01-01");
        MemberL f = new MemberL("Dr. Rao","Faculty","2023-06-10");
        MemberL g = new MemberL("Vikram","General","2025-01-05");

        s.issueBook(books[0],"2025-08-01","2025-08-10");
        s.issueBook(books[1],"2025-08-01","2025-08-12");
        f.issueBook(books[2],"2025-08-05","2025-09-05");

        s.returnBook(books[0].bookId,"2025-08-15");
        s.renewBook(books[1].bookId,"2025-08-20");

        BookL[] overdue = MemberL.getOverdueBooks(books,"2025-08-16");
        System.out.println("Overdue Books:");
        for (BookL b : overdue) System.out.println(b.bookId + " " + b.title);

        MemberL.generateLibraryReport(books, new MemberL[]{s,f,g});
        System.out.println("Total Fines of " + s.memberName + ": " + s.totalFines);
    }
}
