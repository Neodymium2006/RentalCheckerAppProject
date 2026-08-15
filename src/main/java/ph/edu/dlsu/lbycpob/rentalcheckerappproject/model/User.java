package ph.edu.dlsu.lbycpob.rentalcheckerappproject.model;

import jakarta.persistence.*;

// Defines this class as a database entity mapped to the 'users' table
@Entity
@Table(name = "users")
// JOINED inheritance strategy creates separate tables for Admin, Manager, and Renter linked to this parent table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    // Primary Key: Automatically generated auto-incrementing ID for database records
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Encapsulated Field: Encrypted/plain user name, required field in database
    @Column(nullable = false)
    private String name;

    // Encapsulated Field: Account password, required field in database
    @Column(nullable = false)
    private String password;

    // Encapsulated Field: Role discriminator ("ADMIN", "MANAGER", "RENTER")
    @Column(nullable = false)
    private String userType;

    // Default No-Argument Constructor required by JPA/Hibernate ORM framework
    public User() {}

    // Parameterized Constructor to easily initialize new user instances
    public User(String name, String password, String userType) {
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    // Abstract Method: Demonstrates Polymorphism. Each role will implement its own dashboard view logic
    public abstract String viewDashboard();

    // Abstract Method: Demonstrates Polymorphism. Each role will implement its own contact logic
    public abstract String contact();

    // Getter for Primary Key ID
    public Long getId() {
        return id;
    }

    // Getter for Name field
    public String getName() {
        return name;
    }

    // Setter for Name field
    public void setName(String name) {
        this.name = name;
    }

    // Getter for Password field
    public String getPassword() {
        return password;
    }

    // Setter for Password field
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for User Role Type
    public String getUserType() {
        return userType;
    }

    // Setter for User Role Type
    public void setUserType(String userType) {
        this.userType = userType;
    }
}