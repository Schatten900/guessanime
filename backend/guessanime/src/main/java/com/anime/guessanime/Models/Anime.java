package com.anime.guessanime.Models;
import com.anime.guessanime.Models.CharacterAnime;
import com.anime.guessanime.Models.Episode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Anime {
    @Id
    @Column(name="ID_ANIME")
    private long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="SYNOPSIS")
    private String synopsis;

    @OneToMany(mappedBy = "anime",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CharacterAnime> characters = new ArrayList<>();

    @OneToMany(mappedBy = "anime",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Episode> episodes = new ArrayList<>();


    public Anime(){
    }

    public Anime(String title, String synopsis) {
        this.title = title;
        this.synopsis = synopsis;
    }

    public Anime(long id, String title, String synopsis){
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "Title - " + getTitle() + "\n" +
                "Synopsis - " + getSynopsis();

    }

    public void ShowAnimeCharacters(){
        for (CharacterAnime charItem : this.characters){
            System.out.println(charItem.getId());
        }
    }
}
