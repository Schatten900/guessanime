package com.anime.guessanime.Models;
import com.anime.guessanime.Models.CharacterAnime;
import com.anime.guessanime.Models.Episode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="SYNOPSIS")
    private String synopsis;

    @OneToMany(mappedBy = "anime",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CharacterAnime> characters;

    @OneToMany(mappedBy = "anime",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Episode> episodes;

    public Anime(){}

    public Anime(String title, String synopsis) {
        this.title = title;
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
