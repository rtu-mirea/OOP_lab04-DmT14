package books;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

// Задание 2. п.2
public class Protocol {
    BufferedReader reader;
    private DataOutputStream out;
    private File file;
    DataInputStream in;
    private int SIZE_PAGE = 256; // размер сообщения
    private int SIZE_STRING = 64; // размер строки

    // При создании объекта класса Protocol будет создаваться файл
    public Protocol(String fileName) {
        file = new File(fileName);
        if (file.exists())
            System.out.println("Файл с именем " + file.getName() + " уже существует");
    }

    // Задание 2. п.3
    public void writeToFile() {
        int inventory = 0;
        int isTerm = -1;
        String tmp = "";

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(new FileOutputStream(file));
            do {
                System.out.print("\nВведите инвентарный номер: ");
                inventory = Integer.parseInt(reader.readLine());
                out.writeInt(inventory);
                while (true) {
                    System.out.print("Введите 1, если книга сдана в срок, 0 - если нет: ");
                    isTerm = Integer.parseInt(reader.readLine());
                    if (isTerm == 0 || isTerm == 1) {
                        break;
                    }
                }
                out.writeInt(isTerm);
                System.out.print("Введите автора: ");
                writeStr();
                System.out.print("Введите название: ");
                writeStr();
                System.out.print("Введите дату выдачи книги в формате ДД-ММ-ГГГГ: ");
                writeStr();
                System.out.print("Введите дату возврата книги в формате ДД-ММ-ГГГГ: ");
                writeStr();
                System.out.print("\nХотите ввести следующую запись? (англ.) y - да, любой другой символ - нет: ");
            } while(reader.readLine().equals("y"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // метод записи строки в файл
    private void writeStr() throws IOException { // записывает в обычный файл размер строки и строку
        String tmp = reader.readLine();
        out.writeInt(tmp.getBytes().length);
        out.writeBytes(tmp);
    }

    // метод чтения строки из файла
    private String readStr() throws IOException { // читает строку в соответствии с заданным размером
        byte[] str = new byte[in.readInt()];
        in.read(str);
        return new String(str);
    }

    // метод преобразования строки в корректную дату
    private LocalDate getLocalDateFromStr(String str) { // преобразует строку в дату
        String[] tmp = str.split("-");
        return LocalDate.of(Integer.parseInt(tmp[2]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[0]));
    }

    // Задание 2. п.4
    public ArrayList<Note> getNotes() { // возвращает список книг, не сданных в срок
        ArrayList<Note> notes = new ArrayList<>();
        try {
            in = new DataInputStream(new FileInputStream(file));
            while(in.available() > 5) {
                int inventory;
                int isTerm; // сдана в срок - 1, не сдана в срок -  0
                String author;
                String title;
                LocalDate issueDate;
                LocalDate receiveDate;
                inventory = in.readInt();
                isTerm = in.readInt();
                author = readStr();
                title = readStr();
                try {
                    issueDate = getLocalDateFromStr(readStr());
                    receiveDate = getLocalDateFromStr(readStr());
                    if (isTerm == 0) { // дополнительное задание 1
                        notes.add(new Note(inventory, isTerm, author, title, issueDate, receiveDate));
                    }
                } catch (DateTimeException e) {
                    System.out.println("В файле содержится некорректная дата");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes;
    }

    // Задание 2 п.5
    // запись в файл с использованием RandomAccessFile
    public void writeToRAF(ArrayList<Note> notes) { // записывает в файл с использованием RandomAccessFile записи по книгам, которые не возвращены в срок
        try(RandomAccessFile raf = new RandomAccessFile("RAF_" + file, "rw")) {
            raf.setLength(0); // очищаем файл перед началом записи
            for(Note note : notes) {
                raf.writeInt(note.getInventory());
                raf.writeInt(note.getIsTerm());

                raf.writeInt(note.getAuthor().getBytes().length);
                raf.writeBytes(note.getAuthor());
                // дополняем строку до 64 байт
                byte[] spaces = new byte[SIZE_STRING - note.getAuthor().getBytes().length];
                for (int i = 0; i < SIZE_STRING - note.getAuthor().getBytes().length; i++) {
                    spaces[i] = 32;
                }
                raf.write(spaces);

                raf.writeInt(note.getTitle().getBytes().length);
                raf.writeBytes(note.getTitle());
                spaces = new byte[SIZE_STRING - note.getTitle().getBytes().length];
                for (int i = 0; i < SIZE_STRING - note.getTitle().getBytes().length; i++) {
                    spaces[i] = 32;
                }
                raf.write(spaces);

                raf.writeInt(note.getIssueDate().toString().getBytes().length);
                raf.writeBytes(note.getIssueDate().toString());

                raf.writeInt(note.getReceiveDate().toString().getBytes().length);
                raf.writeBytes(note.getReceiveDate().toString());

                int sumBytes = 4 * 6 + SIZE_STRING * 2 + 20; // 20 - 2 даты, SIZE_STRING * 2 - название и автор, 4 * 6 - инв. номер, признак возврата в срок,  4 по 4 - размеры передаваемых строк
                // Создается массив байт из пробелов для заполнения одной страницы объектом Note
                spaces = new byte[SIZE_PAGE - sumBytes];
                for (int i = 0; i < SIZE_PAGE - sumBytes; i++) {
                    spaces[i] = 32;
                }
                raf.write(spaces);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // записать в запись с заданным номером текущую дату возврата
    // Задание 2, п. 5.2., дополнительное задание 2
    public void setCurrentDateForNote(int inventory) { // добавляет в выбранную запись текущую дату возврата
        // этот код ищет по инвентарному номеру в списке
        int size = (int) new File("RAF_" + file).length();
        try(RandomAccessFile raf = new RandomAccessFile("RAF_" + file, "rw")) {
            int p = 0;
            int count = p * SIZE_PAGE;
            while(count < size) {
                raf.seek(count);
                if (raf.readInt() == inventory) {
                    raf.skipBytes(4 * 5 + 10 +  SIZE_STRING * 2);
                    raf.writeBytes(LocalDate.now().toString());
                    break;
                }
                p++;
                count = p * SIZE_PAGE;
                raf.seek(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // дополнительная операция 1: по инвентарному номеру вернуть название и дату возврата книги
    public String getTitleAndDate(int inventory) { // возвращает название и дату по указанному инвентарному номеру
        String str = "Книги с инвентарным номером " + inventory + " не обнаружено";
        int size = (int) new File("RAF_" + file).length();
        try(RandomAccessFile raf = new RandomAccessFile("RAF_" + file, "r")) {
            int p = 0;
            int count = p * SIZE_PAGE;
            while(count < size) {
                raf.seek(count);
                if (raf.readInt() == inventory) {
                    str = "";
                    raf.skipBytes(4 + 4 + 64);
                    byte[] strByte = new byte[raf.readInt()];
                    raf.read(strByte);
                    str = new String(strByte);
                    raf.skipBytes(SIZE_STRING - strByte.length + 4 + 10 + 4);
                    byte[] dateByte = new byte[10];
                    raf.read(dateByte);
                    str += " ";
                    str += new String(dateByte);
                }
                p++;
                count = p * SIZE_PAGE;
                raf.seek(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    // дополнительная операция 2: определить, один ли автор у двух книг
    public boolean matchAuthors(int inventory1, int inventory2) { // проверяет по двум инвентарным номерам, совпадают ли авторы у книг
        String author1 = "-1";
        String author2 = "0";
        int size = (int) new File("RAF_" + file).length();
        try(RandomAccessFile raf = new RandomAccessFile("RAF_" + file, "r")) {
            int p = 0;
            int count = p * SIZE_PAGE;
            while(count < size) {
                raf.seek(count);
                int inv = raf.readInt();
                if (inv == inventory1 || inv == inventory2) {
                    raf.skipBytes(4); // пропускаем 4 байта для признака срока сдачи книги
                    byte[] strByte = new byte[raf.readInt()];
                    raf.read(strByte);
                    if (author1.equals("-1")) {
                        author1 = new String(strByte);
                    }
                    else {
                        author2 = new String(strByte);
                    }
                }
                p++;
                count = p * SIZE_PAGE;
                raf.seek(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return author1.equals(author2);
    }
}