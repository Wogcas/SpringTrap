package services.spring_project.authentication;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public String create(@ModelAttribute("userDTO") UserDTO userDTO){

        logger.debug("Recibiendo datos para el registro de usuario: " + userDTO.getUsername());


        //Need to fix
        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            return "sign-up?error=password-mismatch";
        }

        //Need to fix
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            return "sign-up?error=email-already-exists";
        }

        User user = UserAdapter.toEntity(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        logger.info("Usuario creado con éxito: " + userDTO.getUsername());

        return "login";
    }
}
