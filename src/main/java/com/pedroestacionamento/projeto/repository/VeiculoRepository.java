package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {

    @Query("SELECT veiculo FROM Veiculo veiculo WHERE veiculo.ativo = true")
    public List<Veiculo> listarPorAtivo();
}
