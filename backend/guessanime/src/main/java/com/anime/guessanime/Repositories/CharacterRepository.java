package com.anime.guessanime.Repositories;
import com.anime.guessanime.Models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CharacterRepository extends JpaRepository<Character,Long> {

    //Using QUERY COMMAND
    @Query(value = "" +
            "SELECT AC.* FROM ANIME_CHARACTER as AC " +
            "JOIN ANIME as A ON AC.ID_ANIME = A.ID_ANIME " +
            "WHERE A.TITLE = :animeTitle AND AC.NAME_CHAR = :characterName", nativeQuery = true)
    Optional<Character> findCharacter(@Param("animeTitle") String animeTitle, @Param("characterName") String characterName);
}
