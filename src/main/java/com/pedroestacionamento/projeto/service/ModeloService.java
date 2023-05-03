package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Modelo;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.repository.ModeloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository repository;

    public Modelo buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Modelo> listarModelo(){
        return repository.findAll();
    }

    public List<Modelo> listarPorAtivo(){ return repository.listarPorAtivo();}

    public Modelo salvar(Modelo modelo){
        return  repository.save(modelo);
    }

    public void desativar(Long id){
        repository.desativar(id);
    }

    public void ativar(Long id){
        repository.ativar(id);
    }

    public Modelo editar(Long id, Modelo modeloNovo){
        final Modelo modeloBanco = this.buscarPorId(id);

        if(modeloBanco == null || !modeloBanco.getId().equals(modeloNovo.getId())) {
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
            return repository.save(modeloNovo);
    }

    public void deletar(Long id){
        final Modelo modeloBanco = this.buscarPorId(id);
        List<Movimentacao> movimentacoes = this.repository.buscarVeiculoPorModelo(modeloBanco.getId());

        if(movimentacoes.isEmpty()){
            this.repository.deleteById(modeloBanco.getId());
        } else {
            this.repository.desativar(modeloBanco.getId());
            throw new RuntimeException("modelo possui veiculos ativos, modelo desativado!");
        }

    }
}
