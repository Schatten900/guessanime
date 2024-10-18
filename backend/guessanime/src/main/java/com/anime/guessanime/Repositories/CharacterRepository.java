package com.anime.guessanime.Repositories;
import com.anime.guessanime.Models.CharacterAnime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CharacterRepository extends JpaRepository<CharacterAnime,Long> {

    //Using QUERY COMMAND
    @Query(value = "" +
            "SELECT AC.* FROM ANIME_CHARACTER as AC " +
            "JOIN ANIME as A ON AC.ID_ANIME = A.ID_ANIME " +
            "WHERE A.TITLE = :animeTitle AND AC.NAME = :characterName", nativeQuery = true)
    Optional<CharacterAnime> findCharacter(@Param("animeTitle") String animeTitle, @Param("characterName") String characterName);
}
