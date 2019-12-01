package books;

import java.time.LocalDate;

// Задание 2. п.1
public class Note {
    private int inventory;
    private int isTerm; // сдана в срок - 1, не сдана в срок - 0
    private String author;
    private String title;
    private LocalDate issueDate; // дата выдачи
    private LocalDate receiveDate; // дата возврата

    public Note(int inventory, int isTerm, String author, String title, LocalDate issueDate, LocalDate receiveDate) {
        this.inventory = inventory;
        this.isTerm = isTerm;
        this.author = author;
        this.title = title;
        this.issueDate = issueDate;
        this.receiveDate = receiveDate;
    }

    public String toString() {
        return "Note{" +
                "Инвентарный номер = " + inventory +
                ((isTerm == 1) ? ",\n Сдана в срок" : ",\n Сдана не в срок") +
                ",\n Автор='" + author + '\'' +
                ",\n Название='" + title + '\'' +
                ",\n Дата выдачи=" + issueDate +
                ",\n Дата возврата=" + receiveDate +
                "}\n";
    }

    public int getInventory() {
        return inventory;
    }

    public int getIsTerm() {
        return isTerm;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }
}