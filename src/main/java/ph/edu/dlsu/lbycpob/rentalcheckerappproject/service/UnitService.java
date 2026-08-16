package ph.edu.dlsu.lbycpob.rentalcheckerappproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Unit;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository.UnitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    // Retrieve all units that are currently available for rent
    public List<Unit> getAvailableUnits() {
        return unitRepository.findByStatus("AVAILABLE");
    }

    // Process renting a unit by changing its status
    public boolean rentUnit(Long unitId) {
        Optional<Unit> unitOptional = unitRepository.findById(unitId);

        if (unitOptional.isPresent()) {
            Unit unit = unitOptional.get();
            if ("AVAILABLE".equals(unit.getStatus())) {
                unit.setStatus("RENTED");
                unitRepository.save(unit);
                return true; // Successfully rented
            }
        }
        return false; // Unit not found or not available
    }
}