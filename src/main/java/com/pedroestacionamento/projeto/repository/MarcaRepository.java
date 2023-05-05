package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Marca;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca,Long> {

    @Query("SELECT marca FROM Marca marca WHERE marca.ativo = true")
    public List<Marca> listarPorAtivo();

    @Query("SELECT modelo FROM Modelo modelo WHERE marca.id = :id")
    public List<Movimentacao> buscarModeloPorMarca(@Param("id") final Long marcaId);

    @Query("SELECT marca FROM Marca marca WHERE marca.nome = :nome")
    public boolean verificaNome(@Param("nome")String nome);

    @Transactional
    @Modifying
    @Query("UPDATE Marca marca SET marca.ativo = false WHERE marca.id = :id")
    public void desativar(@Param("id")Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Marca marca SET marca.ativo = true WHERE marca.id = :id")
    public void ativar(@Param("id")Long id);
}
