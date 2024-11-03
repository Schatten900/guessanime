package com.anime.guessanime.Controllers;
import com.anime.guessanime.Domains.RequestDTO;
import com.anime.guessanime.Services.AlimentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alimentation")
public class AlimentationController {

    private final AlimentationService alimentationService;

    @Autowired
    AlimentationController(AlimentationService alimentationService){
        this.alimentationService = alimentationService;
    }

    @PostMapping
    public ResponseEntity<String> takeAnimeAndChars(@RequestBody RequestDTO data){
        try{
            System.out.println("Hello from frontend: " + data.getMessage());
            alimentationService.takeAnime();
            alimentationService.takeCharsByAnime();

            return ResponseEntity.ok().body("Successfully on take anime");
        }
        catch(Exception e){
            System.out.println("Error Occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Error Ocorrured:" + e.getMessage());
        }
    }
}
