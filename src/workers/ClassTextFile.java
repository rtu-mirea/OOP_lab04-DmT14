package workers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClassTextFile {
    private String path;

    public ClassTextFile(String path) {
        this.path = path;
        File file = new File(path);
        if (file.exists())
            System.out.println("Файл с именем " + file.getName() + " уже существует");
    }

    public Worker getWorker() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(new File(path)));
        int code = sc.nextInt();
        String surname = sc.next();
        int salary = sc.nextInt();
        Worker worker = Worker.getInstance();
        worker.setDataWorker(code, surname, salary);
        return worker;
    }
}