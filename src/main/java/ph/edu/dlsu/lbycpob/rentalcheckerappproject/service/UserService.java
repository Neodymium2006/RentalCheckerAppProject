package ph.edu.dlsu.lbycpob.rentalcheckerappproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.User;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Authenticate user credentials based on name and password
    public User authenticateUser(String name, String password) {
        Optional<User> userOptional = userRepository.findByName(name);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Authentication failed
    }

    // Save user entity instance
    public User registerUser(User user) {
        return userRepository.save(user);
    }
}