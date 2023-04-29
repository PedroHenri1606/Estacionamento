package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {

    @Query("SELECT movimentacao FROM Movimentacao movimentacao WHERE movimentacao.ativo = true")
    public List<Movimentacao> listarPorAtivo();
}
