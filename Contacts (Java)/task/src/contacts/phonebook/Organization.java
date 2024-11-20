package contacts.phonebook;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Organization extends Contact {
    private String address;

    Organization() {
        super();
        editableFields = List.of("name", "address", "number");
        setMethods.put("address", (Consumer<String> & Serializable) (address) -> this.address = address);
        getMethods.put("address", (Supplier<String> & Serializable) () -> address);
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
