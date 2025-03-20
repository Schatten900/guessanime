package com.anime.guessanime.DomainsTest;

import com.anime.guessanime.Domains.Password;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {
    private Password passwordTest = new Password();
    private final String VALID_PASSWORD = "Teste@531";
    private final String INVALID_PASSWORD = "password";
    private final String NULL_PASSWORD = null;

    @Test
    public void ValidPassword(){
        assertDoesNotThrow(()->{
            passwordTest.set(VALID_PASSWORD);
        },"Valid password shouldn't throw exception");
    }

    @Test
    public void InvalidPassword(){
        assertThrows(IllegalArgumentException.class,()->{
            passwordTest.set(INVALID_PASSWORD);
        },"Invalid Password must throw exception");
    }

    @Test
    public void NullPassword(){
        assertThrows(IllegalArgumentException.class,()->{
            passwordTest.set(NULL_PASSWORD);
        },"Invalid Password must throw exception");
    }
}
