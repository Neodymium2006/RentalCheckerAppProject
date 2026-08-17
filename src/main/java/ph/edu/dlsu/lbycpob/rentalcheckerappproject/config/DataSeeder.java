package ph.edu.dlsu.lbycpob.rentalcheckerappproject.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Building;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.BuildingManager;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Unit;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository.BuildingRepository;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.service.UserService;

// Runs once when the app starts against an empty database: creates three
// buildings (A, B, C), each with its own manager and a full 5-floor x
// 2-unit layout (10 units per building, 30 total). Safe to leave in
// place — it checks buildingRepository.count() and no-ops once data
// already exists, so it won't duplicate rows on every restart.
@Component
public class DataSeeder implements CommandLineRunner {

    private final BuildingRepository buildingRepository;
    private final UserService userService;

    public DataSeeder(BuildingRepository buildingRepository, UserService userService) {
        this.buildingRepository = buildingRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (buildingRepository.count() > 0) {
            return; // Already seeded on a previous run.
        }

        seedBuilding("Building A", "101 Taft Avenue, Manila", "Alex Chan", "0917-100-0001");
        seedBuilding("Building B", "22 Estrada Street, Manila", "Maria Santos", "0917-100-0002");
        seedBuilding("Building C", "5 Vito Cruz Extension, Manila", "Jordan Reyes", "0917-100-0003");
    }

    // Creates the manager, the building, and its 10 units, then saves the
    // building once — Building's cascade = CascadeType.ALL on "units"
    // persists all of them in the same call.
    private void seedBuilding(String buildingName, String address, String managerName, String managerPhone) {
        // Registered through UserService (not the repository directly) so
        // it goes through whatever password handling processRegistration()
        // already uses — the account can log in normally afterward.
        BuildingManager manager = new BuildingManager(managerName, "manager123", managerPhone);
        userService.registerUser(manager);

        Building building = new Building(buildingName, address, manager);

        String letter = buildingName.substring(buildingName.length() - 1); // "A" / "B" / "C"
        double baseRent = 10000.0;

        for (int floor = 1; floor <= 5; floor++) {
            for (int unitOnFloor = 1; unitOnFloor <= 2; unitOnFloor++) {
                // e.g. A-101, A-102 ... A-501, A-502
                String unitNumber = letter + "-" + floor + "0" + unitOnFloor;
                double monthlyRent = baseRent + (floor - 1) * 500.0; // higher floors cost slightly more
                Unit unit = new Unit(unitNumber, monthlyRent, "AVAILABLE", building);
                building.addUnit(unit); // sets unit.building = this, per Building's helper
            }
        }

        buildingRepository.save(building);
    }
}