package skyw96.investments.Java.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import skyw96.investments.Kotlin.Exception.EmailAlreadyExistException;
import skyw96.investments.Kotlin.Model.Portfolio;
import skyw96.investments.Kotlin.Model.User;
import skyw96.investments.Kotlin.Repository.PortfolioRepository;
import skyw96.investments.Kotlin.Repository.UserRepository;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PortfolioRepository portfolioRepository;

    public User registerUser(String firstname, String lastname, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistException("User with email: " + email + ", already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(firstname, lastname, email, encodedPassword, Set.of("USER"));

        User savedUser = userRepository.save(user);

        Portfolio portfolio = new Portfolio(null, user, null, 0);
        portfolioRepository.save(portfolio);

        return savedUser;
    }

    public User createAdmin(String firstName, String lastName, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistException("User with email: " + email + ", already exists");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(firstName, lastName, email, encodedPassword, Set.of("ADMIN", "USER"));

        return userRepository.save(user);
    }
}
