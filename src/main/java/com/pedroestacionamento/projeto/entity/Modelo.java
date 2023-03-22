package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modelos", schema = "public")
@NoArgsConstructor
public class Modelo extends AbstractEntity {

    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;

    @Column(name = "marca", nullable = false)
    private Marca marca;

    public Modelo(String nome, Marca marca) {
        this.nome = nome;
        this.marca = marca;
    }

}
