package com.anime.guessanime.Controllers;
import com.anime.guessanime.Models.User;
import com.anime.guessanime.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register") //Declaracao do endpoint
public class RegisterController {

    private final UserService userService;
    @Autowired
    public RegisterController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> RegisterUser(@RequestBody User data){
        //Call User's layer service
        try {
            System.out.println("Before saving: " + data);
            userService.RegisterUser(data);
            return ResponseEntity.ok("Data received successfully");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error:" + e.getMessage());
        }
    }
}
