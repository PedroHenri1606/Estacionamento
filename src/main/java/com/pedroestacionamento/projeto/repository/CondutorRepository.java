package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {

}
