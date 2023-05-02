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
        try{
            final Veiculo veiculoBanco = repository.findById(id).orElse(null);
            if(veiculoBanco == null || veiculoBanco.getId().equals(veiculoNovo.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }

            veiculoBanco.setModelo(veiculoNovo.getModelo());
            veiculoBanco.setCor(veiculoNovo.getCor());
            veiculoBanco.setTipoVeiculo(veiculoNovo.getTipoVeiculo());
            veiculoBanco.setAno(veiculoNovo.getAno());
            veiculoBanco.setPlaca(veiculoNovo.getPlaca());

            return repository.save(veiculoBanco);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }

    public void deletar(Long id, Veiculo veiculo){
        try{
            final Veiculo veiculoBanco = repository.findById(id).orElse(null);
            if(veiculoBanco == null || veiculoBanco.getId().equals(veiculo.getId())){
                throw new RuntimeException("Não foi possivel indentificar o registro informado");
            }
            if(!veiculoBanco.getAtivo()) {
                repository.deleteById(id);
            } else {
                this.desativar(veiculoBanco.getId());
                repository.save(veiculoBanco);
            }
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e);
        }
    }
}
