package library;

import java.time.LocalDate;
import java.util.List;

public class Library {

    private List<Book> books;
    private List<Member> members;
    private FileHandler fileHandler;

    public Library() {
        fileHandler = new FileHandler();
        books = fileHandler.loadBooks();
        members = fileHandler.loadMembers();
    }

    public void addBook(Book book) {
        books.add(book);
        fileHandler.saveBooks(books);
        System.out.println("Book added successfully.");
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        books.forEach(System.out::println);
    }

    public void registerMember(Member member) {
        members.add(member);
        fileHandler.saveMembers(members);
        System.out.println("Member registered successfully.");
    }

    public void borrowBook(String isbn, String memberId) {
        Book book = books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);

        Member member = members.stream()
                .filter(m -> m.getId().equals(memberId))
                .findFirst()
                .orElse(null);

        if (book == null || member == null || !book.isAvailable()) {
            System.out.println("Borrow operation failed.");
            return;
        }

        book.setAvailable(false);
        book.setBorrowedBy(memberId);
        book.setDueDate(LocalDate.now().plusWeeks(2));
        member.borrowBook(isbn);

        fileHandler.saveBooks(books);
        fileHandler.saveMembers(members);

        System.out.println("Book borrowed successfully.");
    }

    public void showStatistics() {
        long available = books.stream().filter(Book::isAvailable).count();
        long borrowed = books.size() - available;

        System.out.println("\n--- Library Statistics ---");
        System.out.println("Total Books: " + books.size());
        System.out.println("Available Books: " + available);
        System.out.println("Borrowed Books: " + borrowed);
        System.out.println("Members: " + members.size());
    }
}
