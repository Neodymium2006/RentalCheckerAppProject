package ph.edu.dlsu.lbycpob.rentalcheckerappproject.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.model.*;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.service.UnitService;
import ph.edu.dlsu.lbycpob.rentalcheckerappproject.service.UserService;

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

        // So the "Current Unit" button knows whether to unlock itself.
        if (user instanceof Renter renter) {
            model.addAttribute("currentUnit", unitService.getCurrentUnitForRenter(renter.getId()));
        }

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
        // can show renter identities only for the manager's own building.
        List<Building> allBuildings = unitService.getAllBuildings();
        Building myBuilding = null;
        List<Building> otherBuildings = new ArrayList<>();

        for (Building building : allBuildings) {
            boolean isMine = building.getManager() != null
                    && building.getManager().getId().equals(currentManager.getId());
            if (isMine) {
                myBuilding = building;
            } else {
                otherBuildings.add(building);
            }
        }

        model.addAttribute("myBuilding", myBuilding);
        model.addAttribute("otherBuildings", otherBuildings);

        return "manager-dashboard";
    }

    // Building Details View (PDF Screen 4)
    @GetMapping("/building-details")
    public String showBuildingDetails(@RequestParam(value = "id", defaultValue = "1") Long buildingId,
                                      HttpSession session,
                                      Model model) {
        if (session.getAttribute("user") == null) return "redirect:/";

        model.addAttribute("buildingId", buildingId);
        model.addAttribute("building", unitService.getBuildingById(buildingId));

        return "building-details";
    }

    // Room Details View (PDF Screen 5 & 9)
    // With an "id" param: browsing a specific unit clicked from a floor plan.
    // Without one: falls back to the logged-in renter's current lease.
    @GetMapping("/room-details")
    public String showRoomDetails(@RequestParam(value = "id", required = false) Long unitId,
                                  HttpSession session,
                                  Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";

        Unit unit;
        if (unitId != null) {
            unit = unitService.getUnitById(unitId);
        } else if (user instanceof Renter renter) {
            unit = unitService.getCurrentUnitForRenter(renter.getId());
        } else {
            unit = null;
        }
        model.addAttribute("unit", unit);

        boolean isOwnUnit = unit != null
                && !unit.isAvailable()
                && unit.getRenter() != null
                && unit.getRenter().getId().equals(user.getId());
        model.addAttribute("isOwnUnit", isOwnUnit);

        return "room-details";
    }

    // Payment History Page View
    @GetMapping("/payment-history")
    public String showPaymentHistory(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/";
        return "payment-history";
    }

    // Handle Renting Unit Action
    @PostMapping("/rent/{id}")
    public String rentUnitAction(@PathVariable("id") Long unitId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";

        if (user instanceof Renter renter) {
            unitService.rentUnit(unitId, renter);
        }
        return "redirect:/dashboard";
    }

    // Handle Terminate Lease Action (PDF Screen 8 & 9)
    @PostMapping("/terminate-lease")
    public String terminateLeaseAction(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/";

        if (user instanceof Renter renter) {
            unitService.terminateLease(renter.getId());
        }
        return "redirect:/dashboard";
    }

    // Handle Logout and Session Invalidation
    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Helper method to automatically redirect logged-in users to their respective role dashboard
    private String redirectToDashboard(User user) {
        if ("ADMIN".equalsIgnoreCase(user.getUserType())) {
            return "redirect:/admin-dashboard";
        } else if ("MANAGER".equalsIgnoreCase(user.getUserType())) {
            return "redirect:/manager-dashboard";
        } else {
            return "redirect:/dashboard";
        }
    }
}