package thompson.enterprise.design.hw1;

import java.time.LocalDate;

/**
 * Created by jonathanthompson on 9/4/16.
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
