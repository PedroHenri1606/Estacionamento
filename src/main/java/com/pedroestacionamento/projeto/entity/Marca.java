package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tb_marca", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditTable(value = "tb_marca_audit", schema = "audit")
public class Marca extends AbstractEntity {

    @Getter @Setter
    @NotBlank(message = "Nome da marca é um campo obrigatorio!")
    @Size(min = 3, message = "O nome da marca deve ter no mínimo 3 caracteres!")
    @Column(name = "nome",nullable = false,unique = true, length = 50)
    private String nome;

  }
