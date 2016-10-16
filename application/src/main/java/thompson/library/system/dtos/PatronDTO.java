package thompson.library.system.dtos;

import java.sql.Timestamp;
import java.util.Optional;

public class PatronDto {
    private final Optional<Integer> patronid;
    private final String firstname;
    private final String lastname;
    private final String city;
    private final String state;
    private final int zipcode;
    private final String streetAddress;
    private final Timestamp joinDate;
    private final String email;
    private final long phone;
    private final boolean remotelibrary;
    private final String password;

    public PatronDto(String firstname,
                     String lastname,
                     String city,
                     String state,
                     int zipcode,
                     String streetAddress,
                     Timestamp joinDate,
                     String email,
                     long phone,
                     boolean remotelibrary,
                     String password) {
        this.patronid = Optional.empty();
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.joinDate = joinDate;
        this.email = email;
        this.phone = phone;
        this.remotelibrary = remotelibrary;
        this.password = password;
    }

    public PatronDto(Integer patronid,
                     String firstname,
                     String lastname,
                     String city,
                     String state,
                     int zipcode,
                     String streetAddress,
                     Timestamp joinDate,
                     String email,
                     long phone,
                     boolean remotelibrary,
                     String password) {
        this.patronid = Optional.of(patronid);
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.joinDate = joinDate;
        this.email = email;
        this.phone = phone;
        this.remotelibrary = remotelibrary;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public boolean isRemotelibrary() {
        return remotelibrary;
    }

    public Optional<Integer> getPatronid() {
        return patronid;
    }

    public String getPassword() {
        return password;
    }
}
