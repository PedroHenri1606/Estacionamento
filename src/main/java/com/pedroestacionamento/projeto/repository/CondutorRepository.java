package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

    @Query("SELECT condutor FROM Condutor condutor WHERE condutor.ativo = true")
    public  List<Condutor> listarPorAtivo();
}
