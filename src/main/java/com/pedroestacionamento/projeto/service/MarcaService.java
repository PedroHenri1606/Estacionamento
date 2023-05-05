package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Condutor;
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

    public Marca buscarPorId(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar a marca informada!");

        } else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Marca> listarCondutores() {
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

        if(marca.getAtivo() == null) {
            throw new RuntimeException(", ativo não pode ser nulo, selecione uma opção");

        }else if(marca.getNome().isEmpty()) {
            throw new RuntimeException(", nome não pode estar vazio!");

        }else if(repository.verificaNome(marca.getNome())){
            throw new RuntimeException(", nome digitado, já possui cadastro!");

        }else{
            return repository.save(marca);
        }
    }

    public void desativar(Long id){
        final Marca marca = this.buscarPorId(id);

        if(marca== null) {
            throw new RuntimeException(", não foi possivel localizar a marca informada!");

        } else if(!marca.getAtivo()) {
            throw new RuntimeException(", marca selecionada já esta desativado!");

        } else {
            repository.desativar(id);
        }
    }

    public void ativar(Long id){
        final Marca marca = this.buscarPorId(id);

        if(marca == null) {
            throw new RuntimeException(", não foi possivel localizar a marca informada!");

        } else if(!marca.getAtivo()){
            throw new RuntimeException(", marca selecionada já esta ativado!");

        } else {
            repository.ativar(id);
        }
    }

    public Marca editar(Long id, Marca marcaNova){
        final Marca marcaBanco = this.buscarPorId(id);

        if(marcaBanco == null || !marcaBanco.getId().equals(marcaNova.getId())){
            throw new RuntimeException("não foi possivel indentificar o registro informado!");
        }
            return this.salvar(marcaNova);
    }

    public void deletar(Long id) {
        final Marca marcaBanco = this.buscarPorId(id);

        if (marcaBanco == null) {
            throw new RuntimeException(", não foi possivel identificar a marca informado!");
        }

        List<Movimentacao> movimentacoes = this.repository.buscarModeloPorMarca(marcaBanco.getId());

        if (movimentacoes.isEmpty()) {
            this.repository.deleteById(marcaBanco.getId());
        } else {
            this.repository.desativar(marcaBanco.getId());
            throw new RuntimeException("marca possui modelos ativos, marca desativada!");
        }
    }
}
