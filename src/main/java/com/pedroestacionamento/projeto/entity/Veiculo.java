package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.abstractEntity.AbstractEntity;
import com.pedroestacionamento.projeto.entity.enums.Cor;
import com.pedroestacionamento.projeto.entity.enums.TipoVeiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculos", schema = "public")
public class Veiculo extends AbstractEntity {

    @Column(name = "placa", length = 10, nullable = false,unique = true)
    private String placa;

    @Column(name = "modelo", length = 50, nullable = false)
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    private Cor cor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo_veiculo", length = 20, nullable = false)
    private TipoVeiculo tipoVeiculo;

    @Column(name = "ano", nullable = false)
    private int ano;

    public Veiculo() {
    }

    public Veiculo(String placa, Modelo modelo, Cor cor, TipoVeiculo tipoVeiculo, int ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.tipoVeiculo = tipoVeiculo;
        this.ano = ano;
    }

}
