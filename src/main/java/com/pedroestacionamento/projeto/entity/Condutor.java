package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalTime;

@Entity
@Table(name = "tb_condutor", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditTable(value = "tb_condutores_audit", schema = "audit")
public class Condutor extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true, length = 15)
    private String cpf;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 17)
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_gasto", nullable = false)
    private LocalTime tempoPago;

    @Getter @Setter
    @Column(name = "tempo_desconto", nullable = false)
    private LocalTime tempoDesconto;
}
