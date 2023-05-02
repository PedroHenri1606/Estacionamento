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
        configuracao.setAtivo(true);
        return repository.save(configuracao);

    }

    public Configuracao editar(Long id, Configuracao configuracaoNova){
        try{
            final Configuracao configuracaoBanco = repository.findById(id).orElse(null);
            if(configuracaoBanco == null || configuracaoBanco.getId().equals(configuracaoNova.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }

            configuracaoBanco.setValorHora(configuracaoNova.getValorHora());
            configuracaoBanco.setValorMinutoMulta(configuracaoNova.getValorMinutoMulta());
            configuracaoBanco.setInicioExpediente(configuracaoNova.getInicioExpediente());
            configuracaoBanco.setFimExpediente(configuracaoNova.getFimExpediente());
            configuracaoBanco.setTempoParaDesconto(configuracaoNova.getTempoParaDesconto());
            configuracaoBanco.setTempoDeDesconto(configuracaoNova.getTempoDeDesconto());
            configuracaoBanco.setGerarDesconto(configuracaoNova.getGerarDesconto());
            configuracaoBanco.setVagasMoto(configuracaoNova.getVagasMoto());
            configuracaoBanco.setVagasCarro(configuracaoNova.getVagasCarro());
            configuracaoBanco.setVagasVan(configuracaoNova.getVagasVan());

            return repository.save(configuracaoBanco);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }
}
