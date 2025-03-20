package com.anime.guessanime.Controllers;

import com.anime.guessanime.Models.RequestDTO;
import com.anime.guessanime.Models.User;
import com.anime.guessanime.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final UserService userService;

    @Autowired
    RankingController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> takeRankingUsers(){
        try{
            List<Map<String,Object>> userRanking = userService.getRanking();

            return ResponseEntity.ok(userRanking);

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error in ranking", "error: ", e.getMessage()));

        }
    }

    @PostMapping("/verificationPoints")
    public ResponseEntity<?> verificationPoints(@RequestBody RequestDTO data) {
        try {
            Long id = data.getId();
            int points = data.getQuantity();

            if (id == null || points == 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Failed on updating points"));
            }
            User user = userService.updateUserPoints(points, id);
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getUsername().get());
            response.put("email", user.getEmail().get());
            response.put("points", user.getPoints());
            response.put("image", user.getImageBase64());
            
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "successfully updated points","user",response));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message","failed on updating points"));
        }
    }
}
