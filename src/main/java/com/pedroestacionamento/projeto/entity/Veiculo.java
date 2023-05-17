package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import com.pedroestacionamento.projeto.entity.enums.Cor;
import com.pedroestacionamento.projeto.entity.enums.TipoVeiculo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tb_veiculo", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditTable(value = "tb_veiculo_audit", schema = "audit")
public class Veiculo extends AbstractEntity {

    @Getter @Setter
    @NotBlank(message = "Placa do veículo é um campo obrigatorio!")
    //AQUI TERIA UMA ANOTAÇÃO @PLACA NA PACKAGE VALIDATION
    @Column(name = "placa", length = 10, nullable = false,unique = true)
    private String placa;

    @ManyToOne
    @Getter @Setter
    @NotNull(message = "Modelo é um campo obrigatorio!")
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @NotNull(message = "Cor do veículo é um campo obrigatorio!")
    @Column(name = "cor", length = 20, nullable = false)
    private Cor cor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @NotNull(message = "Tipo do veículo é um campo obrigatorio!")
    @Column(name = "tipo_veiculo", length = 20, nullable = false)
    private TipoVeiculo tipoVeiculo;

    @Getter @Setter
    @NotBlank(message = "Ano do veículo é um campo obrigatorio!")
    @Column(name = "ano", nullable = false)
    private int ano;


}
