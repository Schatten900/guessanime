package com.anime.guessanime.DomainsTest;
import com.anime.guessanime.Domains.Username;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsernameTest {
    private final Username username = new Username();
    final String VALID_USERNAME = "Nick_Fury";
    final String INVALID_USERNAME = "Nick#Fury";
    final String NULL_USERNAME = null;

    //Todas as possibilidades possiveis de teste
    @Test
    public void ValidUsername(){
        assertDoesNotThrow(()->{ //Pede uma lambda como parametro e a msg de excecao caso ocorra erro
            username.set(VALID_USERNAME);
        }, "Valid username shouldn't throw an exception");
        assertEquals(username.get(),VALID_USERNAME, "Username and example should be same");
    }

    @Test
    public void InvalidUsername(){
        //Tipo de excecao esperada, codigo a ser executado usando arrow function
        assertThrows(IllegalArgumentException.class,() -> {
            username.set(INVALID_USERNAME);
        });
    }

    @Test
    public void NullUsername(){
        assertThrows(IllegalArgumentException.class, () -> {
            username.set(NULL_USERNAME);
        });
    }
}
