package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import com.pedroestacionamento.projeto.entity.enums.Cor;
import com.pedroestacionamento.projeto.entity.enums.TipoVeiculo;
import jakarta.persistence.*;
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
@AuditTable(value = "veiculo_audit", schema = "audit")
public class Veiculo extends AbstractEntity {

    @Getter @Setter
    @Column(name = "placa", length = 10, nullable = false,unique = true)
    private String placa;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    private Cor cor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo_veiculo", length = 20, nullable = false)
    private TipoVeiculo tipoVeiculo;

    @Getter @Setter
    @Column(name = "ano", nullable = false)
    private int ano;


}
