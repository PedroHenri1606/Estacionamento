package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "modelos")
public class Modelo extends AbstractEntity  {

    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @Getter
    @OneToMany(mappedBy = "modelo")
    private List<Veiculo> veiculos;

    public Modelo() {}
}
