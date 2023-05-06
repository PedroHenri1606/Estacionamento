package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Condutor;
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

    public Movimentacao buscarPorId(Long id) {
        if (id == 0) {
            throw new RuntimeException(", por favor, informe um valor valido!");

        }else if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar a movimentação informada!");

        }else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Movimentacao> listarMovimentacao(){
        if (repository.findAll().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui movimentações cadastradas!");

        } else {
            return repository.findAll();
        }
    }

    public List<Movimentacao> listarPorAtivo(){
        if(repository.listarPorAtivo().isEmpty()){
            throw new RuntimeException(", banco de dados não possui movimentações ativas!");

        } else {
            return repository.listarPorAtivo();
        }
    }

    public Movimentacao salvar(Movimentacao movimentacao) {

        if (movimentacao.getAtivo() == null) {
            throw new RuntimeException(", ativo não pode ser vazio, selecione uma opção!");

        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException(", veiculo é um campo obrigatorio!");

        } else if (movimentacao.getCondutor() == null) {
            throw new RuntimeException(", condutor é um campo obrigatorio!");

        } else if (movimentacao.getEntrada() == null) {
            throw new RuntimeException(", hora de entrega não pode ser nula");

        } else {
            return repository.save(movimentacao);
        }
    }

    public void desativar(Long id){
        final Movimentacao movimentacao = this.buscarPorId(id);

        if(!movimentacao.getAtivo()) {
            throw new RuntimeException(", movimentação selecionada já esta desativado!");

        } else {
            repository.desativar(id);
        }
    }

    public Movimentacao editar(Long id, Movimentacao movimentacaoNova){
        final Movimentacao movimentacaoBanco = repository.findById(id).orElse(null);

        if(movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacaoNova.getId())) {
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
            return this.salvar(movimentacaoNova);
    }
}
