package kth.lab2_journal_signin.data.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> findUserByEmail(String email) {
        // Returns a Mono<User>, which the caller must handle reactively
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new Exception("User not found with email: " + email)));
    }

    public Mono<User> saveUser(User user) {
        // Additional logic can be added here, like password encoding or validation
        return userRepository.save(user);
    }
}
