package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Modelo;
import com.pedroestacionamento.projeto.entity.Veiculo;
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

    @Query("SELECT veiculo FROM Veiculo veiculo WHERE veiculo.modelo.id = :id")
    public List<Veiculo> buscarVeiculoPorModelo(@Param("id") final Long modeloId);

    @Query("SELECT modelo FROM Modelo modelo WHERE modelo.nome = :nome")
    public List<Modelo> verificaNome(@Param("nome")String nome);

    @Transactional
    @Modifying
    @Query("UPDATE Modelo modelo SET modelo.ativo = false WHERE modelo.id = :id")
    public void desativar(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Modelo modelo SET modelo.ativo = true WHERE modelo.id = :id")
    public void ativar(@Param("id") Long id);
}
