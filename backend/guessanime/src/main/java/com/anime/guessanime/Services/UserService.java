package com.anime.guessanime.Services;
import com.anime.guessanime.Domains.Email;
import com.anime.guessanime.Domains.Password;
import com.anime.guessanime.Domains.Username;
import com.anime.guessanime.Models.User;
import com.anime.guessanime.Repositories.UserRepository;
import org.hibernate.query.IllegalQueryOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.InstanceNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> getUserByEmail(Email email) {
        return userRepository.findByEmail(email);
    }

    public User RegisterUser(String username, String email, String password, MultipartFile image) {
        if (username == null || email == null || password == null) {
            throw new IllegalArgumentException("Some field is empty");
        }

        //Search if email already registered
        Optional<User> emailRegistered = getUserByEmail(new Email(email));

        if (emailRegistered.isPresent())
            throw new IllegalQueryOperationException("Email already registered");

        try {
            User user = new User();
            user.setUsername(new Username(username));
            user.setEmail(new Email(email));
            user.setEncodedPassword(passwordEncoder.encode(password));
            user.setPoints(0);
            user.setImage(image.getBytes());

            //System.out.println("Coded: " + user.getEncodedPassword());

            userRepository.save(user);
            user.setImage(null);
            System.out.println("User saved successfully");
            return user;

        } catch (Exception e) {
            System.out.println("Error saving user");
            throw new RuntimeException("Error saving user:" + e.getMessage());
        }
    }

    public User LoginUser(String email, String password) throws IllegalAccessException {
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

        if (!passwordEncoder.matches(password,user.get().getEncodedPassword()))
            throw new IllegalAccessException("Password's wrong");

        return user.get();
    }

    public List<Map<String,Object>> getRanking() throws InstanceNotFoundException {

        Optional<List<User>> optionUserList = userRepository.findAllByRanking();

        if (optionUserList.isEmpty()){
            throw new InstanceNotFoundException("Ranking is empty");
        }
        List<User> userList = optionUserList.get();

        List<Map<String,Object>> userRanking = new ArrayList<>();

        for (int i=0; i< userList.size(); ++i){

            User userItem = userList.get(i);
            Map<String, Object> response = new HashMap<>();
            response.put("id", userItem.getId());
            response.put("username", userItem.getUsername().get());
            response.put("email", userItem.getEmail().get());
            response.put("points", userItem.getPoints());
            response.put("image", userItem.getImageBase64());
            userRanking.add(response);
        }

        return userRanking;
    }

    public User updateUserPoints(int points, long id) throws IOException {
        userRepository.updateUserByPoints(points,id);
        Optional<User> maybeUser = userRepository.findByID(id);
        if (maybeUser.isEmpty()){
            throw new IOException("user not founded.");
        }
        return maybeUser.get();
    }

    public User changePicture(Long id, MultipartFile image) throws IOException {
        userRepository.updateImageUser(id,image.getBytes());
        Optional<User> maybeUser = userRepository.findByID(id);
        if (maybeUser.isEmpty()){
            throw new IOException("user not founded.");
        }
        return maybeUser.get();
    }

}