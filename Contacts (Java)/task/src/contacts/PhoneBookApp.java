package contacts;

import contacts.phonebook.Contact;
import contacts.phonebook.ContactFactory;
import contacts.phonebook.Phonebook;

import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static contacts.Action.*;

public class PhoneBookApp {
    private final Phonebook phonebook;
    private static final Scanner scanner = new Scanner(System.in);

    private PhoneBookApp(Phonebook phonebook) {
        this.phonebook = phonebook != null? phonebook : new Phonebook();
        mainMenu();
    }

    static void start(String path) {
        if (path.isEmpty()) {
            new PhoneBookApp(new Phonebook());
        } else {
            new PhoneBookApp(Phonebook.loadPhonebook(path));
        }
    }

    private void mainMenu() {
        showMenu("[menu]", EnumSet.of(ADD, LIST, SEARCH, COUNT, EXIT), EXIT.name());
        scanner.close();
        phonebook.savePhonebook("phonebook.db");
    }

    void record(String indexString) {
        //TODO: fix search list index should be mapped to phonebook index; maybe take Contact as parameter?
        try {
            Contact contact = phonebook.getContact(Integer.parseInt(indexString));
            if (contact != null) {
                System.out.println(contact.getLongInfo());
                showMenu("[record]", EnumSet.of(NUMBER, EDIT, DELETE, MENU), MENU.name());
            } else {
                System.out.println("Not a valid record index: " + indexString);
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid record index: " + indexString);
        }
    }

    void list() {
        List<Contact> contacts = phonebook.getContacts();
        listContacts(contacts);
        // TODO: fix no entries shown
        showMenu("[list]", EnumSet.of(NUMBER, BACK), BACK.name());
    }

    private void listContacts(List<Contact> contacts) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            System.out.printf("%d. %s%n", i + 1, contact.getShortInfo());
        }
    }

    public void searchAndPrint(String query) {
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        List<Contact> results = phonebook.getContacts().stream()
                .filter(c -> pattern.matcher(c.getSearchString()).find())
                .toList();
        listContacts(results);
    }

    public void search() {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();
        searchAndPrint(query);
        showMenu("[search]", EnumSet.of(NUMBER, BACK, AGAIN), "(AGAIN|BACK)");
    }

    private void showMenu(String name, EnumSet<Action> actions, String exitCmd) {
        String input;
        do {
            System.out.printf("\n%s Enter action (%s): ", name, Action.getActionList(actions));
            input = scanner.nextLine().toUpperCase();
            Action action = Action.getAction(input);
            if (action != null && actions.contains(action)) {
                action.action.accept(this, input);
            }
        } while (!input.matches(exitCmd));
    }

    void addContact() {
        Contact newContact = ContactFactory.createContact(scanner);
        phonebook.addContact(newContact);
    }

    void deleteContact(String indexString) {
        int index = Integer.parseInt(indexString);
        if (phonebook.getContact(index) != null) {
            phonebook.removeContact(index);
        }
    }

    void editContact(String indexString) {
        Contact contact = phonebook.getContact(Integer.parseInt(indexString));
        if (contact != null) {
            System.out.printf("Select a field %s: ", contact.getEditableFields().toString()
                    .replace("[", "(").replace("]", ")"));
            String fieldString = scanner.nextLine();
            System.out.printf("Enter new %s: ", fieldString);
            String newValue = scanner.nextLine();
            contact.set(fieldString, newValue);
            System.out.println("Saved");
        }
    }

    void countContacts() {
        System.out.printf("The Phone Book has %d records.%n", phonebook.getCount());
    }

    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "";
        PhoneBookApp.start(path);
    }
}
