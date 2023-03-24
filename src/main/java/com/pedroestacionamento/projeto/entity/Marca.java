package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "marca")
public class Marca extends AbstractEntity {

    @Getter @Setter
    @NotNull
    @Column(name = "nome",unique = true, length = 50)
    private String nome;

    @Getter
    @OneToMany(mappedBy = "marca")
    private Set<Modelo> modelos;

    public Marca() {}
}
