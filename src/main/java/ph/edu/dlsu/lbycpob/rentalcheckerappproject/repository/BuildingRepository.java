package ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Building;


@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
}
