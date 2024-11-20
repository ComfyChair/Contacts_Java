package contacts.phonebook;

import java.util.List;

public class Organization extends Contact {
    private String address;

    Organization() {
        super();
        editableFields = List.of("name", "address", "number");
        setMethods.put("address", (address) -> this.address = address);
        getMethods.put("address", () -> address);
    }

    @Override
    public String getLongInfo() {
        return "Organization name: " + name +
                "\nAddress: " + address +
                super.phoneAndTime();
    }

    @Override
    public String getShortInfo() {
        return name;
    }
}
