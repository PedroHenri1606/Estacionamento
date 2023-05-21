package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Marca;
import com.pedroestacionamento.projeto.entity.Modelo;
import com.pedroestacionamento.projeto.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository repository;

    public Marca buscarPorId(Long id) {
        if(id == 0){
            throw new RuntimeException(", por favor, informe um valor valido!");

        }else if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar a marca informada!");

        }else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Marca> listarMarca() {
        if (repository.findAll().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui marcas cadastradas!");

        } else {
            return repository.findAll();
        }
    }

    public List<Marca> listarPorAtivos(){
        if(repository.listarPorAtivo().isEmpty()){
            throw new RuntimeException(", banco de dados não possui marcas ativas!");

        } else {
            return repository.listarPorAtivo();
        }
    }

    public Marca salvar(Marca marca) {
        if (!repository.verificaNome(marca.getNome()).isEmpty()) {
            throw new RuntimeException(", marca informada já esta cadastrada!");

        } else {
            return repository.save(marca);
        }
    }

    public void desativar(Long id) {
        final Marca marca = this.buscarPorId(id);

        if(!marca.getAtivo()) {
            throw new RuntimeException(", marca selecionada já esta desativada!");

        } else {
            repository.desativar(id);
        }
    }

    public void ativar(Long id){
        final Marca marca = this.buscarPorId(id);

        if(marca.getAtivo()){
            throw new RuntimeException(", marca selecionada já esta ativada!");

        } else {
            repository.ativar(id);
        }
    }

    public Marca editar(Long id, Marca marcaNova){
        final Marca marcaBanco = this.buscarPorId(id);

        if(marcaBanco == null || !marcaBanco.getId().equals(marcaNova.getId())){
            throw new RuntimeException("não foi possivel indentificar a marca informada!;");
        }
            return this.salvar(marcaNova);
    }

    public void deletar(Long id) {
        final Marca marcaBanco = this.buscarPorId(id);

        List<Modelo> modelos = this.repository.buscarModeloPorMarca(marcaBanco.getId());

        if (modelos.isEmpty()) {
            this.repository.deleteById(marcaBanco.getId());

        } else {
            this.repository.desativar(marcaBanco.getId());
            throw new RuntimeException("marca possui modelos ativos, marca desativada!");
        }
    }
}

