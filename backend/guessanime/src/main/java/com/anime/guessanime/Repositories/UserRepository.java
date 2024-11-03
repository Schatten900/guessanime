package com.anime.guessanime.Repositories;
import com.anime.guessanime.Domains.Email;
import com.anime.guessanime.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(Email email);
}
