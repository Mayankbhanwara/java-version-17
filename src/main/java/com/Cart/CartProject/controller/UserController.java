package com.Cart.CartProject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;

import com.Cart.CartProject.model.User;
import com.Cart.CartProject.model.Review;
import com.Cart.CartProject.controller.UserController;
import com.Cart.CartProject.repository.ReviewRepository;
// import com.Cart.CartProject.repository.DashboardRepository;
import com.Cart.CartProject.repository.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

     @Autowired
    private UserRepo userrepo;

 @Autowired
    private ReviewRepository reviewRepo;
    // @Autowired
    //  private DashboardRepository dashboardService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";  // signup.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userrepo.save(user);  // ✅ Save to DB
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // login.html
    }

@PostMapping("/login")
public String loginUser(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {

    Optional<User> optionalUser = userrepo.findByEmail(email);

    if (optionalUser.isPresent()) {
        User user = optionalUser.get();

        if (user.getPassword().equals(password)) {
            // ✅ Save full user in session
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
               session.setAttribute("userEmail", email);

            // ✅ Send confirmation email (optional)
            emailService.sendLoginConfirmationToUser(email);

            // ✅ Redirect to /data
            return "redirect:/data";
        }
    }
    return "redirect:/register";
}
@GetMapping("/dashboard")
public String showDashboard(HttpSession session, Model model) {
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser != null) {
        model.addAttribute("name", loggedInUser.getName());
        model.addAttribute("email", loggedInUser.getEmail());
    }
    return "dashboard"; 
}


// reviews..........//
  @GetMapping("/reviews")
    public String showReviewForm(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");

        Review review = new Review();
        review.setEmail(email);  // Pre-fill email from session

        model.addAttribute("review", review);
        model.addAttribute("reviews", reviewRepo.findAll());  // Show all reviews
        return "review";
    }
       @PostMapping("/reviews")
    public String submitReview(@ModelAttribute("review") Review review) {
        reviewRepo.save(review);
        return "redirect:/reviews"; // Reload page to see new review
    }

    // Optional: Delete review
   @PostMapping("/reviews/delete/{id}")
public String deleteReview(@PathVariable Long id, HttpSession session) {
    String loggedInEmail = (String) session.getAttribute("userEmail");

    Review review = reviewRepo.findById(id).orElse(null);

    if (review != null && review.getEmail().equals(loggedInEmail)) {
        reviewRepo.deleteById(id);  // Only delete if the email matches
    }

    return "redirect:/reviews";
}
}