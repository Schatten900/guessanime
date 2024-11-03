package com.anime.guessanime.Domains;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
abstract class Dominio {
    protected String value;

    public Dominio(){}

    public abstract void validar(String valor);

    public void set(String valor){
        validar(valor);
        this.value = valor;
    }
    public String get(){
        return this.value;
    }
}
