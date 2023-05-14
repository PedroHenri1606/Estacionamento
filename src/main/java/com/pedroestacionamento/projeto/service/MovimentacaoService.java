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

    @Autowired
    private CondutorService condutorService;

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

    public String finalizarMovimentacao(Long id) {

        Movimentacao movimentacao = buscarPorId(id);
        Configuracao configuracao = configuracaoService.buscaUltimaConfiguracaoCadastrada();
        Condutor condutor = condutorService.buscarPorId(movimentacao.getCondutor().getId());

        LocalDate dataInicial = LocalDate.from(movimentacao.getEntrada());   LocalTime horarioInicial = LocalTime.from(movimentacao.getEntrada());
        LocalDate dataSaida = LocalDate.from(movimentacao.getSaida());       LocalTime horarioSaida = LocalTime.from(movimentacao.getSaida());

        int diaInicial = dataInicial.getDayOfMonth();         int horaInicial = horarioInicial.getHour();
        int mesInicial = dataInicial.getMonthValue();         int minutoInicial = horarioInicial.getMinute();
        int anoInicial = dataInicial.getYear();               int segundoInicial = horarioInicial.getSecond();

        int diaSaida = dataSaida.getDayOfMonth();             int horaSaida = horarioSaida.getHour();
        int mesSaida = dataSaida.getMonthValue();             int minutoSaida = horarioSaida.getMinute();
        int anoSaida = dataSaida.getYear();                   int segundoSaida = horarioSaida.getSecond();

        int horasTotal = 0;
        int minutosTotal = 0;
        int segundosTotal = 0;

        //ALGORITMO DE CONTAGEM DE TEMPO DE PERMANENCIA DENTRO DO ESTACIONAMENTO
        while (diaInicial != diaSaida) {

            segundoInicial++;
            segundosTotal++;
            if(segundoInicial >= 60){
                minutoInicial++;
                minutosTotal++;
                segundoInicial = 0;
            }

            minutoInicial++;
            minutosTotal++;
            if(minutoInicial >= 60){
                horaInicial++;
                horasTotal++;
                segundoInicial=0;
            }

            horaInicial++;
            horasTotal++;
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

        while (segundoInicial != segundoSaida) {
            segundoInicial++;
            segundosTotal++;
            if(segundoInicial >= 60){
                segundoInicial =0;
                minutoInicial++;
                minutosTotal++;
            }
        }

        while (minutoInicial != minutoSaida) {
            minutoInicial++;
            minutosTotal++;
            if(minutoInicial >= 60){
                minutoInicial =0;
                horaInicial++;
            }
        }

        while (horaInicial != horaSaida) {
            horaInicial++;
            horasTotal++;
        }

        if(segundosTotal >= 60){
            minutosTotal++;
            segundosTotal=0;
        }

        if(minutosTotal >= 60){
            horasTotal++;
            minutosTotal=0;
        }

        //TEMPO PAGO - CONDUTOR
        int segundosPagosCondutor = condutor.getTempoPago().getSecond();
        int minutosPagosCondutor = condutor.getTempoPago().getMinute();
        int horasPagaCondutor = condutor.getTempoPago().getHour();

            for(int i = 0; i < segundosTotal; i++){
                segundosPagosCondutor++;
                if(segundosPagosCondutor >= 60){
                    segundosPagosCondutor= 0;
                    minutosPagosCondutor++;
                }
            }
            for(int i = 0; i < minutosTotal; i++){
                minutosPagosCondutor++;
                if(minutosPagosCondutor >=60){
                    minutosPagosCondutor= 0;
                    horasPagaCondutor++;
                }
            }
            for(int i = 0; i < horasTotal; i++){
                horasPagaCondutor++;
            }

        //condutor.setTempoPago(LocalTime.of(horasPagaCondutor,minutosPagosCondutor,segundosPagosCondutor));

        //TEMPO DESCONTO - MOVIMENTAÇÃO
        LocalTime configTempoDesconto = LocalTime.from(configuracao.getTempoDeDesconto());
        int horaDescontoConfig = configTempoDesconto.getHour();
        int minutoDescontoConfig = configTempoDesconto.getMinute();
        int segundoDescontoConfig = configTempoDesconto.getSecond();

        int segundoDesconto = 0;
        int minutoDesconto = 0;
        int horaDesconto = 0;

        for (int i=0 ; i < horasTotal; i++){
            segundoDesconto += segundoDescontoConfig;
            minutoDesconto += minutoDescontoConfig;
            horaDesconto += horaDescontoConfig;

            if(segundoDesconto >= 60){
                segundoDesconto = segundoDesconto -60;
                minutoDesconto+=1;

            } else if(minutoDesconto >= 60){
                minutoDesconto = minutoDesconto -60;
                horaDesconto +=1;
            }
        }

        //TEMPO DESCONTO - CONDUTOR
        int segundosDescontoCondutor = condutor.getTempoDesconto().getSecond();
        int minutosDescontoCondutor = condutor.getTempoDesconto().getMinute();
        int horasDescontoCondutor = condutor.getTempoDesconto().getHour();

        for(int i = 0; i < segundoDesconto; i++) {
            segundosDescontoCondutor++;
            if(segundosDescontoCondutor >= 60){
                segundosDescontoCondutor= 0;
                minutosDescontoCondutor++;
            }
        }

        for(int i = 0; i < minutoDesconto; i++){
            minutosDescontoCondutor++;
            if(minutosDescontoCondutor >= 60){
                minutosDescontoCondutor= 0;
                horasDescontoCondutor++;
            }
        }

        LocalTime tempoDesconto = LocalTime.of(horaDesconto,minutoDesconto,segundoDesconto);

        condutor.setTempoDesconto(LocalTime.of(horasDescontoCondutor,minutosDescontoCondutor,segundosDescontoCondutor));


        //TEMPO MULTA - MOVIMENTAÇÃO
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

        LocalTime tempoMulta = LocalTime.of(horaMulta,minutoMulta,segundoMulta);

        movimentacao.setTempo(LocalTime.of(horasTotal, minutosTotal, segundosTotal));
        movimentacao.setValorHora(configuracao.getValorHora());
        movimentacao.setValorHoraMulta(configuracao.getValorMinutoMulta().multiply(new BigDecimal(60)));
        movimentacao.setTempoDesconto(tempoDesconto);
        movimentacao.setValorDesconto(new BigDecimal(tempoDesconto.getHour()).multiply(configuracao.getValorHora()));
        movimentacao.setTempoMulta(tempoMulta);
        movimentacao.setValorMulta(new BigDecimal(tempoMulta.getHour()).multiply(new BigDecimal(60)).add(new BigDecimal(tempoMulta.getMinute()).multiply(new BigDecimal(configuracao.getValorMinutoMulta().intValue()))));
        movimentacao.setValorTotal(new BigDecimal(configuracao.getValorHora().intValue()).multiply((new BigDecimal(horasTotal).subtract(new BigDecimal(movimentacao.getTempoMulta().getHour())))).subtract(movimentacao.getValorDesconto()).add(movimentacao.getValorMulta()));

        //this.desativar(id);
        this.salvar(movimentacao);

        return movimentacao.toString();
        }
    }

