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
import java.time.LocalDate;
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

        LocalDate dataInicial = LocalDate.from(movimentacao.getEntrada());   LocalTime horarioInicial = LocalTime.from(movimentacao.getEntrada());

        LocalDate dataFinal = LocalDate.from(movimentacao.getSaida());       LocalTime horarioFinal = LocalTime.from(movimentacao.getSaida());

        int diaInicial = dataInicial.getDayOfMonth();         int horaInicial = horarioInicial.getHour();
        int mesInicial = dataInicial.getMonthValue();         int minutoInicial = horarioInicial.getMinute();
        int anoInicial = dataInicial.getYear();               int segundoInicial = horarioInicial.getSecond();

        int diaFinal = dataFinal.getDayOfMonth();             int horaFinal = horarioFinal.getHour();
        int mesFinal = dataFinal.getMonthValue();             int minutoFinal = horarioFinal.getMinute();
        int anoFinal = dataFinal.getYear();                   int segundoFinal = horarioFinal.getSecond();

        int horasAuxiliar = 0;
        int minutosAuxiliar = 0;
        int segundosAuxiliar = 0;


        while (diaInicial != diaFinal) {

            segundoInicial++;
            segundosAuxiliar++;
            if(segundoInicial >= 60){
                minutoInicial++;
                minutosAuxiliar++;
                segundoInicial = 0;
            }

            minutoInicial++;
            minutosAuxiliar++;
            if(minutoInicial >= 60){
                horaInicial++;
                horasAuxiliar++;
                segundoInicial=0;
            }

            horaInicial++;
            horasAuxiliar++;
            if (horaInicial >= 24) {
                horaInicial = 0;
                diaInicial++;
                if (mesInicial == 1 || mesInicial == 3 || mesInicial == 5 || mesInicial == 7 || mesInicial == 8 || mesInicial == 10 || mesInicial == 12) {
                    if (diaInicial > 31) {
                        diaInicial = 1;
                        if (mesInicial == 12) {
                            anoInicial++;
                            mesInicial = 1;
                        }
                    } else {
                        if (diaInicial > 30) {
                            diaInicial = 1;
                            if (mesInicial == 12) {
                                anoInicial++;
                                mesInicial = 1;
                            }
                        }
                    }
                }
            }
        }

        while (segundoInicial != segundoFinal) {
            segundoInicial++;
            segundosAuxiliar++;
            if(segundoInicial >= 60){
                segundoInicial =0;
                minutoInicial++;
                minutosAuxiliar++;
            }
        }

        while (minutoInicial != minutoFinal) {
            minutoInicial++;
            minutosAuxiliar++;
            if(minutoInicial >= 60){
                minutoInicial =0;
                horaInicial++;
            }
        }

        while (horaInicial != horaFinal) {
            horaInicial++;
            horasAuxiliar++;
        }

        if(segundosAuxiliar >= 60){
            minutosAuxiliar++;
            segundosAuxiliar=0;
        }

        if(minutosAuxiliar >= 60){
            horasAuxiliar++;
            minutosAuxiliar=0;
        }

        movimentacao.setTempo(LocalTime.of(horasAuxiliar, minutosAuxiliar, segundosAuxiliar));

        BigDecimal valorHora = new BigDecimal(String.valueOf(configuracao.getValorHora()));

        movimentacao.setValorHora(configuracao.getValorHora());
        movimentacao.setValorHoraMulta(configuracao.getValorMinutoMulta().multiply(new BigDecimal(60)));

        movimentacao.setTempoDesconto(somaTempoDesconto(horasAuxiliar));
        movimentacao.setValorDesconto(new BigDecimal(somaTempoDesconto(horasAuxiliar).getHour()).multiply(configuracao.getValorHora()));

        int horaMulta = 0;
        int minutoMulta = 0;
        int segundoMulta = 0;

        if(movimentacao.getSaida().getHour() > configuracao.getFimExpediente().getHour()) {
            for (int i = configuracao.getFimExpediente().getHour(); i < movimentacao.getSaida().getHour(); i++) {
                horaMulta += 1;
            }
        }

        if(movimentacao.getSaida().getMinute() > configuracao.getFimExpediente().getMinute()) {
            for (int i = configuracao.getFimExpediente().getMinute(); i < movimentacao.getSaida().getMinute(); i++) {
                minutoMulta += 1;
            }
        }

        if(movimentacao.getSaida().getSecond() > configuracao.getFimExpediente().getSecond()){
            for (int i = configuracao.getFimExpediente().getSecond(); i < movimentacao.getSaida().getSecond(); i++){
                segundoMulta +=1;
            }
        }

        movimentacao.setTempoMulta(LocalTime.of(horaMulta,minutoMulta,segundoMulta));
        movimentacao.setValorMulta(new BigDecimal(horaMulta).multiply(new BigDecimal(60))
                                                            .add(new BigDecimal(minutoMulta).multiply(new BigDecimal(configuracao.getValorMinutoMulta().intValue()))));

        int qtdHoras = horasAuxiliar - movimentacao.getTempoMulta().getHour();

        movimentacao.setValorTotal(valorHora.multiply(new BigDecimal(qtdHoras)).subtract(movimentacao.getValorDesconto()).add(movimentacao.getValorMulta()));

        return movimentacao;
        }

        public LocalTime somaTempoDesconto(int horaFinal){
            Configuracao configuracao = configuracaoService.buscarPorId(1L);
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
            return LocalTime.of(horaAuxiliar,minutoAuxiliar,segundoAuxiliar);
        }
    }

