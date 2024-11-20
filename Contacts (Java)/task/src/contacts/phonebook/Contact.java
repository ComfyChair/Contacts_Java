package contacts.phonebook;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

public abstract class Contact implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected static final String NO_DATA = "[no data]";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    protected String name;
    protected String number;
    private final LocalDateTime timeCreated;
    private LocalDateTime timeModified;

    protected List<String> editableFields;
    protected Map<String, Consumer<String>> setMethods = new HashMap<>();
    protected Map<String, Supplier<String>> getMethods = new HashMap<>();

    Contact() {
        this.timeCreated = LocalDateTime.now();
        this.timeModified = LocalDateTime.now();

        setMethods.put("name", (name) -> this.name = name);
        setMethods.put("number", this::setNumber);

        getMethods.put("name", () -> name);
        getMethods.put("number", () -> number);
    }

    private void setNumber(String number) {
        if (isValidNumber(number)) {
            this.number = number;
        } else {
            System.out.println("Invalid phone number: " + number);
            this.number = "";
        }
    }

    String get(String field) {
        return getMethods.containsKey(field) ? getMethods.get(field).get() : "";
    }

    public void set(String field, String value) {
        if (setMethods.containsKey(field)){
            setMethods.get(field).accept(value);
            timeModified = LocalDateTime.now();
        } else {
            System.out.println("Invalid field: " + field);
        }
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

    boolean hasNumber() {
        return number!= null && !number.isBlank();
    }

    public List<String> getEditableFields() {
        return editableFields;
    }

    public String getSearchString() {
        return getMethods.values().stream()
                .map(Supplier::get)
                .collect(Collectors.joining());
    }

    public abstract String getLongInfo();
    public abstract String getShortInfo();

    protected String phoneAndTime() {
        String number = this.number.isEmpty() ? NO_DATA : this.number;
        return "\nNumber: " + number +
                "\nTime created: " + formatter.format(timeCreated) +
                "\nTime last edit: " + formatter.format(timeModified) + "\n";
    }
}