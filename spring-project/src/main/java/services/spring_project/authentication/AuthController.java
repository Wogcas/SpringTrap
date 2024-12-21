package services.spring_project.authentication;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "sign-up";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public String signUpUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model, HttpSession session) {

        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            model.addAttribute("error", "Passwords mismatch");
            return "sign-up";
        }

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            model.addAttribute("error", "Email registered already");
            return "sign-up";
        }

        User user = UserAdapter.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        session.setAttribute("success", "Account created successfully");
        return "redirect:/login";
    }
}
