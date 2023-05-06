package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tb_movimentacao", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditTable(value = "tb_movimentacao_audit", schema = "audit")
public class Movimentacao extends AbstractEntity {

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "veiculos_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "condutores_id", nullable = false)
    private Condutor condutor;

    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Getter @Setter
    @Column(name = "saida")
    private LocalDateTime saida;

    @Getter @Setter
    @Column(name = "tempo")
    private LocalTime tempo;

    @Getter @Setter
    @Column(name = "tempo_de_desconto")
    private LocalTime tempoDesconto;

    @Getter @Setter
    @Column(name = "tempo_multa")
    private LocalTime tempoMulta;

    @Getter @Setter
    @Column(name = "valor_de_desconto")
    private BigDecimal valorDesconto;

    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;

    @Getter @Setter
    @Column(name = "valor_hora_multa")
    private BigDecimal valorHoraMulta;

}
