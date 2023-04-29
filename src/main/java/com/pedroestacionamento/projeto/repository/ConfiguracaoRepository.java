package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao,Long> {
}
