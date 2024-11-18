package contacts;

final class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber = "";

    Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        setPhoneNumber(phoneNumber);
        System.out.println("A record created!");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (isValid(phoneNumber)){
            this.phoneNumber = phoneNumber;
        }
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
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
