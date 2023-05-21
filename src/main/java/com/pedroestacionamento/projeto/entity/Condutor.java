package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import com.pedroestacionamento.projeto.validation.constraints.CPF;
import com.pedroestacionamento.projeto.validation.constraints.Telefone;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalTime;

@Entity
@Table(name = "tb_condutor", schema = "public")
@NoArgsConstructor
@Audited
@AuditTable(value = "tb_condutores_audit", schema = "audit")
public class Condutor extends AbstractEntity {

    @Getter @Setter
    @NotBlank(message = "Nome do condutor é um campo obrigatorio!")
    @Size(min = 3,max = 50, message = "O nome deve ter no mínimo 3 e no máximo 50 caracteres!")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Getter @Setter
    @CPF(message = "CPF informado não é valido!")
    @NotBlank(message = "CPF do condutor é um campo obrigatorio!")
    @Column(name = "cpf", nullable = false, unique = true, length = 15)
    private String cpf;

    @Getter @Setter
    @NotBlank(message = "Telefone do condutor é um campo obrigatorio!")
    @Telefone(message = "Telefone informado é inválido!")
    @Column(name = "telefone", nullable = false, unique = true, length = 17)
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_gasto")
    private LocalTime tempoPago;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;

    @PrePersist
    public void prePersist(){
        this.setTempoPago(LocalTime.of(0,0,0));
        this.setTempoDesconto(LocalTime.of(0,0,0));
    }

}
