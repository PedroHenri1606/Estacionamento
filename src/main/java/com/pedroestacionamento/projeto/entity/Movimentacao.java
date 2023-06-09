package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class    Movimentacao extends AbstractEntity {

    @ManyToOne
    @Getter @Setter
    @NotNull(message = "Veiculo é um campo obrigatorio!")
    @JoinColumn(name = "veiculos_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @Getter @Setter
    @NotNull(message = "Condutor é um campo obrigatorio!")
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

    @PrePersist
    public void prePersist(){
        this.setEntrada(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return              ("---------------------------------------------" + "\n") +
                            ("-    PARA FINS DE CONFERÊNCIA DO CLIENTE    -" + "\n") +
                            ("---------------------------------------------" + "\n") +
                            ("Entrada: " + this.getEntrada().getDayOfMonth() + "/" + this.getEntrada().getMonthValue() + "/" + this.getEntrada().getYear() + " | " + this.getEntrada().getHour() + ":" + this.getEntrada().getMinute() + ":" + this.getEntrada().getSecond()  + "\n") +
                            ("Saida: " + this.getSaida().getDayOfMonth() + "/" + this.getSaida().getMonthValue() + "/" + this.getSaida().getYear() + " | " + this.getSaida().getHour() + ":" + this.getSaida().getMinute() + ":" + this.getSaida().getSecond()  + "\n") +
                            ("Condutor: " + this.getCondutor().getNome() + "\n") +
                            ("Veiculo: " + this.getVeiculo().getModelo().getNome() + " / Placa: " + this.getVeiculo().getPlaca() + "\n") +
                            ("---------------------------------------------" + "\n") +
                            ("-             DADOS DO CONDUTOR             -" + "\n") +
                            ("---------------------------------------------" + "\n") +
                            ("Período estacionado: " + (this.getCondutor().getTempoPago()/60/60) + " horas" + "\n") +
                            ("Período de desconto armazenado: " + this.getCondutor().getTempoDesconto() + "\n") +
                            ("---------------------------------------------" + "\n") +
                            ("Horas utilizadas: " + this.getTempo() + "\n") +
                            ("Tempo de desconto: " + this.getTempoDesconto() + "\n") +
                            ("Valor por hora utilizada: " + this.getValorHora() + "\n") +
                            ("Valor de desconto: " + this.getValorDesconto() + "\n") +
                            ("Valor multa por atraso: " + this.getValorMulta() + "\n") +
                            ("---------------------------------------------" + "\n") +
                            ("VALOR TOTAL: " + this.getValorTotal() + "\n") +
                            ("---------------------------------------------" + "\n") +
                            ("- *PERÍODOS DE DESCONTO MENORES QUE 1 HORA* -" + "\n") +
                            ("-   *SERÃO ARMAZENADOS ATÉ ATINGIR 1 HORA*  -" + "\n") +
                            ("---------------------------------------------" + "\n");
    }
}
