package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.entity.Veiculo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {

    @Query("SELECT veiculo FROM Veiculo veiculo WHERE veiculo.ativo = true")
    public List<Veiculo> listarPorAtivo();

    @Query("SELECT movimentacao FROM Movimentacao movimentacao WHERE veiculo.id = :id")
    public List<Movimentacao> listarMovimentacaoPorVeiculo(@Param("id") final Long veiculoId);

    @Transactional
    @Modifying
    @Query("UPDATE Veiculo veiculo SET veiculo.ativo = false WHERE veiculo.id = :id")
    public void desativar(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Veiculo veiculo SET veiculo.ativo = true WHERE veiculo.id = :id")
    public void ativar(@Param("id") Long id);
}
