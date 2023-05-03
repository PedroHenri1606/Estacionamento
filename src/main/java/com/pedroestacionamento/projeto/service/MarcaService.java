package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Marca;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.repository.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository repository;

    public Marca buscarMarcaPorId(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Marca> listarMarca(){
        return repository.findAll();
    }

    public List<Marca> listarPorAtivo(){
        return repository.listarPorAtivo();
    }

    public Marca salvar(Marca marca){
        return repository.save(marca);
    }

    public void desativar(Long id){
        repository.desativar(id);
    }

    public void ativar(Long id){
        repository.desativar(id);
    }

    public Marca editar(Long id, Marca marcaNova){
        final Marca marcaBanco = repository.findById(id).orElse(null);

        if(marcaBanco == null || !marcaBanco.getId().equals(marcaNova.getId())){
            throw new RuntimeException("não foi possivel indentificar o registro informado!");
        }
            return  repository.save(marcaNova);
    }

    public void deletar(Long id){
        final Marca marcaBanco = repository.findById(id).orElse(null);
        List<Movimentacao> movimentacoes = this.repository.buscarMovimentacaoPorMarca(marcaBanco.getId());

        if(movimentacoes.isEmpty()){
            this.repository.deleteById(marcaBanco.getId());
        } else {
            this.repository.desativar(marcaBanco.getId());
            throw new RuntimeException("marca possui movimentações, marca desativada!");
        }
    }
}
