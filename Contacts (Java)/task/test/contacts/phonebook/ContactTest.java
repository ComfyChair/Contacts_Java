package contacts.phonebook;

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
                {"John", "Smith", "+0 (123) 456-789-ABcd", true, "22.12.1976", "1976-12-22"},
                {"Adam", "Jones", "+0(123)456-789-9999", false, "23/06/2013", "2013-06-23"},
                {"Linus", "Pauling", "(123) 234 345-456", true, "2009-05-26", "2009-05-26"},
                {"Humpty", "Dumpty", "+49 1 345 654", false, "32.05.1874", "[no data]"},
                {"Robert", "Palmer", "324 26 789", true, "12/13/1972", "[no data]"},
                {"Nikola", "Tesla", "(123) (456) 789", false, "20-12-2000", "[no data]"},
        });
    }
    String name;
    String surname;
    String number;
    boolean isValid;
    String setBirthdate;
    String expectedBirthdate;

    public ContactTest(String name, String surname, String phoneNumber, boolean isValidPhone,
                       String setBirthdate, String expectedBirthdate) {
        this.name = name;
        this.surname = surname;
        this.number = phoneNumber;
        this.isValid = isValidPhone;
        this.setBirthdate = setBirthdate;
        this.expectedBirthdate = expectedBirthdate;
    }

    @Test
    public void testSetPerson() {
        Person person = new Person();
        person.set("name", this.name);
        assertEquals(this.name, person.name);
        person.set("surname", this.surname);
        assertEquals(this.surname, person.get("surname"));
    }

    @Test
    public void testPhoneValidation() {
        Contact contact = new Person();
        contact.set("name", this.name);
        contact.set("surname", this.surname);
        contact.set("number", this.number);
        assertEquals(contact.hasNumber(), this.isValid);
    }

    @Test
    public void testBirthDateValidation() {
        Contact contact = new Person();
        contact.set("birthDate", this.setBirthdate);
        assertEquals(this.expectedBirthdate, contact.get("birthDate"));
    }
}