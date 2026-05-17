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
```
 ![CFG Search](CFG1.png)


### B. Функција `borrowBook`

#### Текст од кодот со нумерирани јазли (Nodes):
```java
public void borrowBook(String title, String author) {
    if (title.isEmpty() || author.isEmpty()){ // јазол 1
        throw new IllegalArgumentException("Invalid search query"); // јазол 2
    }
    for (Book book : books) { // јазол 3
        if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) { // јазол 4
            if (!book.isBorrowed()) { // јазол 5
                book.setBorrowed(true); // јазол 6
                System.out.println("Borrowed successfully"); // јазол 6
            } else {
                throw new RuntimeException("Book is already borrowed."); // јазол 7
            }
            return; // јазол 8
        } // јазол 9
    }
    throw new RuntimeException("Book not found"); // јазол 10
}
```
![CFG Search](CFG2.png)

## 2.Цикломатска комплексност
Цикломатската комплексност ја пресметуваме по формулата $V(G) = P + 1$, каде што $P$ е бројот на предикатни (одлучувачки) јазли во соодветниот Control Flow Graph.
### А. Функција `searchBookByTitle`
Предикатни јазли се:
1. if (title.isEmpty()) (јазол 1)
2. for (Book book : books) (јазол 4)
3. if (book.getTitle().equalsIgnoreCase(title) && !book.isBorrowed()) (јазол 5)
4. if (results.isEmpty()) (јазол 8)
   
Пресметка: $V(G) = 4 + 1 = 5$.

### B. Функција `borrowBook`
Предикатни јазли се:
1. if (title.isEmpty() || author.isEmpty()) (јазол 1)
2. for (Book book : books) (јазол 3)
3. if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) (јазол 4)
4. if (!book.isBorrowed()) (јазол 5)
   
Пресметка: $V(G) = 4 + 1 = 5$.


---

## 6. Објаснување за Every Statement критериумот (searchBookByTitle)

За да се изврши секоја наредба ( statement) во функцијата `searchBookByTitle` барем еднаш, воспоставени се следните тест случаи:

| Тест случај | Влезни податоци | Очекуван резултат | Покриени јазли од CFG | Објаснување |
|---|---|---|---|---|
| `Test 1` | `title = ""` | `IllegalArgumentException` | Јазол 1, Јазол 2 | Го активира условот за празен наслов на самиот почеток на функцијата. |
| `Test 2` | `title = "Clean Code"` (Книгата постои и е слободна) | `List<Book>` со големина 1 | Јазол 1, 3, 4, 5, 6, 7, 8, 10 | Влегува во циклусот, го исполнува `if` условот, ја додава книгата во листата и успешно ја враќа. |
| `Test 3` | `title = "The Hobbit"` (Не постои таква книга) | `null` | Јазол 1, 3, 4, 8, 9 | Поминува низ циклусот без совпаѓање, листата `results` останува празна, па се извршува `return null`. |

### Минимален број на тест случаи:
Минималниот број на тест случаи за да се исполни Every Statement критериумот е **3**. Помалку од 3 тестови не можат да ги извршат сите три различни завршетоци (два исклучоци/null излези и еден успешен излез).

---

## 8. Објаснување за Every Branch критериумот (borrowBook)

За да се поминат сите гранки (edges) од графовите, дефинирани се следните четири ситуации:

| Тест случај | Влезни податоци | Очекуван излез | Покриени гранки (Edges) | Објаснување |
|---|---|---|---|---|
| `Test 1` | `title = ""`, `author = "George Orwell"` | `IllegalArgumentException` | 1 -> 2 (True гранка) | Го извршува True исходот од почетната проверка. |
| `Test 2` | `title = "The Hobbit"`, `author = "J.R.R. Tolkien"` | `RuntimeException("Book not found")` | 1 -> 3 (False), 3 -> 10 (Крај на циклус) | Ја поминува False гранката на почетокот и излегува од циклусот преку False гранка кога нема книги. |
| `Test 3` | `title = "1984"`, `author = "George Orwell"` (Слободна) | Успешно изнајмување | 1 -> 3, 3 -> 4 (True), 4 -> 5 (True), 5 -> 6 (True) | Ги поминува сите True гранки по ред во циклусот до успешно изнајмување. |
| `Test 4` | `title = "1984"`, `author = "George Orwell"` (Зафатена) | `RuntimeException("Book is already borrowed.")` | 1 -> 3, 3 -> 4 (True), 4 -> 5 (True), 5 -> 7 (False гранка) | Стигнува до пронајдена книга, но ја активира False гранката на внатрешниот `if` бидејќи книгата е веќе зафатена. |

### Минимален број на тест случаи:
Минимално се потребни **4 тест случаи** за целосно покривање на Every Branch критериумот бидејќи имаме 4 независни логички исходи кои мора да резултираат со различни патеки во извршувањето.

---

## 10. Објаснување за Multiple Condition критериумот

### А. За условот `if (title.isEmpty() || author.isEmpty())` во `borrowBook`
Поради short-circuit карактеристиката на операторот `||` (ИЛИ), можни се следните комбинации:

| Тест случај | `title.isEmpty()` | `author.isEmpty()` | Исход од условот | Покриена комбинација |
|---|---|---|---|---|
| `MC Test 1` | `True` (`""`) | `X` (Не се проверува) | `True` (Влегува во `if`) | **T || X** |
| `MC Test 2` | `False` (`"1984"`) | `True` (`""`) | `True` (Влегува во `if`) | **F || T** |
| `MC Test 3` | `False` (`"1984"`) | `False` (`"George Orwell"`) | `False` (Прескокнува) | **F || F** |

* **Минимален број на тест случаи:** **3**

### Б. За условот `if (book.getTitle().equalsIgnoreCase(title) && !book.isBorrowed())` во `searchBookByTitle`
Поради short-circuit карактеристиката на операторот `&&` (И), можни се следните комбинации:

| Тест случај | Совпаѓање на наслов | `!book.isBorrowed()` | Исход од условот | Покриена комбинација |
|---|---|---|---|---|
| `MC Test 4` | `False` (`"Непостоечка"`) | `X` (Не се проверува) | `False` (Прескокнува) | **F && X** |
| `MC Test 5` | `True` (`"Effective Java"`) | `False` (Изнајмена е) | `False` (Прескокнува) | **T && F** |
| `MC Test 6` | `True` (`"Clean Code"`) | `True` (Слободна е) | `True` (Влегува во `if`) | **T && T** |

* **Минимален број на тест случаи:** **3**
