package com.anime.guessanime.ServicesTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.anime.guessanime.Models.User;
import com.anime.guessanime.Repositories.UserRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void RegisterUser(){
        //Set the example
        String username = "testUser";
        String email = "testuser@example.com";
        String password = "Test@123";

        User user = new User(username, email, password);
        String passwordEncodedBD = passwordEncoder.encode((CharSequence)user.getPassword().get());

        user.setEncodedPassword(passwordEncodedBD);
        User savedUser = userRepository.save(user);

        //Makes the verification
        assertEquals(user.getUsername(), savedUser.getUsername(), "Both Username must be equal");
        assertEquals(user.getEmail(), savedUser.getEmail(), "Both Email must be equal");
        assertTrue(passwordEncoder.matches(password,savedUser.getEncodedPassword()), "Both Password must be equal");
    }

}
