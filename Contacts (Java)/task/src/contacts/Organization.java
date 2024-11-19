package contacts;

import java.util.List;

public class Organization extends Contact {
    private String address;

    Organization() {
        super();
        editableFields = List.of("name", "address", "number");
        editFunctions.put("address", this::setAddress);
    }

    private void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getLongInfo() {
        return "Organization name: " + name +
                "\nAddress: " + address +
                super.phoneAndTime();
    }

    @Override
    String getShortInfo() {
        return name;
    }
}
