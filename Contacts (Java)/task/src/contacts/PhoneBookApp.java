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
    private List<Contact> currentContactList;
    private Contact currentContact;
    private static final Scanner scanner = new Scanner(System.in);

    private PhoneBookApp(Phonebook phonebook) {
        this.phonebook = phonebook != null? phonebook : new Phonebook();
        this.currentContactList = this.phonebook.getContacts();
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
        showMenu("[menu]", EnumSet.of(ADD, LIST, SEARCH, COUNT, EXIT), EnumSet.of(EXIT));
        scanner.close();
        phonebook.savePhonebook("phonebook.db");
    }

    void recordMenu(String indexString) {
        int index = Integer.parseInt(indexString) - 1;
        if (index >= 0 && index < this.currentContactList.size()) {
            currentContact = currentContactList.get(index);
            if (currentContact != null) {
                System.out.println(currentContact.getLongInfo());
                showMenu("[record]", EnumSet.of(EDIT, DELETE, MENU), EnumSet.of(MENU, DELETE));
            } else {
                System.out.println("Not a valid record index: " + indexString);
            }
        } else {
            System.out.println("Invalid index: " + indexString);
        }
    }

    void listMenu() {
        currentContactList = phonebook.getContacts();
        if (currentContactList.isEmpty()) {
            System.out.println("No contacts in phonebook");
        } else {
            listCurrentContacts();
            showMenu("[list]", EnumSet.of(NUMBER, BACK), EnumSet.of(NUMBER, BACK));
        }
    }

    public void search() {
        if (phonebook.getContacts().isEmpty()) {
            System.out.println("No contacts in phonebook");
        } else {
            System.out.print("Enter search query: ");
            String query = scanner.nextLine();
            Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
            currentContactList = phonebook.getContacts().stream()
                    .filter(c -> pattern.matcher(c.getSearchString()).find())
                    .toList();
            System.out.printf(currentContactList.isEmpty() ?
                    "No search results." :
                    "Found %s results: %n", currentContactList.size());
            listCurrentContacts();
            showMenu("[search]", EnumSet.of(NUMBER, BACK, AGAIN), EnumSet.of(NUMBER, BACK, AGAIN));
        }
    }

    private void showMenu(String name, EnumSet<Action> actions, EnumSet<Action> exitActions) {
        String input;
        Action action;
        do {
            System.out.printf("\n%s Enter action (%s): ", name, Action.getActionList(actions));
            input = scanner.nextLine().strip().toUpperCase();
            action = Action.getAction(input);
            if (action != null && actions.contains(action)) {
                action.action.accept(this, input);
            } else {
                System.out.println("Invalid action: " + input);
            }
        } while (!exitActions.contains(action));
    }

    void addContact() {
        Contact newContact = ContactFactory.createContact(scanner);
        if (newContact != null) {
            phonebook.addContact(newContact);
        }
    }

    void deleteContact() {
        if (currentContact != null) {
            phonebook.remove(currentContact);
        }
    }

    void editContact() {
        if (currentContact != null) {
            System.out.printf("Select a field %s: ", currentContact.getEditableFields().toString()
                    .replace("[", "(").replace("]", ")"));
            String fieldString = scanner.nextLine();
            System.out.printf("Enter new %s: ", fieldString);
            String newValue = scanner.nextLine();
            currentContact.set(fieldString, newValue);
            System.out.println("Saved");
            System.out.println(currentContact.getLongInfo());
        }
    }

    void countContacts() {
        System.out.printf("The Phone Book has %d records.%n", phonebook.getCount());
    }

    private void listCurrentContacts() {
        for (int i = 0; i < currentContactList.size(); i++) {
            Contact contact = currentContactList.get(i);
            System.out.printf("%d. %s%n", i + 1, contact.getShortInfo());
        }
    }

    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "";
        PhoneBookApp.start(path);
    }
}
