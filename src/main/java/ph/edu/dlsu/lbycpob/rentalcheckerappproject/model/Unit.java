package ph.edu.dlsu.lbycpob.rentalcheckerappproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String unitNumber;

    @Column(nullable = false)
    private double monthlyRent;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id")
    private Renter renter;

    public Unit() {}

    // The ONLY parameterized constructor — always takes floor as the 5th
    // argument. If your IDE previously auto-generated a second, 4-argument
    // overload of this constructor, delete it; that stray overload is the
    // likely cause of "status" ending up null.
    public Unit(String unitNumber, double monthlyRent, String status, Building building, int floor) {
        this.unitNumber = unitNumber;
        this.monthlyRent = monthlyRent;
        this.status = status;
        this.building = building;
        this.floor = floor;
        this.renter = null;
    }

    public Long getId() { return id; }

    public String getUnitNumber() { return unitNumber; }
    public void setUnitNumber(String unitNumber) { this.unitNumber = unitNumber; }

    public double getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(double monthlyRent) { this.monthlyRent = monthlyRent; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public Building getBuilding() { return building; }
    public void setBuilding(Building building) { this.building = building; }

    public Renter getRenter() { return renter; }
    public void setRenter(Renter renter) { this.renter = renter; }

    public boolean isAvailable() {
        return "AVAILABLE".equals(status);
    }
}