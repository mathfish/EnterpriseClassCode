package thompson.library.system.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Branch implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int branchid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchid")
    private Set<BranchItem> branchItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currentlocation")
    private Set<BranchItem> locations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "forbranchid")
    private Set<Reservation> reservations;

    private String name;

    private String city;

    private int zipcode;

    private String streetaddress;

    private String state;

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    public Set<BranchItem> getBranchItems() {
        return branchItems;
    }

    public void setBranchItems(Set<BranchItem> branchItems) {
        this.branchItems = branchItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<BranchItem> getLocations() {
        return locations;
    }

    public void setLocations(Set<BranchItem> locations) {
        this.locations = locations;
    }

}
