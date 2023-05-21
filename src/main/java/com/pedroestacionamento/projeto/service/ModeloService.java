package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Modelo;
import com.pedroestacionamento.projeto.entity.Veiculo;
import com.pedroestacionamento.projeto.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository repository;

    public Modelo buscarPorId(Long id) {
        if (id == 0) {
            throw new RuntimeException(", por favor, informe um valor valido!");

        } else if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar o modelo informado!");

        } else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Modelo> listarModelo() {
        if (repository.findAll().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui modelos cadastrados!");

        } else {
            return repository.findAll();
        }
    }

    public List<Modelo> listarPorAtivo() {
        if (repository.listarPorAtivo().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui modelos ativos!");

        } else {
            return repository.listarPorAtivo();
        }
    }

    public Modelo salvar(Modelo modelo) {
        if (!repository.verificaNome(modelo.getNome()).isEmpty()) {
            throw new RuntimeException(", modelo informado já esta cadastrado!");

        } else {
            return repository.save(modelo);
        }
    }

    public void desativar(Long id){
        final Modelo modelo = buscarPorId(id);

        if(!modelo.getAtivo()) {
            throw new RuntimeException(", modelo selecionado já esta desativado!");

        } else {
            repository.desativar(id);
        }
    }

    public void ativar(Long id) {
        final Modelo modelo = buscarPorId(id);

        if (modelo.getAtivo()) {
            throw new RuntimeException(", modelo selecionado já esta ativado!");

        } else {
            repository.ativar(id);
        }
    }

    public Modelo editar(Long id, Modelo modeloNovo){
        final Modelo modeloBanco = this.buscarPorId(id);

        if(modeloBanco == null || !modeloBanco.getId().equals(modeloNovo.getId())) {
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
            return this.salvar(modeloNovo);
    }

    public void deletar(Long id){
        final Modelo modeloBanco = this.buscarPorId(id);

        List<Veiculo> veiculos = this.repository.buscarVeiculoPorModelo(modeloBanco.getId());

        if(veiculos.isEmpty()){
            this.repository.deleteById(modeloBanco.getId());

        } else {
            this.repository.desativar(modeloBanco.getId());
            throw new RuntimeException("modelo possui veiculos cadastrados ativos, modelo desativado!");
        }

    }
}
