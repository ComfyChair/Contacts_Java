package contacts;

import java.util.ArrayList;
import java.util.List;

public class Phonebook {
    private final List<Contact> contacts = new ArrayList<>();

    // Menu actions
    void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("The record added.");
    }

    void removeContact(int index) {
        contacts.remove(index);
        System.out.println("The record removed!");
    }

    Contact getContact(int index) {
        if (index < 0 || index >= contacts.size()) {
            return null;
        }
        return contacts.get(index);
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
}
