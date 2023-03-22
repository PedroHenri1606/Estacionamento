package com.pedroestacionamento.projeto.repositories;

import com.pedroestacionamento.projeto.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {
}
