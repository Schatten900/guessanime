package com.anime.guessanime.Domains;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class RequestDTO {
    private String message;
    private int quantity;
}
