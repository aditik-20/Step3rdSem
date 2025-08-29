import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;
    private double price;
    private int quantity;

    public Book(String title, String author, String isbn, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void addQuantity(int qty) { this.quantity += qty; }

    public void displayBook() {
        System.out.println("Title: " + title + ", Author: " + author +
                ", ISBN: " + isbn + ", Price: " + price + ", Quantity: " + quantity);
    }
}

class Library {
    private String libraryName;
    private Book[] books;
    private int totalBooks;

    public Library(String libraryName, int capacity) {
        this.libraryName = libraryName;
        this.books = new Book[capacity];
        this.totalBooks = 0;
    }

    public void addBook(Book book) {
        if (totalBooks < books.length) {
            books[totalBooks++] = book;
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Library is full!");
        }
    }

    public void searchBook(String keyword) {
        boolean found = false;
        for (int i = 0; i < totalBooks; i++) {
            if (books[i].getTitle().equalsIgnoreCase(keyword) || books[i].getAuthor().equalsIgnoreCase(keyword)) {
                books[i].displayBook();
                found = true;
            }
        }
        if (!found) System.out.println("No book found with keyword: " + keyword);
    }

    public void displayInventory() {
        System.out.println("=== Inventory of " + libraryName + " ===");
        for (int i = 0; i < totalBooks; i++) {
            books[i].displayBook();
        }
    }

    public double calculateTotalValue() {
        double totalValue = 0;
        for (int i = 0; i < totalBooks; i++) {
            totalValue += books[i].getPrice() * books[i].getQuantity();
        }
        return totalValue;
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library("City Library", 100);

        while (true) {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Calculate Total Value");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Enter price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();
                    library.addBook(new Book(title, author, isbn, price, qty));
                    break;
                case 2:
                    System.out.print("Enter title or author to search: ");
                    String keyword = sc.nextLine();
                    library.searchBook(keyword);
                    break;
                case 3:
                    library.displayInventory();
                    break;
                case 4:
                    System.out.println("Total Value of Books: " + library.calculateTotalValue());
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
