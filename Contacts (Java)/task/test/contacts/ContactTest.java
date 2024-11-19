package contacts;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ContactTest extends TestCase {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"John", "Smith", "+0 (123) 456-789-ABcd", true},
                {"Adam", "Jones", "+0(123)456-789-9999", false},
                {"Linus", "Pauling", "(123) 234 345-456", true},
                {"Humpty", "Dumpty", "+49 1 345 654", false},
                {"Robert", "Palmer", "324 26 789", true},
                {"Nikola", "Tesla", "(123) (456) 789", false}
        });
    }
    String firstName;
    String lastName;
    String phone;
    boolean isValid;

    public ContactTest(String firstName, String lastName, String phoneNumber, boolean isValid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phoneNumber;
        this.isValid = isValid;
    }

    @Test
    public void testAddPerson() {
        Phonebook phonebook = new Phonebook();
        Contact contact = new Person();
        contact.edit("name", this.firstName);
        contact.edit("lastName", this.lastName);
        contact.edit("number", this.phone);
        phonebook.addContact(contact);
        assertTrue(phonebook.contains(contact));
        assertEquals(phonebook.getContacts().getLast().name, this.firstName);
    }

    @Test
    public void testPhoneValidation() {
        Contact contact = new Person();
        contact.edit("name", this.firstName);
        contact.edit("lastName", this.lastName);
        contact.edit("number", this.phone);
        assertEquals(contact.hasNumber(), this.isValid);
    }
}