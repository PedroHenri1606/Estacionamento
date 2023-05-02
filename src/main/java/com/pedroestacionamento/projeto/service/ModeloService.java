package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Modelo;
import com.pedroestacionamento.projeto.repository.ModeloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository repository;

    public Modelo buscarModeloPorId(Long id){
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
        try{
            final Modelo modeloBanco = repository.findById(id).orElse(null);
            if(modeloBanco == null || modeloBanco.getId().equals(modeloNovo.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }

            modeloBanco.setNome(modeloNovo.getNome());
            modeloBanco.setMarca(modeloNovo.getMarca());

            return repository.save(modeloBanco);

        } catch (EntityNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public void deletar(Long id, Modelo modelo){
        try{
            final Modelo modeloBanco = repository.findById(id).orElse(null);
            if(modeloBanco == null || modeloBanco.getId().equals(modelo.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            if(!modeloBanco.getAtivo()) {
                repository.deleteById(id);
            } else {
                this.desativar(modeloBanco.getId());
                repository.save(modeloBanco);
            }
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }
}
