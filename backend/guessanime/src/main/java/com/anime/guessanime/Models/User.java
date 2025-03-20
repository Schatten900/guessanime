package com.anime.guessanime.Models;
import com.anime.guessanime.Domains.Email;
import com.anime.guessanime.Domains.Password;

import com.anime.guessanime.Domains.Username;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.lang.model.element.Name;
import java.util.Base64;

@Getter
@Setter
@Entity
@Table(name = "PERFIL")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
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
    @JsonIgnore
    private Password password;

    @Column(name = "POINTS")
    private int points;

    @Lob
    @Column(name= "IMAGE")
    private byte[] image;

    @JsonIgnore
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
                ", points= " + points +
                '}';
    }

    public User(){
        this.username = new Username();
        this.email = new Email();
        this.password = new Password();
        this.points = 0;

    }

    public User(String username, String email, String password) {
        this.username = new Username(username);
        this.email = new Email(email);
        this.password = new Password(password);
        this.points = 0;
    }

    public void setUser(String username, String email, String password, int points){
        this.email.set(email);
        this.password.set(password);
        this.username.set(username);
        this.points = points;
    }

    public String getImageBase64() {
        if (this.image == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(this.image);
    }
}
