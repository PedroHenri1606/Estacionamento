package com.pedroestacionamento.projeto.repositories;

import com.pedroestacionamento.projeto.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {

}
