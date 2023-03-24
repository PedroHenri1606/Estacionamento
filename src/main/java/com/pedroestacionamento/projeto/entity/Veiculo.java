package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import com.pedroestacionamento.projeto.entity.enums.Cor;
import com.pedroestacionamento.projeto.entity.enums.TipoVeiculo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "veiculos")
public class Veiculo extends AbstractEntity {


    @Column(name = "placa", length = 10,unique = true)
    @NotNull
    private String placa;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "veiculos_id")
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20)
    @NotNull
    private Cor cor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo_veiculo", length = 20)
    @NotNull
    private TipoVeiculo tipoVeiculo;

    @Column(name = "ano")
    @NotNull
    private int ano;

    @Getter
    @OneToMany(mappedBy = "veiculo")
    private List<Movimentacao> movimentacoes;

    public Veiculo() {}
}
