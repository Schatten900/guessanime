package com.anime.guessanime.DomainsTest;
import com.anime.guessanime.Domains.Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {
    private Email emailTest = new Email();
    private final String VALID_EMAIL = "Exemple.Test@hotmail.com.br";
    private final String INVALID_EMAIL = "InvalidEmail@gmail";
    private final String NULL_EMAIL = null;

    @Test
    public void ValidEmail(){
        assertDoesNotThrow(()->{
            emailTest.set(VALID_EMAIL);
        },"Valid Email shouldn't throw exception");
        assertEquals(emailTest.get(),VALID_EMAIL,"Email and example must be same");

    }

    @Test
    public void InvalidEmail(){
        assertThrows(IllegalArgumentException.class, ()->{
            emailTest.set(INVALID_EMAIL);
        },"Invalid Email must throw exception");

    }

    @Test
    public void NullEmail(){
        assertThrows(IllegalArgumentException.class, ()->{
            emailTest.set(NULL_EMAIL);
        },"Null Email must throw exception");
    }
}
