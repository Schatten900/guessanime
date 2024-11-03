package com.anime.guessanime.ModelsTest;
import org.junit.jupiter.api.Test;
import com.anime.guessanime.Models.User;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private final String VALID_USERNAME = "__Teste";
    private final String VALID_EMAIL = "Teste123@example.net";
    private final String VALID_PASSWORD = "@Password9";

    private final String INVALID_USERNAME = "_.Teste._";
    private final String INVALID_EMAIL = "Teste.123@example/net";
    private final String INVALID_PASSWORD = "@Password";

    private final String NULL_USERNAME = null;
    private final String NULL_EMAIL = null;
    private final String NULL_PASSWORD = null;
    private User user = new User();

    @Test
    public void ValidUser(){
        assertDoesNotThrow(()->{
            user.setUser(VALID_USERNAME,VALID_EMAIL,VALID_PASSWORD);
        });

    }

    @Test
    public void InvalidUser(){
        assertThrows(IllegalArgumentException.class,()->{
            user.setUser(INVALID_USERNAME,INVALID_EMAIL,INVALID_PASSWORD);
        });
    }

    @Test
    public void NullUser(){
        assertThrows(IllegalArgumentException.class,()->{
            user.setUser(NULL_USERNAME,NULL_EMAIL,NULL_PASSWORD);
        });
    }
}
