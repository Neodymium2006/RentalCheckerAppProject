package ph.edu.dlsu.lbycpob.rentalcheckerappproject.model;

import jakarta.persistence.*;

// JPA Entity annotation mapping this subclass to its own table linked to 'users'
@Entity
@Table(name = "building_managers")
@PrimaryKeyJoinColumn(name = "user_id") // Maps the primary key back to the parent User table ID
public class BuildingManager extends User { // Demonstrates Inheritance from abstract User class

    // Encapsulated Field: Specific contact phone number for unit maintenance/management inquiries
    @Column(nullable = false)
    private String phoneNumber;

    // Default No-Argument Constructor required by JPA/Hibernate
    public BuildingManager() {
        super();
    }

    // Parameterized Constructor initializing user attributes and setting role to "MANAGER"
    public BuildingManager(String name, String password, String phoneNumber) {
        super(name, password, "MANAGER");
        this.phoneNumber = phoneNumber;
    }

    // Polymorphic implementation returning the Manager portal view path
    @Override
    public String viewDashboard() {
        return "manager-dashboard";
    }

    // Polymorphic implementation returning direct contact info for manager inquiries
    @Override
    public String contact() {
        return "Contact Building Manager (" + getName() + ") at Phone: " + phoneNumber;
    }

    // Encapsulation Getter for phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Encapsulation Setter for phone number
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}