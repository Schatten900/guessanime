package com.anime.guessanime.ServicesTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.anime.guessanime.Models.Anime;
import com.anime.guessanime.Models.Character;
import com.anime.guessanime.Repositories.AlimentationRepository;
import com.anime.guessanime.Repositories.CharacterRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
public class AlimentationServiceTest {

    private final AlimentationRepository alimentationRepository;
    private final CharacterRepository characterRepository;

    @Autowired
    public AlimentationServiceTest(AlimentationRepository alimentationRepository, CharacterRepository characterRepository){
        this.alimentationRepository = alimentationRepository;
        this.characterRepository = characterRepository;
    }

    protected final String URL_ANIME_TAKE = "https://kitsu.io/api/edge/anime";

    public Optional<Anime> hasAnime(long id){
        return alimentationRepository.findById(id);
    }

    public Optional<Character> hasCharacter(String animeTitle, String characterName){
        return characterRepository.findCharacter(animeTitle,characterName);
    }

    @Test
    public void takeAnime(){
        //Logic to search anime and chars using an API
        try{
            //Take the list of anime
            HttpClient client = HttpClient.newBuilder().build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_ANIME_TAKE + "?limit=50"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200)
                throw new RuntimeException("Error on using API" + response.statusCode());

            System.out.println("Is working the request");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            JsonNode animeArray = jsonResponse.get("data");

            if (animeArray == null || !animeArray.isArray())
                throw new RuntimeException("Empty return from API");

            //Save the list on database
            for (JsonNode animeItem : animeArray){
                long id = animeItem.get("id").asLong();
                String title = animeItem.get("attributes").has("titles") && animeItem.get("attributes").get("titles").has("en")
                        ? animeItem.get("attributes").get("titles").get("en").asText()
                        : null;
                String synopsis = animeItem.get("attributes").has("synopsis")
                        ? animeItem.get("attributes").get("synopsis").asText()
                        : null;

                if (title == null || synopsis == null)
                    continue;

                System.out.println(animeItem.get("id").asText() + " - " + title);

                Anime animeAux = new Anime(title,synopsis);
                Optional<Anime> existAnime = hasAnime(id);

                if (existAnime.isPresent())
                    continue;

                alimentationRepository.save(animeAux);
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error getting anime:" + e.getMessage());
        }
    }

    @Test
    public void takeCharsByAnime(){
        try{
            //Logic to search the characters by anime on database
            List<Anime> allAnimes = alimentationRepository.findAll();

            //Logic to search for all characters one by one on anime
            for (Anime anime : allAnimes) {
                System.out.println(anime);

                HttpClient client = HttpClient.newBuilder().build();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(URL_ANIME_TAKE + "/" + anime.getId() + "/characters"))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200)
                    throw new RuntimeException("Something went wrong on API");

                //Logic to save the character from the characters' array
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonResponse = mapper.readTree(response.body());
                JsonNode jsonArray = jsonResponse.get("data");

                for (JsonNode characterItem : jsonArray) {
                    String nameChar = characterItem.get("attributes").has("name")
                            ? characterItem.get("attributes").get("name").asText()
                            : null;

                    String image = characterItem.get("attributes").get("image").has("original")
                            ? characterItem.get("attributes").get("image").get("original").asText()
                            : null;

                    if (nameChar == null)
                        continue;

                    Character charAux = new Character(nameChar, image);
                    Optional<Character> existChar = hasCharacter(anime.getTitle(), nameChar);

                    System.out.println("Is present? " + existChar);
                    if (existChar.isPresent())
                        continue;

                    charAux.setAnime(anime);
                    System.out.println(charAux);
                    characterRepository.save(charAux);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error getting anime:" + e.getMessage());
        }
    }
}
