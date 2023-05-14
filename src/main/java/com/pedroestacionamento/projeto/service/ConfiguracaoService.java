package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import com.pedroestacionamento.projeto.repository.ConfiguracaoRepository;
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

    public Configuracao salvar(Configuracao configuracao) {

        if (configuracao.getValorHora() == null) {
            throw new RuntimeException(", valor pora hora não pode estar vazio!");
        }
        if (configuracao.getValorMinutoMulta() == null) {
            throw new RuntimeException(", valor da multa por minuto não pode estar vazio!");
        }
        if (configuracao.getInicioExpediente() == null) {
            throw new RuntimeException(", horario de inicio de expediente não pode estar vazio!");
        }
        if (configuracao.getFimExpediente() == null) {
            throw new RuntimeException(", horario de fim de expediente não pode estar vazio!");
        }
        if (configuracao.getTempoDeDesconto() == null) {
            throw new RuntimeException(", horario de tempo para desconto não pode estar vazio!");
        }
        if (configuracao.getGerarDesconto() == null) {
            throw new RuntimeException(", opção de gerar desconto não pode estar vazio!");
        }
        if (configuracao.getVagasCarro() == null) {
            throw new RuntimeException(", quantidade de vagas de carro não pode estar vazio!");
        }
        if (configuracao.getVagasMoto() == null) {
            throw new RuntimeException(", quantidade de vagas de moto não pode estar vazio!");
        }
        if (configuracao.getVagasVan() == null) {
            throw new RuntimeException(", quantidade de vagas de van não pode estar vazio!");

        } else {
            return repository.save(configuracao);
        }
    }

    public Configuracao editar(Long id, Configuracao configuracaoNova) {
        final Configuracao configuracaoBanco = this.buscarPorId(id);

        if (configuracaoBanco == null || !configuracaoBanco.getId().equals(configuracaoNova.getId())) {
            throw new RuntimeException("Não foi possivel indentificar o registro informado");

        } else {
            return repository.save(configuracaoNova);
        }
    }
}
