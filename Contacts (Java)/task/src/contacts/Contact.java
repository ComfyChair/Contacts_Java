package contacts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

abstract class Contact {
    protected static final String NO_DATA = "[no data]";
    protected String name;
    private String number;
    private final LocalDateTime timeCreated;
    private LocalDateTime timeModified;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    protected List<String> editableFields;
    protected Map<String, Consumer<String>> editFunctions = new HashMap<>();

    Contact() {
        this.timeCreated = LocalDateTime.now();
        this.timeModified = LocalDateTime.now();
        editFunctions.put("name", this::setName);
        editFunctions.put("number", this::setPhone);
    }

    private void setPhone(String number) {
        if (isValidNumber(number)) {
            this.number = number;
        } else {
            System.out.println("Invalid phone number: " + number);
            this.number = "";
        }
    }

    private void setName(String name) {
        this.name = name;
    }

    boolean isValidNumber(String phoneNumber) {
        String symbols = "[a-zA-Z0-9]";
        String separator = "[\\s-]";
        String firstGroupWrapped = String.format("(\\(?%1$s+\\)?)", symbols);
        String secondGroupWrapped = String.format("%1$s+%2$s\\(?%1$s{2,}\\)?", symbols, separator);
        String pattern = String.format("\\+?(%1$s|%2$s)(%3$s%4$s{2,})*",
                firstGroupWrapped, secondGroupWrapped, separator, symbols);
        return phoneNumber.matches(pattern);
    }

    void edit(String fieldString, String newValue) {
        if (editFunctions.containsKey(fieldString)) {
            editFunctions.get(fieldString).accept(newValue);
            timeModified = LocalDateTime.now();
            System.out.println("The record updated!");
        }
    }

    boolean hasNumber() {
        return number!= null && !number.isBlank();
    }

    List<String> getEditableFields() {
        return editableFields;
    }

    abstract String getLongInfo();
    abstract String getShortInfo();

    protected String phoneAndTime() {
        String number = this.number.isEmpty() ? NO_DATA : this.number;
        return "\nNumber: " + number +
                "\nTime created: " + formatter.format(timeCreated) +
                "\nTime last edit: " + formatter.format(timeModified);
    }
}