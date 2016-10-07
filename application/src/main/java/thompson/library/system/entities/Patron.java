package thompson.library.system.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patronid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patronid")
    private Set<Checkout> checkouts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patronid")
    private Set<Reservation> reservations;
    private String firstname;
    private String lastname;
    private String city;
    private String state;
    private int zipcode;
    private String streetaddress;
    private java.sql.Timestamp joindate;
    private long phone;
    private String password;
    private boolean remotelibrary;
    private String email;

    public int getPatronid() {
        return patronid;
    }

    public void setPatronid(int patronid) {
        this.patronid = patronid;
    }

    public Set<Checkout> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(Set<Checkout> checkouts) {
        this.checkouts = checkouts;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String streetaddress) {
        this.streetaddress = streetaddress;
    }

    public Timestamp getJoindate() {
        return joindate;
    }

    public void setJoindate(Timestamp joindate) {
        this.joindate = joindate;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public boolean isRemotelibrary() {
        return remotelibrary;
    }

    public void setRemotelibrary(boolean remotelibrary) {
        this.remotelibrary = remotelibrary;
    }
}
