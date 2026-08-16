package ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository;

import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    // Filter units based on their status (e.g., "AVAILABLE", "RENTED", "MAINTENANCE")
    List<Unit> findByStatus(String status);

}