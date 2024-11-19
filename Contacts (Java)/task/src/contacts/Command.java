package contacts;

enum Command {
    ADD { @Override void execute(PhoneBookApp app) { app.addContact(); } },
    REMOVE { @Override void execute(PhoneBookApp app) { app.removeContact(); } },
    EDIT { @Override void execute(PhoneBookApp app) { app.editContact(); } },
    COUNT { @Override void execute(PhoneBookApp app) { app.countContacts(); } },
    INFO { @Override void execute(PhoneBookApp app) { app.info(); } };

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
