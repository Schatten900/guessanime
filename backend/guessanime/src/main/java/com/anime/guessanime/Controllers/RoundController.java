package com.anime.guessanime.Controllers;

import com.anime.guessanime.Domains.Pair;
import com.anime.guessanime.Domains.RequestDTO;
import com.anime.guessanime.Models.Character;
import com.anime.guessanime.Services.RoundService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            List<List<Pair<Character,Boolean>>> rounds = roundService.getAllRounds(data.getQuantity());

            if (rounds == null || rounds.isEmpty()) {
                throw new RuntimeException("No rounds generated");
            }
            
            return ResponseEntity.ok(rounds);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error - " + e.getMessage());
        }
    }
}
