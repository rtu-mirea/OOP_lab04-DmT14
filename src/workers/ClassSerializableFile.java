package workers;

import java.io.*;
import java.util.ArrayList;

public class ClassSerializableFile {
    private String path;
    private ArrayList<Worker> list;

    public ClassSerializableFile(String path) {
        this.path = path;
    }

    // запись сериализованного объекта в файл
    public void writeOneObject(Worker worker) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(worker);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // чтение сериализованного объекта из файла
    public Worker readOneObject() {
        Worker worker = null;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            worker = (Worker) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return worker;
    }

    // вернуть объект
    public ClassSerializableFile get() {
        return this;
    }

    // создание коллекции и запись коллекции в файл
    public void writeListToFile() { // записывает данные из консоли в обычный файл
        list = new ArrayList<>();
        Worker.createWorker();
        Worker worker = Worker.getInstance();
        worker.setDataWorker(15, "Petrov", 22222);
        list.add(Worker.getInstance());
        Worker.createWorker();
        Worker worker2 = Worker.getInstance();
        list.add(worker2);
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // чтение объектов из файла в коллекцию
    public void readFileToList() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            list = (ArrayList<Worker>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Worker> getList() {
        return this.list;
    }

    public void printList() {
        for (Worker w : list) {
            System.out.println(w);
        }
    }
}