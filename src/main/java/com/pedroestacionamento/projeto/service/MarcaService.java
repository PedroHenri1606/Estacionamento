package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Marca;
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

    public Marca editar(Long id, Marca marcaNova){
        try{
            final Marca marcaBanco = repository.findById(id).orElse(null);
            if(marcaBanco == null || marcaBanco.getId().equals(marcaNova.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            editarItens(marcaBanco,marcaNova);
            return  repository.save(marcaBanco);
        }
        catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }

    public void editarItens(Marca marcaAntiga, Marca marcaEditada){
        marcaAntiga.setCadastro(marcaEditada.getCadastro());
        marcaAntiga.setEdicao(marcaEditada.getEdicao());
        marcaAntiga.setAtivo(marcaEditada.getAtivo());
        marcaAntiga.setNome(marcaEditada.getNome());
    }

    public void deletar(Long id,Marca marca){
        try{
            final Marca marcaBanco = repository.findById(id).orElse(null);
            if(marcaBanco == null || marcaBanco.getId().equals(marca.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            if(!marcaBanco.getAtivo()) {
                repository.deleteById(id);
            } else {
                marcaBanco.setAtivo(false);
                repository.save(marcaBanco);
            }

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }

    }
}
