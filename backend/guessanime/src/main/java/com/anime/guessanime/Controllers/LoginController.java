package com.anime.guessanime.Controllers;
import com.anime.guessanime.Models.User;
import com.anime.guessanime.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    @Autowired
    LoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody User data){
        try {
            System.out.println("Before saving: "
                    + "email: " + data.getEmail().get() + " "
                    + "password: " + data.getPassword().get());
            userService.LoginUser(data.getEmail().get(),data.getPassword().get());
            System.out.println("Successfully on login");
            return ResponseEntity.ok("Data received successfully");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error while search user: " + e.getMessage());
        }
    }

}
