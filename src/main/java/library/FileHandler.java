package library;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String BOOK_FILE = "data/books.txt";
    private static final String MEMBER_FILE = "data/members.txt";

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Book book = new Book(
                        data[0], data[1], data[2], Integer.parseInt(data[3])
                );
                book.setAvailable(Boolean.parseBoolean(data[4]));
                book.setBorrowedBy(data[5].equals("null") ? null : data[5]);
                book.setDueDate(data[6].equals("null") ? null : LocalDate.parse(data[6]));
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Books file not found. Starting fresh.");
        }
        return books;
    }

    public void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOK_FILE))) {
            for (Book b : books) {
                writer.println(
                        b.getIsbn() + "," +
                        b.getTitle() + "," +
                        b.getAuthor() + "," +
                        b.getYear() + "," +
                        b.isAvailable() + "," +
                        b.getBorrowedBy() + "," +
                        b.getDueDate()
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving books.");
        }
    }

    public List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                members.add(new Member(data[0], data[1]));
            }
        } catch (IOException e) {
            System.out.println("Members file not found. Starting fresh.");
        }
        return members;
    }

    public void saveMembers(List<Member> members) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMBER_FILE))) {
            for (Member m : members) {
                writer.println(m.getId() + "," + m.getName());
            }
        } catch (IOException e) {
            System.out.println("Error saving members.");
        }
    }
}
