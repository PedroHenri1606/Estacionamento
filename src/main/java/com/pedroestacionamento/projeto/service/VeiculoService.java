package com.pedroestacionamento.projeto.service;

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

    public Veiculo buscarVeiculoPorId(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Veiculo> listarVeiculos(){
        return repository.findAll();
    }

    public List<Veiculo> listarVeiculoPorAtivo(){
        return repository.listarPorAtivo();
    }

    public Veiculo salvar(Veiculo veiculo) { return  repository.save(veiculo);}

    public void desativar(Long id){repository.desativar(id);}

    public void ativar(Long id){repository.ativar(id);}

    public Veiculo editar(Long id, Veiculo veiculoNovo){
        final Veiculo veiculoBanco = this.buscarVeiculoPorId(id);

        if(veiculoBanco == null || !veiculoBanco.getId().equals(veiculoNovo.getId())){
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
            return repository.save(veiculoNovo);
    }

    public void deletar(Long id) {
        final Veiculo veiculoBanco = this.buscarVeiculoPorId(id);
        List<Movimentacao> movimentacoes = repository.listarMovimentacaoPorVeiculo(veiculoBanco.getId());

        if(movimentacoes.isEmpty()){
            this.repository.deleteById(veiculoBanco.getId());
        } else {
            this.repository.desativar(veiculoBanco.getId());
            throw new RuntimeException("veiculo possui movimentações ativas, veiculo desativado!");
        }
    }
}
