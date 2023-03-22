package com.pedroestacionamento.projeto.repositories;

import com.pedroestacionamento.projeto.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends JpaRepository<Modelo,Long> {
}
