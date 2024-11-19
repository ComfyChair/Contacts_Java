package contacts;

import java.util.ArrayList;
import java.util.List;

public class Phonebook {
    private final List<Contact> contacts = new ArrayList<>();

    // Menu actions
    void addContact(String name, String surname, String phone) {
        contacts.add(new Contact(name, surname, phone));
        System.out.println("The record added.");
    }

    void removeContact(int index) {
        contacts.remove(index);
        System.out.println("The record removed!");
    }

    void editContact(int index, String fieldString, String newValue) {
        if (index >= 0 && index < contacts.size()) {
            contacts.get(index).edit(fieldString, newValue);
        }
    }

    int getCount() {
        return contacts.size();
    }

    List<Contact> getContacts() {
        return contacts;
    }

    // Utility methods
    boolean contains(Contact contact) {
        return contacts.contains(contact);
    }

    enum Command {
        ADD { @Override void execute(PhoneBookApp app) { app.addContact(); } },
        REMOVE { @Override void execute(PhoneBookApp app) { app.removeContact(); } },
        EDIT { @Override void execute(PhoneBookApp app) { app.editContact(); } },
        COUNT { @Override void execute(PhoneBookApp app) { app.countContacts(); } },
        LIST { @Override void execute(PhoneBookApp app) { app.listContacts();} };

        abstract void execute(PhoneBookApp app);

        /**
         * Factory method to create a command from a Command name
         * @param typeString String that matches the Command name
         * @return the corresponding Command
         */
        static Command getCommand(String typeString){
            try {
                return Command.valueOf(typeString.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command: " + typeString);
                return null;
            }
        }
    }
}
