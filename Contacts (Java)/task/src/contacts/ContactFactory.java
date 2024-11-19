package contacts;

import java.util.Scanner;

public class ContactFactory {
    static Contact createContact(Scanner scanner) {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();
        Contact contact = switch (type) {
            case "person" -> new Person();
            case "organization" -> new Organization();
            default -> null;
        };
        if (contact == null) {
            System.out.println("Invalid type: " + type);
        } else {
            for (String field : contact.getEditableFields()) {
                System.out.print("Enter the " + field + ": ");
                String value = scanner.nextLine();
                contact.edit(field, value);
            }
        }
        return contact;
    }
}
