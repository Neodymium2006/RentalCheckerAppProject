package ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Abstract method to query user entity by the 'name' field
    Optional<User> findByName(String name);

}