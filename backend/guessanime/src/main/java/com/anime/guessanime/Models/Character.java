package com.anime.guessanime.Models;
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
    private Anime anime;

    public Character(){}

    public Character(long id, Anime anime){
        this.id = id;
        this.anime = anime;
    }

    public Character(String name, String image){
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString(){
        return "Character - " + getName() + "\n" + "From - " + getAnime().getTitle() + '\n'
                + "Image - " + getImage();
    }

}
