package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca,Long> {

    @Query("SELECT marca FROM Marca marca WHERE marca.ativo = true")
    public List<Marca> listarPorAtivo();
}
