package com.anime.guessanime.Models;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDTO {
    private long id;
    private String name;
    private String image;
    private String synopsis;

    public CharacterDTO(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.image = character.getImage();
        this.synopsis = character.getAnime().getSynopsis(); // Acessa a sinopse do anime
    }
}
