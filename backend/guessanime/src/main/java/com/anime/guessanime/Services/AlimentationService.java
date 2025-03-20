package com.anime.guessanime.Services;
import com.anime.guessanime.Models.Anime;
import com.anime.guessanime.Models.Character;
import com.anime.guessanime.Repositories.AlimentationRepository;
import com.anime.guessanime.Repositories.CharacterRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Service
public class AlimentationService {

    private final AlimentationRepository alimentationRepository;
    private final CharacterRepository characterRepository;
    @Autowired
    public AlimentationService(AlimentationRepository alimentationRepository, CharacterRepository characterRepository){
        this.alimentationRepository = alimentationRepository;
        this.characterRepository = characterRepository;
    }

    protected final int LIMIT_ANIME = 60;
    protected final int LIMIT_CHAR = 20;
    //protected final String URL_ANIME_TAKE = "https://kitsu.io/api/edge/";
    protected final String URL_ANIME_TAKE ="https://api.jikan.moe/v4";
    
    public Optional<Anime> hasAnime(long id){
        return alimentationRepository.findById(id);
    }

    public Optional<Character> hasCharacter(String animeTitle, String characterName){
        return characterRepository.findCharacter(animeTitle,characterName);
    }

    public void takeAnime() throws IOException, InterruptedException {
        //Logic to search anime and chars using an API
            //Take the list of anime
            HttpClient client = HttpClient.newBuilder().build();
            int offset = 0;
            int page = 1;
            boolean existMore = true;

            while (existMore) {
                String apiUrl = URL_ANIME_TAKE + "/top/anime?filter=bypopularity&type=tv&limit=20&page=" + page;
                System.out.println(apiUrl);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(apiUrl))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200)
                    throw new RuntimeException("Error on using API - " + response.statusCode());

                System.out.println("Request successful: " + apiUrl);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.body());
                JsonNode animeArray = jsonResponse.get("data");

                if (offset >= LIMIT_ANIME || animeArray == null || !animeArray.isArray() || animeArray.isEmpty()) {
                    existMore = false;
                    continue;
                }

                //Save the list on database
                for (JsonNode animeItem : animeArray) {

                    long id = animeItem.get("mal_id").asLong();

                    String title = animeItem.has("title_english")
                            ? animeItem.get("title_english").asText()
                            : null;
                    String synopsis = animeItem.has("synopsis")
                            ? animeItem.get("synopsis").asText()
                            : null;

                    if (title == null || synopsis == null || title.isEmpty() || synopsis.isEmpty())
                        continue;

                    System.out.println(id + " - " + title);

                    Anime animeAux = new Anime(id, title, synopsis);

                    Optional<Anime> existAnime = hasAnime(id);

                    System.out.println("Anime already exists? " + existAnime);

                    if (existAnime.isPresent())
                        continue;

                    System.out.println(animeAux);
                    alimentationRepository.save(animeAux);
                }
                page++;
                offset+=20; //Add more characters
            }
    }

    public void takeCharacters() throws IOException, InterruptedException {
        //Take all anime on the database

        System.out.println("Taking ids...");
        List<Anime> allAnimes = alimentationRepository.findAll();

        //Take id of all characters on anime related
        HttpClient client = HttpClient.newBuilder().build();

        for (Anime animeItem : allAnimes){
            System.out.println(animeItem.getTitle() + " - " + animeItem.getId());
            String urlAllCHarsIds = URL_ANIME_TAKE + "/anime/" + animeItem.getId() + "/characters";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlAllCHarsIds))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response.body());
            JsonNode characterData = jsonResponse.get("data");

            //System.out.println(characterData);

            int cont = 0;
            for (JsonNode charData : characterData){
                if (cont >= LIMIT_CHAR){
                    break;
                }

                long idChar = charData.has("character") && charData.get("character").has("mal_id")
                        ? charData.get("character").get("mal_id").asLong()
                        : 0L;
                String nameChar = charData.has("character") && charData.get("character").has("name")
                        ? charData.get("character").get("name").asText()
                        : null;

                String imageChar = charData.has("character") && charData.get("character").has("images") && charData.get("character").get("images").has("jpg") && charData.get("character").get("images").get("jpg").has("image_url")
                        ? charData.get("character").get("images").get("jpg").get("image_url").asText()
                        : null;

                if (imageChar == null) {
                    imageChar = charData.has("character") && charData.get("character").has("images") && charData.get("character").get("images").has("webp") && charData.get("character").get("images").get("webp").has("image_url")
                            ? charData.get("character").get("images").get("webp").get("image_url").asText()
                            : null;
                }
                System.out.println(imageChar);
                if (nameChar == null || imageChar == null || nameChar.isEmpty() || imageChar.isEmpty())
                    continue;

                //Validation name
                String firstName = "";
                String secondName = "";
                boolean hasSecondName = false;
                for (int i=0; i < nameChar.length(); ++i){
                    if (nameChar.charAt(i) == ','){
                        hasSecondName = true;
                        continue;
                    }
                    if (hasSecondName){
                        secondName += nameChar.charAt(i);
                        continue;
                    }
                    firstName += nameChar.charAt(i);
                }

                if (hasSecondName){
                    nameChar = secondName + " " + firstName;
                }

                Optional<Character> optional = characterRepository.findById(idChar);
                if (optional.isPresent())
                    continue;

                //If character not present in database
                System.out.println(idChar + nameChar);
                Character character = new Character(idChar,nameChar,imageChar,animeItem);
                characterRepository.save(character);
                cont++;
            }
        }

        System.out.println("Take ids Successfully");
    }
}
