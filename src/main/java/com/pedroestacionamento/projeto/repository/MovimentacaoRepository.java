package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Movimentacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {

    @Query("SELECT movimentacao FROM Movimentacao movimentacao WHERE movimentacao.ativo = true")
    public List<Movimentacao> listarPorAtivo();

    @Transactional
    @Modifying
    @Query("UPDATE Movimentacao movimentacao SET movimentacao.ativo = false WHERE movimentacao.id = :id")
    public void desativar(@Param("id")Long id);
}
