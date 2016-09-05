package thompson.enterprise.design.hw1;

import java.time.LocalDate;

/**
 * Class used in Response methods. Used as a data structure for first name, sex, birth date, and anniversary.
 */
public class Person {
    private String firstName;
    private Sex sex;
    private LocalDate birthdate;
    private LocalDate anniversary;

    public Person(String firstName, Sex sex, LocalDate birthdate, LocalDate anniversary) {
        this.firstName = firstName;
        this.sex = sex;
        this.birthdate = birthdate;
        this.anniversary = anniversary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(LocalDate anniversary) {
        this.anniversary = anniversary;
    }
}
