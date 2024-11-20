package contacts;

import java.util.EnumSet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

enum Action {
    // main menu commands
    ADD     ("add", (app, ignored) -> app.addContact()),
    LIST    ("list", (app, ignored) -> app.list()),
    SEARCH  ("search", (app, ignored) -> app.search()),
    COUNT   ("count", (app, ignored) -> app.countContacts()),
    EXIT    ("exit", (ignored1, ignored2) -> {}),
    // sub menu actions
    NUMBER  ("[number]", PhoneBookApp::record),
    AGAIN   ("again", ((app, ignored) -> app.search())),
    EDIT    ("edit", PhoneBookApp::editContact),
    DELETE  ("delete", PhoneBookApp::deleteContact),
    BACK    ("back", (ignored1, ignored2) -> {}),
    MENU    ("menu", (ignored1, ignored2) -> {}),
 ;
    final BiConsumer<PhoneBookApp, String> action;
    final String menuString;

    Action(String menuString, BiConsumer<PhoneBookApp, String> action) {
        this.menuString = menuString;
        this.action = action;
    }

    /**
     * Factory method to create a command from a Command name
     * @param typeString String that matches the Command name
     * @return the corresponding Command
     */
    static Action getAction(String typeString){
        try {
            Integer.parseInt(typeString);
            return NUMBER;
        } catch (NumberFormatException ex) {
            try {
                return Action.valueOf(typeString.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command: " + typeString);
                return null;
            }
        }
    }

    public static String getActionList(EnumSet<Action> actions) {
        return actions.stream().map(c -> c.menuString)
                .collect(Collectors.joining(", "));
    }
}
