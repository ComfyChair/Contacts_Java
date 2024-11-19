package contacts;

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
        editFunctions.put("surname", this::setSurname);
        editFunctions.put("birthDate", this::setBirthDate);
        editFunctions.put("gender", this::setGender);
    }

    @Override
    public String getLongInfo() {
        String birthDate = this.birthDate == null ? NO_DATA : this.birthDate.toString();
        String gender = this.gender == Gender.UNKNOWN ? NO_DATA : this.gender.abbreviation;
        return "Name: " + name +
                "\nSurname: " + surname +
                "\nBirth date: " + birthDate +
                "\nGender: " + gender +
                super.phoneAndTime();
    }

    @Override
    String getShortInfo() {
        return String.format("%s %s", name, surname);
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

    private void setSurname(String surname) {
        this.surname = surname;
    }

    private void setGender(String genderString) {
        this.gender = Gender.getGender(genderString);
    }

    private enum Gender {
        MALE("M"), FEMALE("F"), OTHER("O"), UNKNOWN("");
        final String abbreviation;

        Gender(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        static Gender getGender(String genderString) {
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

