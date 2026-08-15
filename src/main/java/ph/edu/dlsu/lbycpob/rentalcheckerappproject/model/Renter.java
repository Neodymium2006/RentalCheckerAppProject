package ph.edu.dlsu.lbycpob.rentalcheckerappproject.model;

import jakarta.persistence.*;

// JPA Entity annotation mapping this subclass to its own table linked to 'users'
@Entity
@Table(name = "renters")
@PrimaryKeyJoinColumn(name = "user_id") // Maps primary key back to parent User table ID
public class Renter extends User { // Demonstrates Inheritance from abstract User class

    // Encapsulated Field: Email address for tenant communications
    @Column(nullable = false)
    private String email;

    // Default No-Argument Constructor required by JPA/Hibernate
    public Renter() {
        super();
    }

    // Parameterized Constructor initializing user attributes and setting role to "RENTER"
    public Renter(String name, String password, String email) {
        super(name, password, "RENTER");
        this.email = email;
    }

    // Polymorphic implementation returning the Renter portal view path
    @Override
    public String viewDashboard() {
        return "renter-dashboard";
    }

    // Polymorphic implementation returning contact info for tenant inquiries
    @Override
    public String contact() {
        return "Contact Renter (" + getName() + ") at Email: " + email;
    }

    // Encapsulation Getter for email address
    public String getEmail() {
        return email;
    }

    // Encapsulation Setter for email address
    public void setEmail(String email) {
        this.email = email;
    }
}