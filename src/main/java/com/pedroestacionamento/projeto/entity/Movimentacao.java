package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "movimentacoes")
public class Movimentacao extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "veiculos_id", unique = true)
    @NotNull
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "movimentacoes_id")
    @NotNull
    private Condutor condutor;

    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Column(name = "saida")
    private LocalDateTime saida;

    @Column(name = "tempo")
    private LocalTime tempo;

    @Column(name = "tempo_de_desconto")
    private LocalTime tempoDesconto;

    @Column(name = "tempo_multa")
    private LocalTime tempoMulta;

    @Column(name = "valor_de_desconto")
    private BigDecimal valorDesconto;

    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "valor_hora")
    private BigDecimal valorHora;

    @Column(name = "valor_hora_multa")
    private BigDecimal valorHoraMulta;

    public Movimentacao() {}
}
