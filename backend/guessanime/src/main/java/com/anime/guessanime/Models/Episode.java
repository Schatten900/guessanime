package com.anime.guessanime.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Episode {

    @Id
    @Column(name="ID_EPISODE")
    private long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="SYNOPSIS")
    private String synopsis;

    @ManyToOne
    @JoinColumn(name = "ID_ANIME")
    private Anime anime;

    @Override
    public String toString() {
        return "Episode - " + getTitle() + "\n" + "Synopsis - " + getSynopsis();
    }
}
