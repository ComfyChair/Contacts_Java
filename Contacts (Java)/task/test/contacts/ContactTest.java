package contacts;

import junit.framework.TestCase;
import org.junit.Before;
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
                {"John", "Smith", "+0 (123) 456-789-ABcd", false},
                {"Adam", "Jones", "+0(123)456-789-9999", true},
                {"Linus", "Pauling", "(123) 234 345-456", false},
                {"Humpty", "Dumpty", "+49 1 345 654", true},
                {"Robert", "Palmer", "324 26 789", false},
                {"Nikola", "Tesla", "(123) (456) 789", true}
        });
    }
    Phonebook phonebook;
    String firstName;
    String lastName;
    String phone;
    boolean isInvalid;

    public ContactTest(String firstName, String lastName, String phoneNumber, boolean isInvalid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phoneNumber;
        this.isInvalid = isInvalid;
    }

    @Before
    public void setUp() {
        phonebook = new Phonebook();
    }

    @Test
    public void testAddContact() {
        Contact contact = new Contact(this.firstName, this.lastName, this.phone);
        phonebook.addContact(contact);
        assertTrue(phonebook.contacts.contains(contact));
    }

    @Test
    public void testPhoneValidation() {
        Contact contact = new Contact(this.firstName, this.lastName, this.phone);
        phonebook.addContact(contact);
        assertEquals(phonebook.getPhoneNumber(0).isEmpty(), this.isInvalid);
    }
}