package com.anime.guessanime.Services;
import com.anime.guessanime.Models.Anime;
import com.anime.guessanime.Models.CharacterAnime;
import com.anime.guessanime.Repositories.AlimentationRepository;
import com.anime.guessanime.Repositories.CharacterRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonArray;
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
    protected final int LIMIT_CHAR = 10;
    protected final String URL_ANIME_TAKE = "https://kitsu.io/api/edge/";
    
    public Optional<Anime> hasAnime(long id){
        return alimentationRepository.findById(id);
    }

    public Optional<CharacterAnime> hasCharacter(String animeTitle, String characterName){
        return characterRepository.findCharacter(animeTitle,characterName);
    }

    public void takeAnime(){
        //Logic to search anime and chars using an API
        try{
            //Take the list of anime
            HttpClient client = HttpClient.newBuilder().build();
            int offset = 0;
            boolean existMore = true;

            while (existMore) {
                String apiUrl = URL_ANIME_TAKE + "anime?page[limit]=20&sort=popularityRank&page[offset]=" + offset;
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(apiUrl))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200)
                    throw new RuntimeException("Error on using API" + response.statusCode());

                System.out.println("Request successful: " + apiUrl);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.body());
                JsonNode animeArray = jsonResponse.get("data");

                if (offset >= LIMIT_ANIME || animeArray == null || !animeArray.isArray() || animeArray.isEmpty()){
                    existMore = false;
                    continue;
                }

                //Save the list on database
                for (JsonNode animeItem : animeArray) {

                    long id = animeItem.get("id").asLong();

                    String title = animeItem.get("attributes").has("titles") && animeItem.get("attributes").get("titles").has("en")
                            ? animeItem.get("attributes").get("titles").get("en").asText()
                            : null;
                    String synopsis = animeItem.get("attributes").has("synopsis")
                            ? animeItem.get("attributes").get("synopsis").asText()
                            : null;

                    if (title == null || synopsis == null || title.isEmpty() || synopsis.isEmpty())
                        continue;

                    System.out.println(animeItem.get("id").asText() + " - " + title);

                    Anime animeAux = new Anime(id,title, synopsis);

                    Optional<Anime> existAnime = hasAnime(id);

                    System.out.println("Anime already exists? " + existAnime);

                    if (existAnime.isPresent())
                        continue;

                    System.out.println(animeAux);
                    alimentationRepository.save(animeAux);
                }
                offset += 20; //Add more characters
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error getting anime: " + e.getMessage());
        }
    }

    public void takeCharsByAnime(){
        try{
            //Logic to search the characters by anime on database
            List<Anime> allAnimes = takeCharsIds();
            if (allAnimes.isEmpty())
                return;

            //Take the character info with api
            for (Anime animeItem : allAnimes) {
                if (animeItem == null)
                    continue;
                String actual_title_anime = animeItem.getTitle();
                System.out.println("Taking all characters from " + actual_title_anime);
                for (CharacterAnime charItem : animeItem.getCharacters()) {

                    if (charItem == null)
                        continue;
                    //Build Api url
                    String url_anime_chars = URL_ANIME_TAKE + "/characters?filter[id]=" + charItem.getId();
                    HttpClient client = HttpClient.newBuilder().build();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(url_anime_chars))
                            .GET()
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    //Take data character response and save
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonResponse = objectMapper.readTree(response.body());
                    JsonNode charData = jsonResponse.get("data");
                    if (charData.isEmpty())
                        continue;

                    JsonNode firstCharData = charData.get(0);

                    String charName = firstCharData.has("attributes") && firstCharData.get("attributes").has("name")
                            ? firstCharData.get("attributes").get("name").asText()
                            : null;
                    String charImage = firstCharData.has("attributes") && firstCharData.get("attributes").has("image")
                            && firstCharData.get("attributes").get("image").has("original")
                            ? firstCharData.get("attributes").get("image").get("original").asText()
                            : null;

                    if (charName == null || charImage == null || charName.isEmpty() || charImage.isEmpty()){
                        System.out.println("Something went wrong");
                        continue;
                    }

                    System.out.println("Checking if character already exists...");
                    Optional<CharacterAnime> existChar = hasCharacter(actual_title_anime,charName);

                    if (existChar.isPresent()) {
                        System.out.println("Already exists on database that character");
                        continue;
                    }
                    charItem.setName(charName);
                    charItem.setImage(charImage);
                    System.out.println(charItem);
                    characterRepository.save(charItem);

                }
                //Take the episodes info with api
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error getting anime:" + e.getMessage());
        }

    }

    private List<Anime> takeCharsIds() throws IOException, InterruptedException {
        //Take all anime on the database

        System.out.println("Taking ids...");
        List<Anime> allAnimes = alimentationRepository.findAll();

        //Take id of all characters on anime related
        HttpClient client = HttpClient.newBuilder().build();

        for (Anime animeItem : allAnimes){
            String urlAllCHarsIds = URL_ANIME_TAKE + "anime/" + animeItem.getId() + "/relationships/anime-characters";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlAllCHarsIds))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response.body());
            JsonNode characterIds = jsonResponse.get("data");

            int cont = 0;
            for (JsonNode ids : characterIds){
                if (cont >= LIMIT_CHAR){
                    break;
                }

                long id = ids.has("id")
                        ? ids.get("id").asLong()
                        : 0L;

                animeItem.getCharacters().add(new CharacterAnime(id,animeItem));
                cont++;
            }
        }

        System.out.println("Take ids Successfully");
        return allAnimes;
    }
}
