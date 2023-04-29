package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo,Long> {

    @Query("SELECT modelo FROM Modelo modelo WHERE modelo.ativo = true")
    public List<Modelo> listarPorAtivo();
}
