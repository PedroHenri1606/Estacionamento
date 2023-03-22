package com.pedroestacionamento.projeto.entity.abstractEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO) @Id
    @Column(name = "id",nullable = false,unique = true )
    public Long id;

    @Getter @Setter
    @Column(name = "dtCadastro", nullable = false)
    private LocalDateTime cadastro;

    @Getter @Setter
    @Column(name = "dtAtualizacao")
    private LocalDateTime edicao;

    @Getter @Setter
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;


    @PrePersist
    private void preAdicao(){
        this.edicao = LocalDateTime.now();
        this.ativo = true;
    }
}
