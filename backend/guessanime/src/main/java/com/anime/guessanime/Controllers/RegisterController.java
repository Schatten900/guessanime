package com.anime.guessanime.Controllers;
import com.anime.guessanime.Models.User;
import com.anime.guessanime.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register") //Declaracao do endpoint
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> RegisterUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        //Call User's layer service
        try {
            User user = userService.RegisterUser(username, email, password, image);
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername().get());
            response.put("email", user.getEmail().get());
            response.put("points", user.getPoints());
            response.put("image", user.getImageBase64());

            return ResponseEntity.ok(Map.of("message", "Successfully on register ", "user", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed on register: " + e.getMessage()));
        }
    }
}