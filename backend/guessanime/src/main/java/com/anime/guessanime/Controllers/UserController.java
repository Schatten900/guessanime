package com.anime.guessanime.Controllers;

import com.anime.guessanime.Models.User;
import com.anime.guessanime.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/changingPicture")
    public ResponseEntity<?> changePicture(
            @RequestParam("id") Long id,
            @RequestParam(value = "image", required = false) MultipartFile image){
        try{
            User user = userService.changePicture(id,image);
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername().get());
            response.put("email", user.getEmail().get());
            response.put("points", user.getPoints());
            response.put("image", user.getImageBase64());

            return ResponseEntity.ok(Map.of("message","Sucessfully on changing profile","user",response));
        }
        catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message","Failed on changing user picture"));
        }
    }
}
