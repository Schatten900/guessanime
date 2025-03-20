package com.anime.guessanime.Controllers;
import com.anime.guessanime.Models.User;
import com.anime.guessanime.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    @Autowired
    LoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData){
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");
            
            System.out.println("Before saving: "
                    + "email: " + email + " "
                    + "password: " + password);
            User user = userService.LoginUser(email,password);

            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername().get());
            response.put("email", user.getEmail().get());
            response.put("points", user.getPoints());
            response.put("image", user.getImageBase64());

            System.out.println("Successfully on login");
            return ResponseEntity.ok(Map.of("message", "Successfully on login", "user", response));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed on login: " + e.getMessage()));
        }
    }

}
