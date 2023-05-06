package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.entity.Veiculo;
import com.pedroestacionamento.projeto.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    public Veiculo buscarPorId(Long id) {
        if (id == 0) {
            throw new RuntimeException(", por favor, informe um valor valido!");

        }else if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar o veiculo informado!");

        }else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Veiculo> listarVeiculos(){
        if (repository.findAll().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui veiculos cadastrados!");

        } else {
            return repository.findAll();
        }
    }

    public List<Veiculo> listarVeiculoPorAtivo(){
        if(repository.listarPorAtivo().isEmpty()){
            throw new RuntimeException(", banco de dados não possui veiculos ativos!");

        } else {
            return repository.listarPorAtivo();
        }
    }

    public Veiculo salvar(Veiculo veiculo) {

        if (veiculo.getAtivo() == null) {
            throw new RuntimeException(", ativo não pode ser vazio, selecione uma opção!");

        } else if (veiculo.getPlaca() == null) {
            throw new RuntimeException(", placa é um campo obrigatorio!");

        } else if (veiculo.getPlaca().isBlank()) {
            throw new RuntimeException(", placa invalida!");

        } else if (!repository.verificarPlaca(veiculo.getPlaca()).isEmpty()) {
            throw new RuntimeException(", placa digitada já esta cadastrada!");

        } else if (veiculo.getModelo() == null) {
            throw new RuntimeException(", modelo do veiculo é um campo obrigatorio!");

        } else if (veiculo.getCor() == null) {
            throw new RuntimeException(", cor do veiculo é um campo obrigatorio!");

        } else if (veiculo.getTipoVeiculo() == null) {
            throw new RuntimeException(", tipo do veiculo é um campo obrigatorio!");

        } else if (veiculo.getAno() < 1940) {
            throw new RuntimeException(", ano do veiculo é um campo obrigatorio!");

        } else {
            return repository.save(veiculo);
        }
    }

    public void desativar(Long id){
        final Veiculo veiculo = this.buscarPorId(id);

        if(!veiculo.getAtivo()) {
            throw new RuntimeException(", veiculo selecionado já esta desativado!");

        } else {
            repository.desativar(id);
        }
    }

    public void ativar(Long id){
        final Veiculo veiculo = this.buscarPorId(id);

        if(veiculo.getAtivo()){
            throw new RuntimeException(", veiculo selecionado já esta ativado!");

        } else {
            repository.ativar(id);
        }
    }

    public Veiculo editar(Long id, Veiculo veiculoNovo){
        final Veiculo veiculoBanco = this.buscarPorId(id);

        if(veiculoBanco == null || !veiculoBanco.getId().equals(veiculoNovo.getId())){
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
            return this.salvar(veiculoNovo);
    }

    public void deletar(Long id) {
        final Veiculo veiculoBanco = this.buscarPorId(id);

        List<Movimentacao> movimentacoes = repository.listarMovimentacaoPorVeiculo(veiculoBanco.getId());

        if(movimentacoes.isEmpty()){
            this.repository.deleteById(veiculoBanco.getId());

        } else {
            this.repository.desativar(veiculoBanco.getId());
            throw new RuntimeException("veiculo possui movimentações ativas, veiculo desativado!");
        }
    }
}
