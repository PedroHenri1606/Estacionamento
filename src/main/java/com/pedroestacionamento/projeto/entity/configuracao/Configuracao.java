package com.pedroestacionamento.projeto.entity.configuracao;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "tb_configuracao", schema = "public")
@NoArgsConstructor
public class Configuracao extends AbstractEntity {

    @Getter @Setter
    @NotNull(message = "Valor por hora é um campo obrigatorio!")
    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora;

    @Getter @Setter
    @NotNull(message = "Valor por minuto multa é um campo obrigatorio!")
    @Column(name = "valor_minuto_multa", nullable = false)
    private BigDecimal valorMinutoMulta;

    @Getter @Setter
    @NotNull(message = "Horário de início de expediente é um campo obrigatorio!")
    @Column(name = "inicio_expediente", nullable = false)
    private LocalTime inicioExpediente;

    @Getter @Setter
    @NotNull(message = "Horário de fim de expediente é um campo obrigatorio!")
    @Column(name = "fim_expediente", nullable = false)
    private LocalTime fimExpediente;

    @Getter @Setter
    @NotNull(message = "Valor tempo para desconto é um campo obrigatorio!")
    @Column(name = "tempo_para_desconto", nullable = false)
    private LocalTime tempoParaDesconto;

    @Getter @Setter
    @NotNull(message = "Valor tempo de desconto é um campo obrigatorio!")
    @Column(name = "tempoDeDesconto", nullable = false)
    private LocalTime tempoDeDesconto;

    @Getter @Setter
    @NotNull(message = "Gerar desconto é um campo obrigatorio!")
    @Column(name = "gerar_desconto", nullable = false)
    private Boolean gerarDesconto;

    @Getter @Setter
    @NotNull(message = "Vagas de moto é um campo obrigatorio!")
    @Column(name = "vagas_moto", nullable = false)
    private Integer vagasMoto;

    @Getter @Setter
    @NotNull(message = "Vagas de carro é um campo obrigatorio!")
    @Column(name = "vagas_carro", nullable = false)
    private Integer vagasCarro;

    @Getter @Setter
    @NotNull(message = "Vagas de van é um campo obrigatorio!")
    @Column(name = "vagas_van", nullable = false)
    private Integer vagasVan;

}
