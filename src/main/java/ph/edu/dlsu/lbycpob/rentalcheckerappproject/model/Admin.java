package ph.edu.dlsu.lbycpob.rentalcheckerappproject.model;

import jakarta.persistence.*;

// JPA Entity annotation indicating this class maps to its own table linked to 'users'
@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id") // Links the Admin table primary key back to the User parent ID
public class Admin extends User { // Demonstrates Inheritance from the abstract User base class

    // Default No-Argument Constructor required by JPA/Hibernate
    public Admin() {
        super();
    }

    // Parameterized Constructor initializing fields and setting role to "ADMIN"
    public Admin(String name, String password) {
        super(name, password, "ADMIN");
    }

    // Polymorphic implementation of viewDashboard for Admin role
    @Override
    public String viewDashboard() {
        // Returns the template path for the Admin portal
        return "admin-dashboard";
    }

    // Polymorphic implementation of contact for Admin role
    @Override
    public String contact() {
        // Custom contact message for system administrators
        return "Contact System Administrator at support@rentalchecker.com";
    }
}