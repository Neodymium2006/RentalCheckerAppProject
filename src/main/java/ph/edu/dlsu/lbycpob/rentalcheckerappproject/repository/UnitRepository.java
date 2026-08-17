package ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository;

import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    // Filter units based on their status (e.g., "AVAILABLE", "RENTED", "MAINTENANCE")
    List<Unit> findByStatus(String status);

    // Filter units belonging to a specific building. The underscore tells
    // Spring Data to traverse the "building" association and match on its
    // "id" field — Unit itself has no plain "buildingId" property, only a
    // Building object, so findByBuildingId() alone would not resolve.
    List<Unit> findByBuilding_Id(Long buildingId);
}