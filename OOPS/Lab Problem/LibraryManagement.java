class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;
    private static int totalBooks = 0;
    private static int availableBooks = 0;

    public Book(String title, String author) {
        this.bookId = generateBookId();
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        totalBooks++;
        availableBooks++;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getBookId() {
        return bookId;
    }

    public void issueBook() {
        if (isAvailable) {
            isAvailable = false;
            availableBooks--;
            System.out.println("Book " + title + " issued successfully.");
        } else {
            System.out.println("Book " + title + " is not available.");
        }
    }

    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            availableBooks++;
            System.out.println("Book " + title + " returned successfully.");
        } else {
            System.out.println("Book " + title + " was not issued.");
        }
    }

    public void displayBookInfo() {
        System.out.println("---------------------------------");
        System.out.println("Book ID     : " + bookId);
        System.out.println("Title       : " + title);
        System.out.println("Author      : " + author);
        System.out.println("Available   : " + (isAvailable ? "Yes" : "No"));
        System.out.println("---------------------------------");
    }

    private static String generateBookId() {
        return String.format("B%03d", totalBooks + 1);
    }

    public static int getTotalBooks() {
        return totalBooks;
    }

    public static int getAvailableBooks() {
        return availableBooks;
    }
}

class Member {
    private String memberId;
    private String memberName;
    private String[] booksIssued;
    private int bookCount;
    private static int totalMembers = 0;

    public Member(String memberName) {
        this.memberId = generateMemberId();
        this.memberName = memberName;
        this.booksIssued = new String[5];
        this.bookCount = 0;
        totalMembers++;
    }

    public void borrowBook(Book book) {
        if (book.isAvailable() && bookCount < booksIssued.length) {
            book.issueBook();
            booksIssued[bookCount] = book.getBookId();
            bookCount++;
            System.out.println(memberName + " borrowed book " + book.getBookId());
        } else {
            System.out.println(memberName + " cannot borrow book " + book.getBookId());
        }
    }

    public void returnBook(String bookId, Book[] books) {
        int index = -1;
        for (int i = 0; i < bookCount; i++) {
            if (booksIssued[i].equals(bookId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (Book book : books) {
                if (book.getBookId().equals(bookId)) {
                    book.returnBook();
                    break;
                }
            }
            for (int i = index; i < bookCount - 1; i++) {
                booksIssued[i] = booksIssued[i + 1];
            }
            booksIssued[bookCount - 1] = null;
            bookCount--;
            System.out.println(memberName + " returned book " + bookId);
        } else {
            System.out.println("Book ID " + bookId + " not found in " + memberName + "'s account.");
        }
    }

    public void displayMemberInfo() {
        System.out.println("=================================");
        System.out.println("Member ID   : " + memberId);
        System.out.println("Name        : " + memberName);
        System.out.print("Books Issued: ");
        if (bookCount == 0) {
            System.out.println("None");
        } else {
            for (int i = 0; i < bookCount; i++) {
                System.out.print(booksIssued[i] + " ");
            }
            System.out.println();
        }
        System.out.println("=================================");
    }

    private static String generateMemberId() {
        return String.format("M%03d", totalMembers + 1);
    }

    public static int getTotalMembers() {
        return totalMembers;
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Book[] books = new Book[3];
        books[0] = new Book("Java Programming", "James Gosling");
        books[1] = new Book("C++ Programming", "Bjarne Stroustrup");
        books[2] = new Book("Python Programming", "Guido van Rossum");

        Member[] members = new Member[2];
        members[0] = new Member("Alice");
        members[1] = new Member("Bob");

        members[0].borrowBook(books[0]);
        members[0].borrowBook(books[1]);
        members[1].borrowBook(books[0]);

        members[0].displayMemberInfo();
        members[1].displayMemberInfo();

        books[0].displayBookInfo();
        books[1].displayBookInfo();
        books[2].displayBookInfo();

        members[0].returnBook("B001", books);
        members[1].borrowBook(books[0]);

        members[0].displayMemberInfo();
        members[1].displayMemberInfo();

        System.out.println("Total Books: " + Book.getTotalBooks());
        System.out.println("Available Books: " + Book.getAvailableBooks());
        System.out.println("Total Members: " + Member.getTotalMembers());
    }
}
