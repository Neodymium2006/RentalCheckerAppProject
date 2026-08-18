package ph.edu.dlsu.lbycpob.rentalcheckerappproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Building;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Renter;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Unit;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository.BuildingRepository;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository.UnitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    public List<Unit> getAvailableUnits() {
        return unitRepository.findByStatus("AVAILABLE");
    }

    // Attempts to rent a unit to the given renter. Fails (returns false) if
    // the unit is not available, OR if this renter already has an active
    // lease elsewhere — a renter may only hold one unit at a time.
    public boolean rentUnit(Long unitId, Renter renter) {
        if (getCurrentUnitForRenter(renter.getId()) != null) {
            return false; // already renting somewhere else
        }

        Optional<Unit> unitOpt = unitRepository.findById(unitId);
        if (unitOpt.isPresent()) {
            Unit unit = unitOpt.get();
            if ("AVAILABLE".equals(unit.getStatus())) {
                unit.setStatus("RENTED");
                unit.setRenter(renter);
                unitRepository.save(unit);
                return true;
            }
        }
        return false;
    }

    // Used by the "Current Unit" screen. Returns the first matching unit
    // if — due to leftover bad data — more than one is found, instead of
    // throwing.
    public Unit getCurrentUnitForRenter(Long renterId) {
        List<Unit> units = unitRepository.findByRenter_Id(renterId);
        return units.isEmpty() ? null : units.get(0);
    }

    // Releases a renter's current unit: flips it back to AVAILABLE and
    // clears the renter reference. No-op if the renter has no active lease.
    public void terminateLease(Long renterId) {
        Unit unit = getCurrentUnitForRenter(renterId);
        if (unit != null) {
            unit.setStatus("AVAILABLE");
            unit.setRenter(null);
            unitRepository.save(unit);
        }
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Building getBuildingById(Long buildingId) {
        return buildingRepository.findById(buildingId).orElse(null);
    }

    public List<Unit> getUnitsByBuildingId(Long buildingId) {
        return unitRepository.findByBuilding_Id(buildingId);
    }

    public Unit getUnitById(Long unitId) {
        return unitRepository.findById(unitId).orElse(null);
    }
}