package com.anime.guessanime.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CharacterAnime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name="NAME_CHAR")
    private String name;

    @Column(name="IMAGE")
    private String image;

    @ManyToOne
    @JoinColumn(name="ID_ANIME")
    private Anime anime;

    public CharacterAnime(){}
    public CharacterAnime(String name,String image){
        this.name = name;
        this.image = image;
    }

}
