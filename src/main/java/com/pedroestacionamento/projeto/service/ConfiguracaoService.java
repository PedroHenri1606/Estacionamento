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

    public Configuracao salvar(Configuracao configuracao){
        return repository.save(configuracao);
    }

    public Configuracao editar(Long id, Configuracao configuracaoNova){
        try{
            final Configuracao configuracaoBanco = repository.findById(id).orElse(null);
            if(configuracaoBanco == null || configuracaoBanco.getId().equals(configuracaoNova.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            editarItens(configuracaoBanco,configuracaoNova);
            return repository.save(configuracaoBanco);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }

    public void editarItens(Configuracao configuracao, Configuracao nova){
        configuracao.setCadastro(nova.getCadastro());
        configuracao.setEdicao(nova.getEdicao());
        configuracao.setAtivo(nova.getAtivo());
        configuracao.setValorHora(nova.getValorHora());
        configuracao.setValorMinutoMulta(nova.getValorMinutoMulta());
        configuracao.setInicioExpediente(nova.getInicioExpediente());
        configuracao.setFimExpediente(nova.getFimExpediente());
        configuracao.setTempoParaDesconto(nova.getTempoParaDesconto());
        configuracao.setTempoDeDesconto(nova.getTempoDeDesconto());
        configuracao.setGerarDesconto(nova.getGerarDesconto());
        configuracao.setVagasMoto(nova.getVagasMoto());
        configuracao.setVagasCarro(nova.getVagasCarro());
        configuracao.setVagasVan(nova.getVagasVan());

    }
}
