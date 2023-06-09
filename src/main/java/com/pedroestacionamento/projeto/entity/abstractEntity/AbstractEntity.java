package com.pedroestacionamento.projeto.entity.abstractEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false,unique = true )
    public Long id;

    @Getter @Setter
    @Column(name = "dtCadastro", nullable = false)
    private LocalDateTime cadastro;

    @Getter @Setter
    @Column(name = "dtAtualizacao")
    private LocalDateTime edicao;

    @Getter @Setter
    @NotNull(message = "Ativo é um campo obrigatorio!")
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @PrePersist
    private void prePersist(){
        this.cadastro = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.edicao = LocalDateTime.now();
        this.ativo = true;
    }
}
