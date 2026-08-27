package ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository;

import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findByStatus(String status);

    List<Unit> findByBuilding_Id(Long buildingId);

    // Changed from Optional<Unit> to List<Unit> — a renter should only
    // ever have one active lease, but querying as a list means leftover
    // bad data (from before rentUnit() tracked the renter) won't crash
    // the app with a NonUniqueResultException.
    List<Unit> findByRenter_Id(Long renterId);
}