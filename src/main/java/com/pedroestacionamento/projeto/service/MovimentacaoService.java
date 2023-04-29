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

    public Movimentacao editar(Long id, Movimentacao movimentacaoNova){
        try{
            final Movimentacao movimentacaoBanco = repository.findById(id).orElse(null);
            if(movimentacaoBanco == null || movimentacaoBanco.getId().equals(movimentacaoNova.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            editarItens(movimentacaoBanco,movimentacaoNova);
            return repository.save(movimentacaoBanco);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }

    public void editarItens(Movimentacao movimentacaoAntiga, Movimentacao movimentacaoNova){

        movimentacaoAntiga.setAtivo(movimentacaoNova.getAtivo());
        movimentacaoAntiga.setCadastro(movimentacaoAntiga.getCadastro());
        movimentacaoAntiga.setEdicao(movimentacaoAntiga.getEdicao());
        movimentacaoAntiga.setEntrada(movimentacaoAntiga.getEntrada());
        movimentacaoAntiga.setSaida(movimentacaoAntiga.getSaida());
        movimentacaoAntiga.setTempo(movimentacaoAntiga.getTempoDesconto());
        movimentacaoAntiga.setTempoDesconto(movimentacaoAntiga.getTempoDesconto());
        movimentacaoAntiga.setTempoMulta(movimentacaoAntiga.getTempoMulta());
        movimentacaoAntiga.setValorDesconto(movimentacaoAntiga.getValorDesconto());
        movimentacaoAntiga.setValorHora(movimentacaoAntiga.getValorHoraMulta());
        movimentacaoAntiga.setValorHoraMulta(movimentacaoAntiga.getValorMulta());
        movimentacaoAntiga.setValorTotal(movimentacaoAntiga.getValorTotal());
        movimentacaoAntiga.setCondutor(movimentacaoAntiga.getCondutor());
        movimentacaoAntiga.setVeiculo(movimentacaoAntiga.getVeiculo());
    }

    public Movimentacao desativarMovimentacao(Long id,Movimentacao movimentacao){
        try{
            final Movimentacao movimentacaoBanco = repository.findById(id).orElse(null);
            if(movimentacaoBanco == null || movimentacaoBanco.getId().equals(movimentacao.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            movimentacaoBanco.setAtivo(false);
            return repository.save(movimentacaoBanco);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }

    }
}
