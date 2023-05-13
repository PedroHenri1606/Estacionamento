package com.pedroestacionamento.projeto.repository;

import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao,Long> {

    @Query("SELECT configuracao1 FROM Configuracao configuracao1 WHERE configuracao1.id = (SELECT MAX(configuracao2.id) FROM Configuracao configuracao2)")
    public Configuracao buscaUltimaConfiguracaoCadastrada();
}
