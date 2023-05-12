package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import com.pedroestacionamento.projeto.repository.MovimentacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository repository;

    @Autowired
    private ConfiguracaoService configuracaoService;

    public Movimentacao buscarPorId(Long id) {
        if (id == 0) {
            throw new RuntimeException(", por favor, informe um valor valido!");

        } else if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar a movimentação informada!");

        } else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Movimentacao> listarMovimentacao() {
        if (repository.findAll().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui movimentações cadastradas!");

        } else {
            return repository.findAll();
        }
    }

    public List<Movimentacao> listarPorAtivo() {
        if (repository.listarPorAtivo().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui movimentações ativas!");

        } else {
            return repository.listarPorAtivo();
        }
    }

    public Movimentacao salvar(Movimentacao movimentacao) {

        if (movimentacao.getAtivo() == null) {
            throw new RuntimeException(", ativo não pode ser vazio, selecione uma opção!");

        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException(", veiculo é um campo obrigatorio!");

        } else if (movimentacao.getCondutor() == null) {
            throw new RuntimeException(", condutor é um campo obrigatorio!");

        } else if (movimentacao.getEntrada() == null) {
            throw new RuntimeException(", hora de entrega não pode ser nula");

        } else {
            return repository.save(movimentacao);
        }
    }

    public void desativar(Long id) {
        final Movimentacao movimentacao = this.buscarPorId(id);

        if (!movimentacao.getAtivo()) {
            throw new RuntimeException(", movimentação selecionada já esta desativado!");

        } else {
            repository.desativar(id);
        }
    }

    public Movimentacao editar(Long id, Movimentacao movimentacaoNova) {
        final Movimentacao movimentacaoBanco = repository.findById(id).orElse(null);

        if (movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacaoNova.getId())) {
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
        return this.salvar(movimentacaoNova);
    }

    public Movimentacao finalizarMovimentacao(Long id) {
        Movimentacao movimentacao = buscarPorId(id);

        Configuracao configuracao = configuracaoService.buscarPorId(1L);

        LocalTime inicio = LocalTime.from(movimentacao.getEntrada());
        LocalTime fim = LocalTime.from(movimentacao.getSaida());

        int horaFinal;
        int minutoFinal;
        int segundoFinal;

        if (inicio.getHour() > fim.getHour()) {
            horaFinal = inicio.getHour() - fim.getHour();
        } else {
            horaFinal = fim.getHour() - inicio.getHour();
        }

        if (inicio.getMinute() > fim.getMinute()) {
            minutoFinal = inicio.getMinute() - fim.getMinute();
        } else {
            minutoFinal = fim.getMinute() - inicio.getMinute();
        }

        if (inicio.getSecond() > fim.getSecond()) {
            segundoFinal = inicio.getSecond() - fim.getSecond();
        } else {
            segundoFinal = fim.getSecond() - inicio.getSecond();
        }

        movimentacao.setTempo(LocalTime.of(horaFinal, minutoFinal, segundoFinal));

        BigDecimal valorHora = new BigDecimal(String.valueOf(configuracao.getValorHora()));
        BigDecimal qtdHoras = new BigDecimal(horaFinal);

        movimentacao.setValorHora(configuracao.getValorHora());
        movimentacao.setValorHoraMulta(configuracao.getValorMinutoMulta().multiply(new BigDecimal(60)));

        LocalTime tempoDesconto = LocalTime.from(configuracao.getTempoDeDesconto());

        int horaDesconto = tempoDesconto.getHour();
        int minutoDesconto = tempoDesconto.getMinute();
        int segundoDesconto = tempoDesconto.getSecond();

        int segundoAuxiliar = 0;
        int minutoAuxiliar = 0;
        int horaAuxiliar = 0;

        for (int i=0 ; i < horaFinal; i++){
            segundoAuxiliar += segundoDesconto;
            minutoAuxiliar += minutoDesconto;
            horaAuxiliar += horaDesconto;

            if(segundoAuxiliar >= 60){
                segundoAuxiliar = segundoAuxiliar -60;
                minutoAuxiliar+=1;

            } else if(minutoAuxiliar >= 60){
                minutoAuxiliar = minutoAuxiliar -60;
                horaAuxiliar +=1;
            }
        }

        movimentacao.setTempoDesconto(LocalTime.of(horaAuxiliar, minutoAuxiliar, segundoAuxiliar));
        movimentacao.setValorDesconto(new BigDecimal(horaAuxiliar).multiply(configuracao.getValorHora()));
        movimentacao.setValorTotal((valorHora.multiply(qtdHoras)).subtract(movimentacao.getValorDesconto()));
        return movimentacao;
        }
    }

