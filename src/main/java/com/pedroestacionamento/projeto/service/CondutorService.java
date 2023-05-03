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

    public Condutor buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }
    public List<Condutor> listarCondutores(){
        return repository.findAll();
    }

    public List<Condutor> listarPorAtivos(){
        return repository.listarPorAtivo();
    }

    public Condutor salvar(Condutor condutor){ return repository.save(condutor); }

    public void desativar(Long id){
        repository.desativar(id);
    }

    public void ativar(Long id){
        repository.ativar(id);
    }

    public Condutor editar(Long id, Condutor condutorNovo){
        final Condutor entidadeBanco = this.buscarPorId(id);

        if(entidadeBanco == null || !entidadeBanco.getId().equals(condutorNovo.getId())){
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }
            return repository.save(condutorNovo);
    }

    public void deletar(Long id){

        final Condutor condutorBanco = this.buscarPorId(id);
        List<Movimentacao> movimentacoes = this.repository.buscarMovimentacaoPorCondutor(condutorBanco.getId());

        if(movimentacoes == null){
            this.repository.deleteById(condutorBanco.getId());
        } else {
            this.repository.desativar(condutorBanco.getId());
            throw new RuntimeException("Condutor possui movimentações, condutor desativado");

        }
    }
}
