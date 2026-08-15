package ph.edu.dlsu.lbycpob.rentalcheckerappproject.model;

import jakarta.persistence.*;

// JPA Entity annotation mapping this class to the 'units' database table
@Entity
@Table(name = "units")
public class Unit {

    // Primary Key: Auto-incrementing ID for database records
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Encapsulated Field: Unit or apartment number (e.g., "101A", "302B")
    @Column(nullable = false)
    private String unitNumber;

    // Encapsulated Field: Monthly rental price
    @Column(nullable = false)
    private double monthlyRent;

    // Encapsulated Field: Current status of unit ("AVAILABLE", "RENTED", "MAINTENANCE")
    @Column(nullable = false)
    private String status;

    // Relationship: Many Units belong to One Building
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    // Relationship: One Unit can be occupied by One Renter (Null if available)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id")
    private Renter renter;

    // Default No-Argument Constructor required by JPA/Hibernate
    public Unit() {}

    // Parameterized Constructor initializing unit attributes
    public Unit(String unitNumber, double monthlyRent, String status, Building building) {
        this.unitNumber = unitNumber;
        this.monthlyRent = monthlyRent;
        this.status = status;
        this.building = building;
        this.renter = null; // Unoccupied upon creation
    }

    // Encapsulation Getter for Unit ID
    public Long getId() {
        return id;
    }

    // Encapsulation Getter for Unit Number
    public String getUnitNumber() {
        return unitNumber;
    }

    // Encapsulation Setter for Unit Number
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    // Encapsulation Getter for Monthly Rent
    public double getMonthlyRent() {
        return monthlyRent;
    }

    // Encapsulation Setter for Monthly Rent
    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    // Encapsulation Getter for Unit Status
    public String getStatus() {
        return status;
    }

    // Encapsulation Setter for Unit Status
    public void setStatus(String status) {
        this.status = status;
    }

    // Encapsulation Getter for associated Building
    public Building getBuilding() {
        return building;
    }

    // Encapsulation Setter for associated Building
    public void setBuilding(Building building) {
        this.building = building;
    }

    // Encapsulation Getter for assigned Renter
    public Renter getRenter() {
        return renter;
    }

    // Encapsulation Setter for assigned Renter
    public void setRenter(Renter renter) {
        this.renter = renter;
    }
}