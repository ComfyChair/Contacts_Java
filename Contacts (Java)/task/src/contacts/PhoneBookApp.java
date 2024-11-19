package contacts;

import java.util.List;
import java.util.Scanner;

public class PhoneBookApp {
    private static final String MAIN_MENU = "Enter action (add, remove, edit, count, list, exit): ";
    private static final String NO_RECORDS = "No records to ";
    private static final String EXIT_CMD = "EXIT";
    private final Phonebook phonebook;
    private static final Scanner scanner = new Scanner(System.in);
    private static PhoneBookApp instance = null;

    private PhoneBookApp() {
        phonebook = new Phonebook();
        mainMenu();
    }

    static void start() {
        if (instance == null) {
            instance = new PhoneBookApp();
        }
    }

    private void mainMenu() {
        String input;
        do {
            System.out.println(MAIN_MENU);
            input = scanner.nextLine().toUpperCase();
            if (!input.equals(EXIT_CMD)) {
                Phonebook.Command command = Phonebook.Command.getCommand(input);
                if (command != null) {
                    command.execute(this);
                }
            }
        } while (!input.equals(EXIT_CMD));
        scanner.close();
    }

    void addContact() {
        System.out.print("Enter the name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter the number: ");
        String phone = scanner.nextLine();
        phonebook.addContact(name, surname, phone);
    }

    void removeContact() {
        if (phonebook.getCount() == 0) {
            System.out.println(NO_RECORDS + "remove!");
        } else {
            int index = selectRecord();
            phonebook.removeContact(index);
        }
    }

    void editContact() {
        if (phonebook.getCount() == 0) {
            System.out.println(NO_RECORDS + "edit!");
        } else {
            int index = selectRecord();
            System.out.print("Select a field (name, surname, number): ");
            String fieldString = scanner.nextLine();
            System.out.printf("Enter new %s: ", fieldString);
            String newValue = scanner.nextLine();
            phonebook.editContact(index, fieldString, newValue);
        }
    }

    private int selectRecord() {
        listContacts();
        System.out.print("Select a record: ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.printf("Invalid input: %s%n", input);
            return selectRecord();
        }
    }

    void countContacts() {
        System.out.printf("The Phone Book has %d records.%n", phonebook.getCount());
    }
    void listContacts() {
        List<Contact> contacts = phonebook.getContacts();
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            System.out.printf("%d. %s%n", i + 1, contact.toString());
        }
    }

    public static void main(String[] args) {
        PhoneBookApp.start();
    }

}
