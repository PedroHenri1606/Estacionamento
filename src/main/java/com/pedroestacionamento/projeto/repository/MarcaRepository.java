package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca,Long> {
}
