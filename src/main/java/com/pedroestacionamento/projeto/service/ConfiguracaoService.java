package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import com.pedroestacionamento.projeto.repository.ConfiguracaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository repository;

    public List<Configuracao> listarConfiguracoes(){
        return repository.findAll();
    }

    public Configuracao salvar(Configuracao configuracao) {
        return repository.save(configuracao);
    }

    public Configuracao editar(Long id, Configuracao configuracaoNova){
        final Configuracao configuracaoBanco = this.repository.findById(id).orElse(null);

        if(configuracaoBanco == null || !configuracaoBanco.getId().equals(configuracaoNova.getId())){
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
            return repository.save(configuracaoNova);
    }
}
