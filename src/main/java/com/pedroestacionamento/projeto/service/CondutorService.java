package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.repository.CondutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Condutor salvar(Condutor condutor){
        return repository.save(condutor);
    }

    public Condutor editar(Long id, Condutor condutorNovo){
        try{
           final Condutor entidadeBanco = repository.findById(id).orElse(null);
                        if(entidadeBanco == null || entidadeBanco.getId().equals(condutorNovo.getId())){
                throw new RuntimeException("Não foi possivel indentifcar o registro informado");
            }

            editarItens(entidadeBanco,condutorNovo);
            return repository.save(entidadeBanco);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }

    public void editarItens(Condutor condutor, Condutor entidade){
        condutor.setCadastro(entidade.getCadastro());
        condutor.setEdicao(entidade.getEdicao());
        condutor.setAtivo(entidade.getAtivo());
        condutor.setNome(entidade.getNome());
        condutor.setCpf(entidade.getCpf());
        condutor.setTelefone(entidade.getTelefone());
        condutor.setTempoPago(entidade.getTempoPago());
        condutor.setTempoDesconto(entidade.getTempoDesconto());
    }

    public void deletar(Long id, Condutor condutor){
        try{
            final Condutor condutorBanco = repository.findById(id).orElse(null);
            if(condutorBanco == null || condutorBanco.getId().equals(condutor.getId())) {
                throw new RuntimeException("Não foi possivel indentifcar o registro informado");
            }
            if(!condutorBanco.getAtivo()) {
                repository.deleteById(id);
            } else {
                condutorBanco.setAtivo(false);
                repository.save(condutorBanco);
            }

        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(e);
        }
    }
}
