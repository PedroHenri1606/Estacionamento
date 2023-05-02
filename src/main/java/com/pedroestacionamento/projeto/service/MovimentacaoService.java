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
        try{
            final Movimentacao movimentacaoBanco = repository.findById(id).orElse(null);
            if(movimentacaoBanco == null || movimentacaoBanco.getId().equals(movimentacaoNova.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }

            movimentacaoBanco.setEntrada(movimentacaoNova.getEntrada());
            movimentacaoBanco.setSaida(movimentacaoNova.getSaida());
            movimentacaoBanco.setTempo(movimentacaoNova.getTempoDesconto());
            movimentacaoBanco.setTempoDesconto(movimentacaoNova.getTempoDesconto());
            movimentacaoBanco.setTempoMulta(movimentacaoNova.getTempoMulta());
            movimentacaoBanco.setValorDesconto(movimentacaoNova.getValorDesconto());
            movimentacaoBanco.setValorHora(movimentacaoNova.getValorHoraMulta());
            movimentacaoBanco.setValorHoraMulta(movimentacaoNova.getValorMulta());
            movimentacaoBanco.setValorTotal(movimentacaoNova.getValorTotal());
            movimentacaoBanco.setCondutor(movimentacaoNova.getCondutor());
            movimentacaoBanco.setVeiculo(movimentacaoNova.getVeiculo());

            return repository.save(movimentacaoBanco);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }

    public Movimentacao desativarMovimentacao(Long id,Movimentacao movimentacao){
        try{
            final Movimentacao movimentacaoBanco = repository.findById(id).orElse(null);
            if(movimentacaoBanco == null || movimentacaoBanco.getId().equals(movimentacao.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            this.desativar(movimentacaoBanco.getId());
            return repository.save(movimentacaoBanco);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }

    }
}
