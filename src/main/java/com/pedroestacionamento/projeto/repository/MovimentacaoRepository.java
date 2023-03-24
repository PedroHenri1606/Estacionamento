package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {
}
