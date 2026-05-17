# SI_2026_lab2_246035
 * **Име и презиме:** Огнен Стојчевски
 * **Индекс:** 246035

---

 ## 1. Control Flow Graph (CFG)
 ### А. Функција `searchBookByTitle`

#### Текст од кодот со нумерирани јазли (Nodes):
```java
public List<Book> searchBookByTitle(String title) {
    if (title.isEmpty()){ // јазол 1
        throw new IllegalArgumentException("Invalid title"); // јазол 2
    }
    List<Book> results = new ArrayList<Book>(); // јазол 3
    for (Book book : books) { // јазол 4
        if (book.getTitle().equalsIgnoreCase(title) && !book.isBorrowed()) { // јазол 5
            results.add(book); // јазол 6
        } // јазол 7
    }
    if (results.isEmpty()) { // јазол 8
        return null; // јазол 9
    }
    return results; // јазол 10
}
