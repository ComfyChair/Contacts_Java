package contacts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Contact contact = createContact(scanner);
        Phonebook phonebook = new Phonebook(contact);
    }

    private static Contact createContact(Scanner scanner) {
        System.out.println("Enter the name of the person:");
        String name = scanner.nextLine();
        System.out.println("Enter the surname of the person:");
        String surname = scanner.nextLine();
        System.out.println("Enter the number:");
        String phone = scanner.nextLine();
        return new Contact(name, surname, phone);
    }
}
