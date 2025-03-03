package com.anime.guessanime.Domains;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Password extends Dominio{
    @Transient
    private final int MAX_CHARS;
    @Transient
    private final int MIN_CHARS;
    {
        MAX_CHARS = 10;
        MIN_CHARS = 5;
    }
    public Password(){}

    public Password(String password){
        set(password);
    }

    @Override
    public void validar(String senha){
        boolean special = false;
        boolean upper = false;
        boolean digit = false;

        if (senha == null)
            throw new IllegalArgumentException("Password shouldn't be empty");

        if (senha.length() < MIN_CHARS)
            throw new IllegalArgumentException("Password must have more than 5 chars");
        if (senha.length() > MAX_CHARS)
            throw new IllegalArgumentException("Password must have less than 10 chars");

        for(int i=0; i < senha.length(); ++i){
            char chAux = senha.charAt(i);
            if (Character.isDigit(chAux))
                digit = true;
            if (Character.isUpperCase(chAux))
                upper = true;
            if (!Character.isLetterOrDigit(chAux))
                special = true;
        }
        if (!digit || !upper || !special)
            throw new IllegalArgumentException("Password must have: \n" +
                    "At least a special char \n" +
                    "At least a digit char \n" +
                    "At least a upper case letter");
    }

}
