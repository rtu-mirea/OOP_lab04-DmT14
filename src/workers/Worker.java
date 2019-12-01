package workers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;


public class Worker implements Serializable {
    // Задание 3.1.
    private int code;
    private String surname;
    private int salary;

    private static Worker instance = null;
    private Worker() {}

    // вернуть объект
    public static Worker getInstance() {
        if (instance == null) {
            return new Worker();
        }
        return instance;
    }

    // Ввод данных объекта с консоли
    public static void createWorker() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("\nВведите код сотрудника: ");
            int code = Integer.parseInt(reader.readLine());
            System.out.print("Введите фамилию сотрудника: ");
            String surname = reader.readLine();
            System.out.print("Введите зарплату сотрудника: ");
            int salary = Integer.parseInt(reader.readLine());
            instance = new Worker();
            instance.setDataWorker(code, surname, salary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Залить данные в объект
    public void setDataWorker(int code, String surname, int salary) {
        this.code = code;
        this.surname = surname;
        this.salary = salary;
    }

    public String toString() {
        return "Worker{" +
                "Код = " + code +
                ",\n Фамилия = '" + surname + '\'' +
                ",\n Зарплата = " + salary +
                '}';
    }
}