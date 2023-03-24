package com.pedroestacionamento.projeto.entity;

import com.pedroestacionamento.projeto.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "condutores")
public class Condutor extends AbstractEntity {

    @Getter @Setter
    @NotNull
    @Column(name = "nome", length = 50)
    private String nome;

    @Getter @Setter
    @NotNull
    @Column(name = "cpf", unique = true, length = 15)
    private String cpf;

    @Getter @Setter
    @NotNull
    @Column(name = "telefone", length = 17)
    private String telefone;

    @Getter @Setter
    @NotNull
    @Column(name = "tempo_gasto")
    private LocalTime tempoPago;

    @Getter @Setter
    @NotNull
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;

    @Getter
    @OneToMany(mappedBy = "condutor")
    private List<Movimentacao> movimentacoes;

    public Condutor() {
    }
}
