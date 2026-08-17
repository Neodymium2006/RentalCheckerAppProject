package ph.edu.dlsu.lbycpob.rentalcheckerappproject.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.*;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.service.UnitService;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.service.UserService;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.Building;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private UnitService unitService;

    // Display login & role selection page (PDF Screen 1 & 2)
    @GetMapping({"/", "/login"})
    public String showLoginPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return redirectToDashboard(user);
        }
        return "index";
    }

    // Display registration page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Handle user registration (Only MANAGER or RENTER, Admin registration excluded)
    @PostMapping("/register")
    public String processRegistration(@RequestParam("name") String name,
                                      @RequestParam("password") String password,
                                      @RequestParam("userType") String userType,
                                      @RequestParam(value = "email", required = false) String email,
                                      Model model) {
        User newUser;

        // Instantiate concrete subclasses based on userType
        if ("MANAGER".equalsIgnoreCase(userType)) {
            newUser = new BuildingManager();
        } else {
            Renter renter = new Renter();
            renter.setEmail(email);
            newUser = renter;
        }

        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setUserType(userType.toUpperCase());

        userService.registerUser(newUser);
        model.addAttribute("message", "Registration successful! Please sign in.");
        return "index";
    }


}