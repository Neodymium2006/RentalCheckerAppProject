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

    // Handle login form submission and route user according to selected role
    @PostMapping("/login")
    public String processLogin(@RequestParam("name") String name,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {
        User loggedInUser = userService.authenticateUser(name, password);

        if (loggedInUser != null) {
            session.setAttribute("user", loggedInUser);
            return redirectToDashboard(loggedInUser);
        } else {
            model.addAttribute("error", "Invalid name or password!");
            return "index";
        }
    }

    // Renter Dashboard View (PDF Screen 7 & 8)
    @GetMapping("/dashboard")
    public String showRenterDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";

        model.addAttribute("user", user);
        model.addAttribute("availableUnits", unitService.getAvailableUnits());
        return "dashboard";
    }

    // Admin Dashboard View (PDF Screen 3)
    @GetMapping("/admin-dashboard")
    public String showAdminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";

        model.addAttribute("user", user);
        model.addAttribute("buildings", unitService.getAllBuildings());
        return "admin-dashboard";
    }

    // Manager Dashboard View (PDF Screen 6)
    @GetMapping("/manager-dashboard")
    public String showManagerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";

        BuildingManager currentManager = (BuildingManager) user;

        model.addAttribute("user", user);
        model.addAttribute("managerName", user.getName());

        // Split every building into "mine" vs "the others" so the template
        // can show renter identities only for the manager's own building —
        // other buildings only ever get availability numbers, per the brief.
        List<Building> allBuildings = unitService.getAllBuildings();
        Building myBuilding = null;
        List<Building> otherBuildings = new ArrayList<>();
        List<BuildingManager> otherManagers = new ArrayList<>();

        for (Building building : allBuildings) {
            boolean isMine = building.getManager() != null
                    && building.getManager().getId().equals(currentManager.getId());
            if (isMine) {
                myBuilding = building;
            } else {
                otherBuildings.add(building);
                if (building.getManager() != null) {
                    otherManagers.add(building.getManager());
                }
            }
        }

        model.addAttribute("myBuilding", myBuilding);
        model.addAttribute("otherBuildings", otherBuildings);
        model.addAttribute("otherManagers", otherManagers);

        return "manager-dashboard";
    }

}