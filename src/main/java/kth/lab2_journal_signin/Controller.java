package kth.lab2_journal_signin;

import kth.lab2_journal_signin.data.user.User;
import kth.lab2_journal_signin.data.user.UserService;
import kth.lab2_journal_signin.data.dto.RegistrationRequest;
import kth.lab2_journal_signin.data.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class Controller {

    private final UserService userService;

    @Autowired
    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public Mono<ResponseEntity<Role>> login(@RequestParam String email, @RequestParam String password) {
        return userService.findUserByEmail(email)
                .flatMap(user -> {
                    if (user.getPassword().equals(password)) {
                        return Mono.just(ResponseEntity.ok(Role.valueOf(user.getRole().toUpperCase())));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                    }
                });
    }




    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody RegistrationRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Password encoding recommended
        user.setRole(String.valueOf(request.getRole() != null ? Role.valueOf(request.getRole().toUpperCase()) : Role.PATIENT));

        return userService.saveUser(user)
                .flatMap(savedUser -> {
                    if ("Practitioner".equalsIgnoreCase(request.getUserType())) {
                        /*
                        Practitioner practitioner = new Practitioner();
                        practitioner.setName(request.getName());
                        practitioner.setDateOfBirth(request.getDateOfBirth());
                        practitioner.setRole(Role.valueOf(request.getRole().toUpperCase()));
                        practitioner.setUser(savedUser);
                        return practitionerService.savePractitioner(practitioner)
                                .thenReturn(new ResponseEntity<>("User registered successfully", HttpStatus.CREATED));

                         */
                        return Mono.just(new ResponseEntity<>("Practitioner", HttpStatus.OK));
                    } else if ("Patient".equalsIgnoreCase(request.getUserType())) {
                        /*
                        Patient patient = new Patient();
                        patient.setName(request.getName());
                        patient.setDateOfBirth(request.getDateOfBirth());
                        patient.setGender(request.getGender());
                        patient.setUser(savedUser);
                        return patientService.savePatient(patient)
                                .thenReturn(new ResponseEntity<>("User registered successfully", HttpStatus.CREATED));

                         */
                        return Mono.just(new ResponseEntity<>("Patient", HttpStatus.OK));
                    }
                    return Mono.just(new ResponseEntity<>("Invalid user type", HttpStatus.BAD_REQUEST));
                });
    }
}
