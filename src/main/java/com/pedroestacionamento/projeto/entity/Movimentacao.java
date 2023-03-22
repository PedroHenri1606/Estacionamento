package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "movimentacoes", schema = "public")
public class Movimentacao extends AbstractEntity {

    @Column(name = "veiculo", nullable = false, unique = true)
    private Veiculo veiculo;

    @Column(name = "condutor", nullable = false)
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

    public Movimentacao() {
    }

    public Movimentacao(Veiculo veiculo, Condutor condutor, LocalDateTime entrada, LocalDateTime saida, LocalTime tempo, LocalTime tempoDesconto, LocalTime tempoMulta, BigDecimal valorDesconto, BigDecimal valorMulta, BigDecimal valorTotal, BigDecimal valorHora, BigDecimal valorHoraMulta) {
        this.veiculo = veiculo;
        this.condutor = condutor;
        this.entrada = entrada;
        this.saida = saida;
        this.tempo = tempo;
        this.tempoDesconto = tempoDesconto;
        this.tempoMulta = tempoMulta;
        this.valorDesconto = valorDesconto;
        this.valorMulta = valorMulta;
        this.valorTotal = valorTotal;
        this.valorHora = valorHora;
        this.valorHoraMulta = valorHoraMulta;
    }

}
