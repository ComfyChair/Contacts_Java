package contacts.phonebook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Phonebook implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Contact> contacts = new ArrayList<>();

    // Menu actions
    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("The record added.");
    }

    public void removeContact(int index) {
        contacts.remove(index);
        System.out.println("The record removed!");
    }

    public Contact getContact(int index) {
        if (index < 0 || index >= contacts.size()) {
            return null;
        }
        return contacts.get(index);
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
                    oos.writeObject(this);
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
                Object phonebookObject = ois.readObject();
                System.out.printf("open %s%n", filename);
                return (Phonebook) phonebookObject;
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
