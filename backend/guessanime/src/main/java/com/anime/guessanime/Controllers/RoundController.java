package com.anime.guessanime.Controllers;

import com.anime.guessanime.Domains.Pair;
import com.anime.guessanime.Models.Character;
import com.anime.guessanime.Models.CharacterDTO;
import com.anime.guessanime.Models.RequestDTO;
import com.anime.guessanime.Services.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/byCharacter")
public class RoundController {

    private final RoundService roundService;
    @Autowired
    public RoundController(RoundService roundService){
        this.roundService = roundService;
    }

    @PostMapping
    public ResponseEntity<?> giveInfoRound(@RequestBody RequestDTO data){
        try{
            //In frontend user will occur the logic of all rounds
            System.out.println("Solicited rounds - " + data.getQuantity());
            List<List<Pair<CharacterDTO,Boolean>>> rounds = roundService.getAllRounds(data.getQuantity());

            if (rounds == null || rounds.isEmpty()) {
                throw new RuntimeException("No rounds generated");
            }
            
            return ResponseEntity.ok(rounds);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error - " + e.getMessage());
        }
    }

    @PostMapping("/help")
    public ResponseEntity<?> summarizationWithAI(@RequestBody Map<String, String> request){
        try{
            String text = request.get("synopsis");
            String new_text = roundService.summarization(text);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message","Successfully on summarization text","new_text",new_text));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message","failed on text summarization","error", e.getMessage()));
        }
    }
}
