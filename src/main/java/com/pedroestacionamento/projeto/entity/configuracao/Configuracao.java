package com.pedroestacionamento.projeto.entity.configuracao;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "configuracoes", schema = "public")
public class Configuracao extends AbstractEntity  {

    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;

    @Getter @Setter
    @Column(name = "valor_minuto_multa")
    private BigDecimal valorMinutoMulta;

    @Getter @Setter
    @Column(name = "inicio_expediente")
    private LocalTime inicioExpediente;

    @Getter @Setter
    @Column(name = "fim_expediente")
    private LocalTime fimExpediente;

    @Getter @Setter
    @Column(name = "tempo_para_desconto")
    private LocalTime tempoParaDesconto;

    @Getter @Setter
    @Column(name = "tempoDeDesconto")
    private LocalTime tempoDeDesconto;

    @Getter @Setter
    @Column(name = "gerar_desconto")
    private Boolean gerarDesconto;

    @Getter @Setter
    @Column(name = "vagas_moto")
    private Integer vagasMoto;

    @Getter @Setter
    @Column(name = "vagas_carro")
    private Integer vagasCarro;

    @Getter @Setter
    @Column(name = "vagas_van")
    private Integer vagasVan;

    public Configuracao() {
    }
}
