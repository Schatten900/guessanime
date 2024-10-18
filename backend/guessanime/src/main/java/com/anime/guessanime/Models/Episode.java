package com.anime.guessanime.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="TITLE")
    private String name;

    @Column(name="SYNOPSIS")
    private String synopsis;

    @ManyToOne
    @JoinColumn(name = "ID_ANIME")
    private Anime anime;
}
