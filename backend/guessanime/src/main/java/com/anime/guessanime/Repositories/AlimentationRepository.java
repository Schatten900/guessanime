package com.anime.guessanime.Repositories;
import com.anime.guessanime.Models.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AlimentationRepository extends JpaRepository<Anime,Long> {
    Optional<Anime> findByTitle(String title);
}
