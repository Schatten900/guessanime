package com.anime.guessanime.Repositories;
import com.anime.guessanime.Domains.Email;
import com.anime.guessanime.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(Email email);

    @Query(value=""+
    "SELECT * FROM PERFIL WHERE ID_USER =:id", nativeQuery = true)
    Optional<User> findByID(Long id);

    @Query(value="" +
            "SELECT * FROM PERFIL ORDER BY POINTS DESC LIMIT 20 ", nativeQuery = true)
    Optional<List<User>> findAllByRanking();

    @Transactional
    @Modifying
    @Query(value = "" +
            "UPDATE PERFIL SET POINTS = POINTS + :points WHERE ID_USER= :id", nativeQuery = true)
    void updateUserByPoints(@Param("points") int points, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value="" +
            "UPDATE PERFIL SET IMAGE = :image WHERE ID_USER =:id",nativeQuery = true)
    void updateImageUser(@Param("id") long id, @Param("image") byte[] image);
}
