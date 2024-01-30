package banking;

import java.util.Date; // это класс который предоставляет время и дату для выполнения операций депозита и перевода
import java.util.HashMap; // это класс, который представляет хэш-таблицу, или ассоциативный массив. В данном случае он используется для хранения карты клиентов, где ключом является имя пользователя, а значением - объект клиента
import java.util.Map; // это интерфейс, который представляет собой карту, или ассоциативный массив. В данном случае он используется для объявления типа данных customerMap, который является картой клиентов.
import java.util.Scanner; // это класс, который используется для чтения ввода пользователя с консоли. В данном коде он используется для получения ввода от пользователя, такого как имя пользователя, пароль, сумма депозита или перевода и выбор операции.
// Создаем класс Banking
public class Banking {

    private static double amount = 0;
    Map<String, Customer> customerMap;

    Banking() {
        customerMap = new HashMap<String, Customer>();
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Customer customer;
            Banking bank = new Banking();
            int choice;
            outer:
            while (true) {
                // Приветственное меню 
                System.out.println("\n-------------------");
                System.out.println("БАНКОВСКИЙ СЧЕТ");
                System.out.println("-------------------\n");
                System.out.println("1. Регистрация");
                System.out.println("2. Авторизация");
                System.out.println("3. Выход");
                System.out.print("\nВведите ваш выбор : ");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:      //  Процесс регистрации аккаунта
                        System.out.print("Введите ваше Имя : ");
                        String firstName = sc.nextLine();
                        System.out.print("Введите вашу Фамилию : ");
                        String lastName = sc.nextLine();
                        System.out.print("Введите ваш Адресс : ");
                        String address = sc.nextLine();
                        System.out.print("Введите ваш номер телефона : ");
                        String phone = sc.nextLine();
                        System.out.println("Введите логин : ");
                        String username = sc.next();
                        while (bank.customerMap.containsKey(username)) { 
                            // В том случае, если логин будет занят - выводим сообщение в консоль
                            System.out.println("Логин занят. Повторите попытку. : ");
                            username = sc.next();
                        }
                        System.out.println("Установите пароль:");
                        String password = sc.next();
                        sc.nextLine();
                        // Отправляем данные в класс Customer
                        customer = new Customer(firstName, lastName, address, phone, username, password, new Date());
                        bank.customerMap.put(username, customer);
                        break;
                        //  авторизация в созданный ранее аккаунт
                    case 2:
                        System.out.println("Введите логин : ");
                        username = sc.next();
                        sc.nextLine();
                        System.out.println("Введите пароль : ");
                        password = sc.next();
                        sc.nextLine();
                        if (bank.customerMap.containsKey(username)) {
                            customer = bank.customerMap.get(username);
                            if (customer.getPassword().equals(password)) {
                                while (true) { // Меню после авторизации
                                    System.out.println("\n-------------------");
                                    System.out.println("W  E  L  C  O  M  E");
                                    System.out.println("-------------------\n");
                                    System.out.println("1. Депозит.");
                                    System.out.println("2. Перевод.");
                                    System.out.println("3. 5 последних переводов.");
                                    System.out.println("4. Ваша информация.");
                                    System.out.println("5. Выйти из аккаунта.");
                                    System.out.print("\nВведите ваш выбор : ");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                    switch (choice) {
                                        case 1:
                                            System.out.print("Введите сумму : ");
                                            while (!sc.hasNextDouble()) {
                                                System.out.println("Недопустимая сумма. Введите еще раз :");
                                                sc.nextLine();
                                            }
                                            amount = sc.nextDouble();
                                            sc.nextLine();
                                            customer.deposit(amount, new Date());
                                            break;

                                        case 2:
                                            System.out.print("Введите логин пользователя получателя : ");
                                            username = sc.next();
                                            sc.nextLine();
                                            System.out.println("Введите сумму : ");
                                            while (!sc.hasNextDouble()) {
                                                System.out.println("Недопустимая сумма. Введите еще раз:");
                                                sc.nextLine();
                                            }
                                            amount = sc.nextDouble();
                                            sc.nextLine();
                                            // Лимит на перевод равен 300, если лимит привышен - выводим ошибку.
                                            if (amount > 300) {
                                                System.out.println("Лимит перевода превышен. Свяжитесь с менеджером банка.");
                                                break;
                                            }
                                            if (bank.customerMap.containsKey(username)) {
                                                Customer payee = bank.customerMap.get(username); //Todo: check
                                                payee.deposit(amount, new Date());
                                                customer.withdraw(amount, new Date());
                                            } else {
                                                System.out.println("Логин пользователя не существует.");
                                            }
                                            break;

                                        case 3:
                                            for (String transactions : customer.getTransactions()) {
                                                System.out.println(transactions);
                                            }
                                            break;
                                        // Вывод информации аккаунта
                                        case 4:
                                            System.out.println("Имя пользователя: " + customer.getFirstName());
                                            System.out.println("Фамилия пользователя: " + customer.getLastName());
                                            System.out.println("Логин пользователя: " + customer.getUsername());
                                            System.out.println("Домашний адрес: " + customer.getAddress());
                                            System.out.println("Номер телефона: " + customer.getPhone());
                                            break;
                                        case 5:
                                            continue outer;
                                        default:
                                            System.out.println("Неправильный выбор !");
                                    }
                                }
                            } else {
                                System.out.println("Неверное имя пользователя/пароль.");
                            }
                        } else {
                            System.out.println("Неверное имя пользователя/пароль.");
                        }
                        break;

                    case 3:
                        System.out.println("\n Благодарим вас за использование нашей Банковской системы.");
                        System.exit(1);
                        break;
                    default:
                        System.out.println("Неправильный выбор !");
                }
            }
        }
    }
}
