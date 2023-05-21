package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.entity.Veiculo;
import com.pedroestacionamento.projeto.repository.VeiculoRepository;
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
        if (!repository.verificarPlaca(veiculo.getPlaca()).isEmpty()) {
            throw new RuntimeException(" , veiculo informado já esta cadastrado!");

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
