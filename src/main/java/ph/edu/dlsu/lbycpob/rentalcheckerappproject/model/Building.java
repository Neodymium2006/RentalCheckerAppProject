package ph.edu.dlsu.lbycpob.rentalcheckerappproject.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

// JPA Entity annotation mapping this class to the 'buildings' database table
@Entity
@Table(name = "buildings")
public class Building {

    // Primary Key: Auto-incrementing ID for database records
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Encapsulated Field: Name of the property or building
    @Column(nullable = false)
    private String name;

    // Encapsulated Field: Physical address of the property
    @Column(nullable = false)
    private String address;

    // Relationship: One Building Manager manages one or more Buildings
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private BuildingManager manager;

    // Relationship: One Building contains multiple rental Units (One-to-Many mapping)
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unit> units = new ArrayList<>();

    // Default No-Argument Constructor required by JPA/Hibernate
    public Building() {}

    // Parameterized Constructor initializing essential property details
    public Building(String name, String address, BuildingManager manager) {
        this.name = name;
        this.address = address;
        this.manager = manager;
    }

    // Encapsulation Getter for Building ID
    public Long getId() {
        return id;
    }

    // Encapsulation Getter for Building Name
    public String getName() {
        return name;
    }

    // Encapsulation Setter for Building Name
    public void setName(String name) {
        this.name = name;
    }

    // Encapsulation Getter for Address
    public String getAddress() {
        return address;
    }

    // Encapsulation Setter for Address
    public void setAddress(String address) {
        this.address = address;
    }

    // Encapsulation Getter for assigned Building Manager
    public BuildingManager getManager() {
        return manager;
    }

    // Encapsulation Setter for assigned Building Manager
    public void setManager(BuildingManager manager) {
        this.manager = manager;
    }

    // Encapsulation Getter for list of associated Units
    public List<Unit> getUnits() {
        return units;
    }

    // Helper method to add a Unit to this Building
    public void addUnit(Unit unit) {
        units.add(unit);
        unit.setBuilding(this);
    }
}