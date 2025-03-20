package com.anime.guessanime.Domains;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Embeddable
public class Email extends Dominio{
    public Email(){}

    public Email(String email){
        set(email);
    }

    @Override
    public void validar(String email){

        if (email == null)
            throw new IllegalArgumentException("Email shouldn't be empty");
        // ? Zero or one
        // * Zero or more
        // + One or more
        // () Group by
        // | OR
        // $ finaL
        String regex = "([a-zA-Z0-9\\._-])+@([a-zA-Z])+(\\.([a-zA-Z])+)+"; //Padrao de email
        Pattern pattern = Pattern.compile(regex);   //Compila o padrao de email
        Matcher matcher = pattern.matcher(email);   //Verifica o padrao de email e o email recebido
        if (!matcher.matches())
            throw new IllegalArgumentException("Invalid email was found");
    }
}