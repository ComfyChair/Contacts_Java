package contacts;

record Contact(String firstName, String lastName, String phoneNumber) {
    Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        System.out.println("A record created!");
    }
}
