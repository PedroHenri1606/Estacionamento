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

    public Modelo editar(Long id, Modelo modeloNovo){
        try{
            final Modelo modeloBanco = repository.findById(id).orElse(null);
            if(modeloBanco == null || modeloBanco.getId().equals(modeloNovo.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            editarItens(modeloBanco,modeloNovo);
            return repository.save(modeloBanco);

        } catch (EntityNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public void editarItens(Modelo modeloAntigo,Modelo modeloNovo){
        modeloAntigo.setCadastro(modeloNovo.getCadastro());
        modeloAntigo.setEdicao(modeloNovo.getEdicao());
        modeloAntigo.setAtivo(modeloNovo.getAtivo());
        modeloAntigo.setNome(modeloNovo.getNome());
        modeloAntigo.setMarca(modeloNovo.getMarca());
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
                modeloBanco.setAtivo(false);
                repository.save(modeloBanco);
            }
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }
}
