package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "condutores", schema = "public")
public class Condutor extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true, length = 15)
    private String cpf;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 17)
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_gasto", nullable = false)
    private LocalTime tempoPago;

    @Getter @Setter
    @Column(name = "tempo_desconto", nullable = false)
    private LocalTime tempoDesconto;

    public Condutor() {
    }

    public Condutor(String nome, String cpf, String telefone, LocalTime tempoPago, LocalTime tempoDesconto) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.tempoPago = tempoPago;
        this.tempoDesconto = tempoDesconto;
    }
}
