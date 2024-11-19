package contacts;

import java.util.Objects;

final class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber = "";

    Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        setPhoneNumber(phoneNumber);
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getLastName() {
        return lastName;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        if (isValid(phoneNumber)){
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = "";
            System.out.println("Wrong number format!");
        }
    }

    boolean hasNumber() {
        return !phoneNumber.isEmpty();
    }

    private boolean isValid(String phoneNumber) {
        String symbols = "[a-zA-Z0-9]";
        String separator = "[\\s-]";
        String firstGroupWrapped = String.format("(\\(?%1$s+\\)?)", symbols);
        String secondGroupWrapped = String.format("%1$s+%2$s\\(?%1$s{2,}\\)?", symbols, separator);
        String pattern = String.format("\\+?(%1$s|%2$s)(%3$s%4$s{2,})*",
                firstGroupWrapped, secondGroupWrapped, separator, symbols);
        return phoneNumber.matches(pattern);
    }

    @Override
    public String toString() {
        String number = hasNumber() ? getPhoneNumber() : "[no number]";
        return String.format("%s %s, %s", firstName, lastName, number);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Contact contact)) return false;

        return Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(phoneNumber);
        return result;
    }

    public void edit(String fieldString, String newValue) {
        EditField editField = getField(fieldString);
        if (editField != null) {
            editField.edit(this, newValue);
            System.out.println("The record updated.");
        }
    }

    private static EditField getField(String typeString){
        try {
            return EditField.valueOf(typeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid field: " + typeString);
            return null;
        }
    }

    private enum EditField {
        NAME { @Override void edit(Contact contact, String value) { contact.setFirstName(value); } },
        SURNAME { @Override void edit(Contact contact, String value) { contact.setLastName(value); } },
        NUMBER { @Override void edit(Contact contact, String value) { contact.setPhoneNumber(value); } };

        abstract void edit(Contact contact, String newValue);
    }
}
