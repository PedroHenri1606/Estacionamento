package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Modelo;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo,Long> {

    @Query("SELECT modelo FROM Modelo modelo WHERE modelo.ativo = true")
    public List<Modelo> listarPorAtivo();

    @Query("SELECT movimentacao FROM Movimentacao movimentacao WHERE modelo.id = :id")
    public List<Movimentacao> buscarMovimentacaoPorModelo(@Param("id") Long modeloId);

    @Transactional
    @Modifying
    @Query("UPDATE Modelo modelo SET modelo.ativo = false WHERE modelo.id = :id")
    public void desativar(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Modelo modelo SET modelo.ativo = true WHERE modelo.id = :id")
    public void ativar(@Param("id") Long id);
}
