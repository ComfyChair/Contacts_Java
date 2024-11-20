package contacts.phonebook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Phonebook implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Contact> contacts;

    public Phonebook() {
        this.contacts = new ArrayList<>();
    }

    private Phonebook(List<Contact> contacts) {
        this.contacts = contacts;
    }

    // Menu actions
    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("The record added.");
    }

    public void remove(Contact currentContact) {
        contacts.remove(currentContact);
        System.out.println("The record removed!");
    }

    public int getCount() {
        return contacts.size();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    // Utility methods

    public void savePhonebook(String filename) {
        File file = new File(filename);
        try {
            boolean fileExists = file.exists();
            if (!fileExists) { fileExists = file.createNewFile(); }
            if (fileExists) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                    oos.writeObject(contacts);
                } catch (IOException e) {
                    System.out.println("IO exception while writing file");
                }
            } else {
                System.out.println("File could not be created.");
            }
        } catch (IOException e) {
            System.out.println("Phonebook could not be saved.");
        }
    }

    public static Phonebook loadPhonebook(String filename) {
        File file = new File(filename);
        if (file.exists() && file.isFile() && file.canRead()) {
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                Object contactList = ois.readObject();
                System.out.printf("open %s%n", filename);
                return new Phonebook((List<Contact>) contactList);
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
            } catch (IOException e) {
                System.out.println("IO exception!");
            } catch (ClassNotFoundException e) {
                System.out.println("An error occurred while deserializing: Are you sure this is the right file?");
            }
        }
        System.out.println("Phonebook could not be read from file: " + file.getAbsolutePath());
        return null;
    }

}
