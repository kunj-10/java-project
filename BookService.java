
import java.util.*;

class BookServiceException extends Exception {

    public BookServiceException(String message) {
        super(message);
    }
}

class BookService {

    final private CSVDatabase db;
    private static int bookId = 1;

    public BookService(CSVDatabase db) {
        this.db = db;
        List<String[]> allBooks = db.executeQuery();
        bookId = allBooks.size();
    }

    public void addBook() {
        System.out.print("Enter Book Title: ");
        String title = Library.scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = Library.scanner.nextLine();
        System.out.print("Enter Year: ");
        int year = Library.scanner.nextInt();
        Library.scanner.nextLine();
        db.executeUpdate(new String[]{String.valueOf(bookId++), title, author, String.valueOf(year), "Yes"});
        System.out.println("Book added successfully!");
    }

    public void viewBooks() {
        System.out.println("\nChoose search option:");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Year");
        System.out.println("4. View All Books");
        System.out.print("Enter your choice: ");
        int choice = Library.scanner.nextInt();
        Library.scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Book Title to search: ");
                String title = Library.scanner.nextLine();
                List<String[]> books = db.searchByTitle(title);
                displayBooks(books);
                break;
            }
            case 2 -> {
                System.out.print("Enter Author to search: ");
                String author = Library.scanner.nextLine();
                List<String[]> books = db.searchByAuthor(author);
                displayBooks(books);
                break;
            }
            case 3 -> {
                System.out.print("Enter Year to search: ");
                int year = Library.scanner.nextInt();
                Library.scanner.nextLine();
                List<String[]> books = db.searchByYear(year);
                displayBooks(books);
                break;
            }
            case 4 -> {
                List<String[]> allBooks = db.executeQuery();
                displayBooks(allBooks);
                break;
            }
            default ->
                System.out.println("Invalid choice! Please try again.");
        }
    }

    private void displayBooks(List<String[]> books) {
        try {
            if (books.isEmpty()) {
                throw new BookServiceException("No books found.");
            } else {
                for (String[] book : books) {
                    System.out.printf("%s | %s | %s | %s | %s\n", book[0], book[1], book[2], book[3], book[4]);
                }
            }
        } catch (BookServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateBook() {
        System.out.print("Enter Book ID to update: ");
        int id = Library.scanner.nextInt();
        Library.scanner.nextLine();
        List<String[]> books = db.executeQuery();
        boolean found = false;
        for (int i = 1; i < books.size(); i++) {
            if (Integer.parseInt(books.get(i)[0]) == id) {
                System.out.print("Enter New Title: ");
                String title = Library.scanner.nextLine();
                System.out.print("Enter New Author: ");
                String author = Library.scanner.nextLine();
                System.out.print("Enter New Year: ");
                int year = Library.scanner.nextInt();
                Library.scanner.nextLine();
                books.set(i, new String[]{String.valueOf(id), title, author, String.valueOf(year), "Yes"});
                found = true;
                break;
            }
        }
        if (found) {
            db.overwriteDatabase(books);
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Book ID not found.");
        }
    }

    public void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        int id = Library.scanner.nextInt();
        Library.scanner.nextLine();
        List<String[]> books = db.executeQuery();
        boolean found = false;
        for (int i = 1; i < books.size(); i++) {
            if (Integer.parseInt(books.get(i)[0]) == id) {
                books.remove(i);
                found = true;
                break;
            }
        }
        if (found) {
            db.overwriteDatabase(books);
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book ID not found.");
        }
    }
}
