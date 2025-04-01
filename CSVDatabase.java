import java.io.*;
import java.util.*;

class CSVDatabase {
    private static final String FILE_NAME = "library.csv";

    public CSVDatabase() {
        initializeDB();
    }

    private void initializeDB() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
                writer.println("Id,title,author,year,available");
            } catch (IOException e) {
                System.out.println("Error initializing CSV file: " + e.getMessage());
            }
        }
    }

    public void executeUpdate(String[] data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(String.join(",", data));
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public List<String[]> executeQuery() {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        }
        return records;
    }

    public void overwriteDatabase(List<String[]> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String[] row : data) {
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            System.out.println("Error overwriting CSV file: " + e.getMessage());
        }
    }

    public List<String[]> searchByAuthor(String author) {
        List<String[]> results = new ArrayList<>();
        List<String[]> books = executeQuery();
        for (String[] book : books) {
            if (book[2].equalsIgnoreCase(author)) {
                results.add(book);
            }
        }
        return results;
    }

    public List<String[]> searchByTitle(String title) {
        List<String[]> results = new ArrayList<>();
        List<String[]> books = executeQuery();
        for (String[] book : books) {
            if (book[1].equalsIgnoreCase(title)) {
                results.add(book);
            }
        }
        return results;
    }

    public List<String[]> searchByYear(int year) {
        List<String[]> results = new ArrayList<>();
        List<String[]> books = executeQuery();
        for (String[] book : books) {
            if (Integer.parseInt(book[3]) == year) {
                results.add(book);
            }
        }
        return results;
    }
}