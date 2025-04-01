import java.util.Scanner;

public class Library {
    public static Scanner scanner = new Scanner(System.in);
    final private static BookService bookService = new BookService(new CSVDatabase());

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> bookService.addBook();
                case 2 -> bookService.viewBooks();
                case 3 -> bookService.updateBook();
                case 4 -> bookService.deleteBook();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);
    }

    private static void showMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Update Book");
        System.out.println("4. Delete Book");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
}