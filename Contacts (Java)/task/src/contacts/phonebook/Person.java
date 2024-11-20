package contacts.phonebook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Person extends Contact {
    private String surname;
    private LocalDate birthDate;
    private Gender gender;

    Person() {
        super();
        editableFields = List.of("name", "surname", "birth date", "gender", "number");

        setMethods.put("surname", (surname) -> this.surname = surname);
        setMethods.put("birthDate", this::setBirthDate);
        setMethods.put("gender", (gender) -> this.gender = Gender.fromString(gender));

        getMethods.put("surname", () -> surname);
        getMethods.put("birthDate", this::getBirthDate);
        getMethods.put("gender", this::getGender);
    }

    private void setBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e1) {
            try {
                this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e2) {
                try {
                    this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                } catch (DateTimeParseException e3) {
                    System.out.println("Invalid birth date: " + birthDate);
                    this.birthDate = null;
                }
            }
        }
    }

    private String getGender() {
        return this.gender == Gender.UNKNOWN ? NO_DATA : this.gender.abbreviation;
    }

    private String getBirthDate() {
        return this.birthDate == null ? NO_DATA : this.birthDate.toString();
    }

    @Override
    public String getShortInfo() {
        return String.format("%s %s", name, surname);
    }

    @Override
    public String getLongInfo() {
        return "Name: " + name +
                "\nSurname: " + surname +
                "\nBirth date: " + getBirthDate() +
                "\nGender: " + getGender() +
                super.phoneAndTime();
    }

    private enum Gender {
        MALE("M"), FEMALE("F"), OTHER("O"), UNKNOWN("");
        final String abbreviation;

        Gender(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        static Gender fromString(String genderString) {
            String query = genderString.toUpperCase();
            for (Gender gender : Gender.values()) {
                if (gender.abbreviation.equals(query) || gender.name().equals(query)) {
                    return gender;
                }
            }
            System.out.println("Unknown gender: " + query);
            return UNKNOWN;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}

