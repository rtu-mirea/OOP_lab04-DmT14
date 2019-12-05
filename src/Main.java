import books.Protocol;
import workers.ClassSerializableFile;
import workers.ClassTextFile;

import java.io.*;
import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) throws java.lang.Exception {
        try {


            System.out.println("------------------------------------------------------------------------\n\t\tЗадание № 1");
            // Задание 1, упр. 1.1. Создать файл в папке приложения с именем MyFile1.txt
            File file1 = new File("src\\MyFile1.txt");
            file1.createNewFile();

            // Задание 1, упр. 1.2. Создать файл с именем MyFile2.txt в корне определенного диска [ОТКАЗАНО В ДОСТУПЕ]
            File file2 = new File("C:\\MyFile2.txt");
            //file2.createNewFile();

            // Задание 1, упр. 1.3. Создать файл с именем MyFile3.txt в папке Имя диска\\Имя папки\\Имя файла [ОТКАЗАНО В ДОСТУПЕ]
            File file3 = new File("C:\\Program Files\\MyFile3.txt");
            //file3.createNewFile();

            // Задание 1, упр. 1.4. создать папку третьего уровня, например, Первая\\Вторая\\Третья
            File file4 = new File("src\\Первая\\Вторая\\Третья");
            file4.mkdirs();


            // Задание 1, упр. 2.1. Проверить, что вызывающий объект содержит имя файла, а не папки, и отобразить имя файла вызывающего объекта и его родительскую папку
            if (file1.isFile())
                System.out.println("\nОбъект с именем " + file1.getName() + " содержит имя файла. Родительская папка: " + file1.getParent());
            else System.out.println("\nОбъект с именем " + file1.getName() + " содержит имя папки");

            // Задание 1, упр. 2.2. Проверить, что вызывающий объект содержит имя папки, а не файла и отобразить имя файла вызывающего объекта
            if (file4.isDirectory()) System.out.println("Объект с именем " + file4.getName() + " содержит имя папки");
            else
                System.out.println("Объект с именем " + file4.getName() + " содержит имя файла. Родительская папка: " + file4.getParent());

            // Задание 1, упр. 2.3. Проверить, что в папке приложения существует файл с именем MyFile1.txt
            if (file1.exists()) System.out.println("\nФайл MyFile1.txt существует в папке приложения");
            else System.out.println("\nФайла MyFile1.txt нет в папке приложения");

            // Задание 1, упр. 2.4. Отобразить полный путь к файлу или папке объекта
            System.out.println("\nПолный путь к файлу MyFile1.txt: " + file1.getAbsolutePath());
            System.out.println("Полный путь к папка Третья: " + file4.getAbsolutePath());

            // Задание 1, упр. 2.5. Отобразить размер файла или папки объекта, указать единицу измерения. Прокомментировать вид файла – папка или файл
            if (file1.isFile())
                System.out.println("\nИмя: " + file1.getName() + ". Вид: файл. Размер = " + file1.length() + " байт");
            else if (file1.isDirectory())
                System.out.println("\nИмя: " + file1.getName() + ". Вид: папка. Размер = " + file1.length() + " байт");
            if (file4.isFile())
                System.out.println("Имя: " + file4.getName() + ". Вид: файл. Размер = " + file4.length() + " байт");
            else if (file4.isDirectory())
                System.out.println("Имя: " + file4.getName() + ". Вид: папка. Размер = " + file4.length() + " байт");


            // Задание 1, упр. 3.1. Добавить в папку приложения ещё одну папку
            File file5 = new File("scr\\Папка_для_1.3.1");
            file5.mkdir();

            // Задание 1, упр. 3.2. Сформировать массив файлов, находящихся в папке приложения, используя метод list(). Отобразить содержимое массива
            File file0 = new File("C:\\Users\\user\\Desktop\\task_io"); // П О М Е Н Я Т Ь  П У Т Ь ! ! ! //

            String[] files1 = file0.list();
            System.out.println("\nФайлы папки приложения методом list():");
            for (int i = 0; i < files1.length; i++)
                System.out.println("\t" + files1[i]);

            // Задание 1, упр. 3.3. Сформировать массив файлов, находящихся в папке приложения, используя метод listFiles(). Отобразить содержимое массива. Определить количество папок, содержащихся в файле приложения
            File[] files2 = file0.listFiles();
            System.out.println("\nФайлы папки приложения методом listFiles():");
            int amountDirectories = 0;
            for (int i = 0; i < files2.length; i++) {
                System.out.println("\t" + files2[i]);
                if (files2[i].isDirectory())
                    amountDirectories++;
            }
            System.out.println("Количество папок в файле приложения: " + amountDirectories);

            // Задание 1, упр. 3.4. Удалить папки и файлы, созданные во всех трёх упражнениях
            if (file1.exists()) file1.delete();
            if (file2.exists()) file2.delete();
            if (file3.exists()) file3.delete();
            if (file4.exists()) {
                String file4ParStr = file4.getParent();

                File file4Par = new File(file4ParStr);
                String file4ParParStr = file4Par.getParent();
                File file4ParPar = new File(file4ParParStr);
                file4.delete();
                file4Par.delete();
                file4ParPar.delete();
            }
            if (file5.exists()) file5.delete();
            System.out.println("\nСозданные файлы удалены");


            System.out.println("------------------------------------------------------------------------\n\t\tЗадание № 3");
            // Задание 3
            task3_num1();
            task3_num2();
            task3_num3();
        } catch (java.lang.Exception e) {
            System.out.println("Ошибка: " + e);
        }


        // Задание 2
        System.out.println("------------------------------------------------------------------------\n\t\tЗадание № 2");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Введите название файла для записи в него списка книг: ");
            Protocol protocol = new Protocol(reader.readLine());
            protocol.writeToFile();
            System.out.println("\nСписок не сданных в срок книг: ");
            System.out.println(protocol.getNotes());
            // Заполнение нового файла записями фиксированной длины
            protocol.writeToRAF(protocol.getNotes());
            int x = 0;
            while (true) {
                System.out.println("\nВведите инвентарный номер книги для установки текущей даты в дату возврата (-1 - для выхода) {для не сданных вовремя книг}:");
                x = Integer.parseInt(reader.readLine());
                if (x != -1) {
                    protocol.setCurrentDateForNote(x);
                } else {
                    break;
                }
            }

            x = 0;
            while (true) {
                System.out.println("\nВведите инвентарный номер книги для получения названия и даты возврата (-1 - для выхода) {для не сданных вовремя книг}:");
                x = Integer.parseInt(reader.readLine());
                if (x != -1) {
                    System.out.println(protocol.getTitleAndDate(x));
                } else {
                    break;
                }
            }

            while (true) {
                System.out.println("\nВведите в двух разных строках два инвентарных номера для проверки авторов на одинаковость (-1 - для выхода) {для не сданных вовремя книг}:");
                int inv1 = Integer.parseInt(reader.readLine());
                if(inv1 == -1) break;
                int inv2 = Integer.parseInt(reader.readLine());
                if (inv1 != -1) {
                    System.out.println("Признак того, что авторы одинаковые: " + protocol.matchAuthors(inv1, inv2));
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Задание 4
        System.out.println("------------------------------------------------------------------------\n\t\tЗадание № 4");
        ClassTextFile textFile = new ClassTextFile("worker.txt");
        try {
            ClassSerializableFile file = new ClassSerializableFile("test.txt");
            file.writeOneObject(textFile.getWorker());
            System.out.println(file.readOneObject());
            file.writeListToFile();
            file.readFileToList();
            System.out.print("\n");
            file.printList();
            System.out.println("=============");
            System.out.println(file.getList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Задание 3, упр. 1
    public static void task3_num1() throws IOException {
        try {
            FileReader reader = new FileReader("T1.txt");
            FileWriter writer = new FileWriter("T2.txt");

            int x;
            while ((x = reader.read()) != -1) {
                writer.write((char) x);
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
        }
    }

    // Задание 3, упр. 2
    public static void task3_num2() throws IOException {
        try {
            File file6 = new File("A.txt");
            file6.createNewFile();
            FileReader fileReader = new FileReader(file6);
            BufferedReader inb = new BufferedReader(fileReader, 128);

            File file7 = new File("B.txt");
            file7.createNewFile();
            FileWriter fileWriter = new FileWriter(file7);
            BufferedWriter outb = new BufferedWriter(fileWriter, 128);

            char[] buf = new char[128];
            for(int i = 0, j = 0; i < file6.length(); i++, j++) {
                if(j == buf.length) {
                    j = 0;
                    outb.write("\r\n");
                }
                buf[j] = (char)inb.read();
                outb.write(buf[j]);
            }

            inb.close();
            outb.close();
        } catch (IOException e) {
            System.out.println("Ошибка: " + e);
        }
    }

    // Задание 3, упр. 3
    public static void task3_num3() throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C.txt"), "UTF-8"));
            System.out.println("Кодировка, действующая в системе: " + Charset.defaultCharset().name());
            File file8 = new File("C.txt");

            String buf1 = "";
            int x;
            while ((x = in.read()) != -1) {
                buf1 += (char) x;
            }
            System.out.println(buf1);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e);
        }
    }
}