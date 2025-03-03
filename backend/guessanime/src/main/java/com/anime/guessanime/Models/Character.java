package com.anime.guessanime.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="ANIME_CHARACTER")
public class Character {
    @Id
    @Column(name="ID_CHAR")
    private long id;

    @Column(name="NAME_CHAR")
    private String name;

    @Column(name="IMAGE")
    private String image;

    @ManyToOne
    @JoinColumn(name="ID_ANIME")
    @JsonBackReference
    private Anime anime;

    public Character(){}

    public Character(String name, String image){
        this.name = name;
        this.image = image;
    }

    public Character(Long id, String name, String image, Anime anime){
        this.id = id;
        this.name = name;
        this.image = image;
        this.anime = anime;
        if (anime != null) {
            anime.getCharacters().add(this); // Adiciona o character à lista do anime
        }
    }

    @Override
    public String toString(){
        return "Character - " + getName() + "\n" + "From - " + getAnime().getTitle() + '\n'
                + "Image - " + getImage();
    }

}
