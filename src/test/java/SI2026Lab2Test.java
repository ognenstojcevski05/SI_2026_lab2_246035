import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class SI2026Lab2Test {
    // БАРАЊЕ 5: Every Statement за searchBookByTitle
    @Test
    public void searchBookEveryStatementTest() {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));

        // Тест 1: Празен наслов (исфрла IllegalArgumentException)
        assertThrows(IllegalArgumentException.class, () -> library.searchBookByTitle(""));

        // Тест 2: Книгата е успешно пронајдена
        List<Book> found = library.searchBookByTitle("Clean Code");
        assertNotNull(found);
        assertEquals(1, found.size());

        // Тест 3: Книгата не постои во библиотеката (враќа null)
        assertNull(library.searchBookByTitle("The Hobbit"));
    }

    // БАРАЊЕ 7: Every Branch за borrowBook
    @Test
    public void borrowBookEveryBranchTest() {
        Library library = new Library();
        library.addBook(new Book("1984", "George Orwell", "Dystopian"));

        // Тест 1: Празен наслов/автор (исфрла IllegalArgumentException)
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook("", "George Orwell"));

        // Тест 2: Книгата не е пронајдена во целата листа (исфрла RuntimeException)
        RuntimeException exNotFound = assertThrows(RuntimeException.class, () ->
                library.borrowBook("The Hobbit", "J.R.R. Tolkien")
        );
        assertEquals("Book not found", exNotFound.getMessage());

        // Тест 3: Книгата е пронајдена и успешно изнајмена
        library.borrowBook("1984", "George Orwell");

        // Тест 4: Книгата е пронајдена, но веќе е изнајмена (исфрла RuntimeException)
        RuntimeException exAlreadyBorrowed = assertThrows(RuntimeException.class, () ->
                library.borrowBook("1984", "George Orwell")
        );
        assertEquals("Book is already borrowed.", exAlreadyBorrowed.getMessage());
    }

    // БАРАЊЕ 9: Multiple Condition Тестови
    @Test
    public void borrowBookMultipleConditionTest() {
        Library library = new Library();

        // 1. Комбинација: T || X (Празен title, кус спој)
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook("", "George Orwell"));

        // 2. Комбинација: F || T (Валиден title, празен author)
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook("1984", ""));

        // 3. Комбинација: F || F (Двата вноса се полни, условот е неточен и се прескокнува)
        library.addBook(new Book("1984", "George Orwell", "Dystopian"));
        library.borrowBook("1984", "George Orwell");
    }

    @Test
    public void searchBookMultipleConditionTest() {
        Library library = new Library();
        Book b1 = new Book("Clean Code", "Robert C. Martin", "Programming");
        Book b2 = new Book("Effective Java", "Joshua Bloch", "Programming");
        b2.setBorrowed(true);

        library.addBook(b1);
        library.addBook(b2);

        // 1. Комбинација: F && X (Насловот не се совпаѓа воопшто)
        assertNull(library.searchBookByTitle("NonExistent"));

        // 2. Комбинација: T && F (Точен наслов, но книгата е веќе изнајмена)
        assertNull(library.searchBookByTitle("Effective Java"));

        // 3. Комбинација: T && T (Точен наслов и книгата е слободна за изнајмување)
        assertNotNull(library.searchBookByTitle("Clean Code"));
    }
}
