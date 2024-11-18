package contacts;

import java.util.ArrayList;
import java.util.List;

public class Phonebook {
    List<Contact> contacts = new ArrayList<>();

    Phonebook() {
        System.out.println("A Phone Book created!");
    }

    Phonebook(Contact contact) {
        contacts.add(contact);
        System.out.println("A Phone Book with a single record created!");
    }

    void addContact(Contact contact) {
        contacts.add(contact);
    }

    String getPhoneNumber(int id) {
        return contacts.get(id).getPhoneNumber();
    }
}
