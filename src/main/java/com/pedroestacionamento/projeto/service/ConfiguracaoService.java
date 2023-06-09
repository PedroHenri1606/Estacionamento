package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import com.pedroestacionamento.projeto.repository.ConfiguracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository repository;

    public Configuracao buscarPorId(Long id) {
        if (id == 0) {
            throw new RuntimeException(", por favor, informe um valor valido!");

        } else if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar a configuração informada");

        } else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Configuracao> listarConfiguracoes(){
        if(repository.findAll().isEmpty()){
            throw new RuntimeException(", banco de dados não possui configuração cadastrada!");

        } else {
            return repository.findAll();
        }
    }

    public Configuracao buscaUltimaConfiguracaoCadastrada(){
        if(repository.findAll().isEmpty()){
            throw new RuntimeException(", banco de dados não possui configuração cadastrada!");

        } else {
            return repository.buscaUltimaConfiguracaoCadastrada();
        }
    }

    @Transactional
    public Configuracao salvar(Configuracao configuracao) {
            return repository.save(configuracao);
    }

    @Transactional
    public Configuracao editar(Long id, Configuracao configuracaoNova) {
        final Configuracao configuracaoBanco = this.buscarPorId(id);

        if (configuracaoBanco == null || !configuracaoBanco.getId().equals(configuracaoNova.getId())) {
            throw new RuntimeException("Não foi possivel indentificar o registro informado");

        } else {
            return repository.save(configuracaoNova);
        }
    }
}
