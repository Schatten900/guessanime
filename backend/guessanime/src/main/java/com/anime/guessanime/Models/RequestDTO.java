package com.anime.guessanime.Models;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class RequestDTO {
    private String message;
    private int quantity;
    private Long id;
}

