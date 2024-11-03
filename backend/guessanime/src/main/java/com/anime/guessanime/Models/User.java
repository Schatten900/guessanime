package com.anime.guessanime.Models;
import com.anime.guessanime.Domains.Email;
import com.anime.guessanime.Domains.Password;

import com.anime.guessanime.Domains.Username;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.lang.model.element.Name;

@Getter
@Setter
@Entity
@Table(name = "PERFIL")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="value",column = @Column(name = "EMAIL",nullable = false))
    })
    private Email email;

    @Column(name="ENCODED_PASSWORD", nullable = false)
    private String encodedPassword;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "USERNAME", nullable = false))
    })
    private Username username;

    @Transient
    private Password password;

    public User getUser(){
        return this;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email.get() +
                ", password=" + password.get() +
                ", username=" + username.get() +
                '}';
    }

    public User(){
        this.username = new Username();
        this.email = new Email();
        this.password = new Password();

    }

    public User(String username, String email, String password) {
        this.username = new Username(username);
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public void setUser(String username, String email, String password){
        this.email.set(email);
        this.password.set(password);
        this.username.set(username);
    }

}
