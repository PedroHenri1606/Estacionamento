package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.repository.MovimentacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository repository;

    public Movimentacao buscarMovimentacaoPorId(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Movimentacao> listarMovimentacao(){
        return repository.findAll();
    }

    public List<Movimentacao> listarPorAtivo(){
        return repository.listarPorAtivo();
    }

    public Movimentacao salvar(Movimentacao movimentacao){
        return repository.save(movimentacao);
    }

    public void desativar(Long id){
        repository.desativar(id);
    }

    public Movimentacao editar(Long id, Movimentacao movimentacaoNova){
        final Movimentacao movimentacaoBanco = repository.findById(id).orElse(null);

        if(movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacaoNova.getId())) {
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
            return repository.save(movimentacaoNova);
    }
}
