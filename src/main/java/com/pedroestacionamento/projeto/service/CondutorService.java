package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository repository;

    public Condutor buscarPorId(Long id) {
        if (id == 0) {
            throw new RuntimeException(", por favor, informe um valor valido!");

        }else if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar o condutor informado!");

        }else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Condutor> listarCondutores() {
        if (repository.findAll().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui condutores cadastrados!");

        } else {
            return repository.findAll();
        }
    }

    public List<Condutor> listarPorAtivos(){
        if(repository.listarPorAtivo().isEmpty()){
            throw new RuntimeException(", banco de dados não possui condutores ativos!");

        } else {
            return repository.listarPorAtivo();
        }
    }


    public Condutor salvar(Condutor condutor) {
        if (!repository.verificarCPF(condutor.getCpf()).isEmpty()) {
            throw new RuntimeException(", condutor informado já esta cadastrado!");

        } else if(!repository.verificarTelefone(condutor.getTelefone()).isEmpty()){
            throw new RuntimeException(", telefone informado já cadastrado para outro condutor!");

        } else {
            return repository.save(condutor);
        }
    }

    public void desativar(Long id){
        final Condutor condutor = this.buscarPorId(id);

        if(!condutor.getAtivo()) {
            throw new RuntimeException(", condutor selecionado já esta desativado!");

        } else {
            repository.desativar(id);
        }
    }

    public void ativar(Long id){
        final Condutor condutor = this.buscarPorId(id);

        if(condutor.getAtivo()){
            throw new RuntimeException(", condutor selecionado já esta ativado!");

        } else {
            repository.ativar(id);
        }
    }

    public Condutor editar(Long id, Condutor condutorNovo){
        final Condutor condutorBanco = this.buscarPorId(id);

        if(condutorBanco == null || !condutorBanco.getId().equals(condutorNovo.getId())){
            throw new RuntimeException("não foi possivel identificar o condutor informado!");
        }
            return repository.save(condutorNovo);
    }

    public void deletar(Long id){
        final Condutor condutorBanco = this.buscarPorId(id);

        List<Movimentacao> movimentacoes = this.repository.buscarMovimentacaoPorCondutor(condutorBanco.getId());

        if (movimentacoes.isEmpty()) {
            this.repository.deleteById(condutorBanco.getId());

        } else {
            this.repository.desativar(condutorBanco.getId());
            throw new RuntimeException("condutor possui movimentações ativas, condutor desativado!");
        }
    }
}
