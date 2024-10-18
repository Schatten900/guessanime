package com.anime.guessanime.Services;
import com.anime.guessanime.Domains.Email;
import com.anime.guessanime.Domains.Password;
import com.anime.guessanime.Models.User;
import com.anime.guessanime.Repositories.UserRepository;
import org.hibernate.query.IllegalQueryOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {
    //Logica de negocio para poder cadastrar usuario
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> getUserByEmail(Email email) {
        return userRepository.findByEmail(email);
    }

    public void RegisterUser(User user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Some field is empty");
        }

        System.out.println(user);
        String username = user.getUsername().get();
        String email = user.getEmail().get();
        String password = user.getPassword().get();

        //Search if email already registered
        Optional<User> emailRegistered = getUserByEmail(user.getEmail());

        if (emailRegistered.isPresent())
            throw new IllegalQueryOperationException("Email already registered");

        try {
            String encodedPassword = passwordEncoder.encode(password);
            user.setEncodedPassword(encodedPassword);

            System.out.println("Codificado: " + user.getEncodedPassword());
            userRepository.save(user);
            System.out.println("Usuario salvo com sucesso");

        } catch (Exception e) {
            System.out.println("Erro ao salvar usuario");
            throw new RuntimeException("Error saving user:" + e.getMessage());
        }
    }

    public void LoginUser(String email, String password) throws IllegalAccessException {
        //Verification
        Email emailAux = new Email(email);
        Password passwordAux = new Password(password);
        System.out.println("Received: "
                + "email: " + email + " "
                + "password: " + password);
        Optional<User> user = userRepository.findByEmail(emailAux);

        if (user.isEmpty())
            throw new IllegalAccessException("None user founded");

        System.out.println("Email founded in our system");

        System.out.println(user.get().getEncodedPassword());
        //Don't working as expected :<
        if (!passwordEncoder.matches(password,user.get().getEncodedPassword()))
            throw new IllegalAccessException("Password's wrong");

    }
}