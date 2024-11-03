package com.anime.guessanime.Domains;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
public class Username extends Dominio{

    @Transient //Hibernate gone ignore this attribute
    private final int MIN_CHAR;
    @Transient
    private final int MAX_CHAR ;
    {
        MIN_CHAR = 4;
        MAX_CHAR = 15;
    }
    public Username(){}
    public Username(String username){
        set(username);
    }

    //Implementacao do metodo abstrato de Dominio
    @Override
    public void validar(String username){
        if (username == null || username.length() < MIN_CHAR )
            throw new IllegalArgumentException("Username must have at least 4 chars");

        if (username.length() > MAX_CHAR)
            throw  new IllegalArgumentException("Username must not pass 10 chars");

        for (int i=0; i < username.length(); ++i) {
            char chAux = username.charAt(i);
            if (!Character.isLetterOrDigit(username.charAt(i)) && chAux != '_')
                throw  new IllegalArgumentException("Username must not contains special characters or backspace");
        }
    }
}
