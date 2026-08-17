package ph.edu.dlsu.lbycpob.rentalcheckerappproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Building;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Unit;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository.BuildingRepository;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository.UnitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    // NEW: needed so getBuildingById() / listing buildings can work.
    // This was missing before, which is why the compiler couldn't resolve
    // "Building" — the class was never imported or wired into this service.
    @Autowired
    private BuildingRepository buildingRepository;

    // Returns every unit currently open for rent (renter dashboard).
    public List<Unit> getAvailableUnits() {
        return unitRepository.findByStatus("AVAILABLE");
    }

    // Attempts to rent a unit; fails silently (returns false) if the unit
    // does not exist or is no longer available.
    public boolean rentUnit(Long unitId) {
        Optional<Unit> unitOpt = unitRepository.findById(unitId);
        if (unitOpt.isPresent()) {
            Unit unit = unitOpt.get();
            if ("AVAILABLE".equals(unit.getStatus())) {
                unit.setStatus("RENTED");
                unitRepository.save(unit);
                return true; // Successfully rented
            }
        }
        return false; // Unit not found or not available
    }

    // Used by /building-details — fetch one building by id so the page has
    // something to render (name, address, manager, etc.).
    public Building getBuildingById(Long buildingId) {
        return buildingRepository.findById(buildingId).orElse(null);
    }

    // Used by /building-details — fetch just the units that belong to a
    // given building, so the floor stack (3 floors x 2 units) can be built
    // even if Building itself does not keep a units list.
    public List<Unit> getUnitsByBuildingId(Long buildingId) {
        return unitRepository.findByBuilding_Id(buildingId);
    }

    // Used by /room-details — fetch a single unit for the detail view.
    public Unit getUnitById(Long unitId) {
        return unitRepository.findById(unitId).orElse(null);
    }
}