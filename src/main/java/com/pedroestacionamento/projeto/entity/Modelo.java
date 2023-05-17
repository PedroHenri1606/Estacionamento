package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tb_modelo", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditTable(value = "tb_modelo_audit", schema = "audit")
public class Modelo extends AbstractEntity {

    @Getter @Setter
    @NotBlank(message = "Nome do modelo é um campo obrigatorio!")
    @Size(min = 2,max = 50,message = "O nome do modelo deve ter no mínimo 2 e no máximo 30 caracteres!")
    @Column(name = "nome", nullable = false, unique = true, length = 30)
    private String nome;

    @Getter @Setter
    @ManyToOne
    @NotNull(message = "Marca é um campo obrigatorio!")
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;


}
