package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import com.pedroestacionamento.projeto.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            return repository.save(movimentacao);
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
        final Movimentacao movimentacaoBanco = buscarPorId(id);

        if (movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacaoNova.getId())) {
            throw new RuntimeException("Não foi possivel indentificar o registro informado");
        }
        return this.salvar(movimentacaoNova);
    }

    public Movimentacao fecharMovimentacao(Long id){
        final Movimentacao movimentacaoBanco = buscarPorId(id);
        movimentacaoBanco.setSaida(LocalDateTime.now());

        return this.salvar(movimentacaoBanco);
    }

    public String finalizarMovimentacao(Long id) {

        Movimentacao movimentacao = buscarPorId(id);
        Configuracao configuracao = configuracaoService.buscaUltimaConfiguracaoCadastrada();
        Condutor condutor = condutorService.buscarPorId(movimentacao.getCondutor().getId());

        if(movimentacao.getSaida() == null){
            throw new RuntimeException(", movimentação selecionada ainda não foi fechada!");
        }

        LocalDate dataEntrada = LocalDate.from(movimentacao.getEntrada());   LocalTime horarioEntrada = LocalTime.from(movimentacao.getEntrada());
        LocalDate dataSaida = LocalDate.from(movimentacao.getSaida());       LocalTime horarioSaida = LocalTime.from(movimentacao.getSaida());

        int diaEntrada = dataEntrada.getDayOfMonth();         int horaEntrada = horarioEntrada.getHour();
        int mesEntrada = dataEntrada.getMonthValue();         int minutoEntrada = horarioEntrada.getMinute();
        int anoEntrada = dataEntrada.getYear();               int segundoEntrada = horarioEntrada.getSecond();

        int diaSaida = dataSaida.getDayOfMonth();             int horaSaida = horarioSaida.getHour();
        int mesSaida = dataSaida.getMonthValue();             int minutoSaida = horarioSaida.getMinute();
        int anoSaida = dataSaida.getYear();                   int segundoSaida = horarioSaida.getSecond();

        int horasTotal = 0;
        int minutosTotal = 0;
        int segundosTotal = 0;

        //ALGORITMO DE CONTAGEM DE TEMPO DE PERMANENCIA DENTRO DO ESTACIONAMENTO
        while (diaEntrada != diaSaida) {

            segundoEntrada++;
            segundosTotal++;
            if(segundoEntrada >= 60){
                minutoEntrada++;
                minutosTotal++;
                segundoEntrada = 0;
            }

            minutoEntrada++;
            minutosTotal++;
            if(minutoEntrada >= 60){
                horaEntrada++;
                horasTotal++;
                minutoEntrada=0;
            }

            horaEntrada++;
            horasTotal++;
            if (horaEntrada >= 24) {
                horaEntrada = 0;
                diaEntrada++;
                if (mesEntrada == 1 || mesEntrada == 3 || mesEntrada == 5 || mesEntrada == 7 || mesEntrada == 8 || mesEntrada == 10 || mesEntrada == 12) {
                    if (diaEntrada > 31) {
                        diaEntrada = 1;
                        if (mesEntrada == 12) {
                            anoEntrada++;
                            mesEntrada = 1;
                        }
                    } else {
                        if (diaEntrada > 30) {
                            diaEntrada = 1;
                            if (mesEntrada == 12) {
                                anoEntrada++;
                                mesEntrada = 1;
                            }
                        }
                    }
                }
            }
        }

        while (segundoEntrada != segundoSaida) {
            segundoEntrada++;
            segundosTotal++;
            if(segundoEntrada >= 60){
                segundoEntrada =0;
                minutoEntrada++;
                minutosTotal++;
            }
        }

        while (minutoEntrada != minutoSaida) {
            minutoEntrada++;
            minutosTotal++;
            if(minutoEntrada >= 60){
                minutoEntrada =0;
                horaEntrada++;
            }
        }

        while (horaEntrada != horaSaida) {
            horaEntrada++;
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
        
            //TEMPO DESCONTO - MOVIMENTAÇÃO
            LocalTime configTempoDesconto = LocalTime.from(configuracao.getTempoDeDesconto());
            int horaDescontoConfig = configTempoDesconto.getHour();
            int minutoDescontoConfig = configTempoDesconto.getMinute();
            int segundoDescontoConfig = configTempoDesconto.getSecond();

            int segundoDesconto = 0;
            int minutoDesconto = 0;
            int horaDesconto = 0;

            for (int i = 0; i < horasTotal; i++) {
                segundoDesconto += segundoDescontoConfig;
                minutoDesconto += minutoDescontoConfig;
                horaDesconto += horaDescontoConfig;

                if (segundoDesconto >= 60) {
                    segundoDesconto = segundoDesconto - 60;
                    minutoDesconto += 1;

                } else if (minutoDesconto >= 60) {
                    minutoDesconto = minutoDesconto - 60;
                    horaDesconto += 1;
                }
            }
            LocalTime tempoDesconto = LocalTime.of(horaDesconto, minutoDesconto, segundoDesconto);

            //TEMPO MULTA - MOVIMENTAÇÃO
            int horaMulta = 0;
            int minutoMulta = 0;
            int segundoMulta = 0;

            if (movimentacao.getSaida().getHour() > configuracao.getFimExpediente().getHour()) {
                for (int i = configuracao.getFimExpediente().getHour(); i < movimentacao.getSaida().getHour(); i++) {
                    horaMulta += 1;
                }

                for (int i = configuracao.getFimExpediente().getMinute(); i < movimentacao.getSaida().getMinute(); i++) {
                    minutoMulta += 1;
                }

                for (int i = configuracao.getFimExpediente().getSecond(); i < movimentacao.getSaida().getSecond(); i++) {
                    segundoMulta += 1;
                }
            }
            LocalTime tempoMulta = LocalTime.of(horaMulta, minutoMulta, segundoMulta);

            //TEMPO PAGO - CONDUTOR
            int segundosPagosCondutor = condutor.getTempoPago().getSecond();
            int minutosPagosCondutor = condutor.getTempoPago().getMinute();
            int horasPagaCondutor = condutor.getTempoPago().getHour();

            int horaTotalMaisHoraPagaCondutor = horasTotal;

            for (int i = 0; i < segundosPagosCondutor; i++) {
                segundosPagosCondutor++;
                if (segundosPagosCondutor >= 60) {
                    segundosPagosCondutor = 0;
                    minutosPagosCondutor++;
                }
            }
            for (int i = 0; i < minutosPagosCondutor; i++) {
                minutosPagosCondutor++;
                if (minutosPagosCondutor >= 60) {
                    minutosPagosCondutor = 0;
                    horasPagaCondutor++;
                }
            }
            if (horasPagaCondutor >= 1) {
                horaTotalMaisHoraPagaCondutor++;
                horasPagaCondutor--;
            }
            condutor.setTempoPago(LocalTime.of(0, minutosTotal, segundosTotal));

            //TEMPO DESCONTO - CONDUTOR
            int segundosDescontoCondutor = condutor.getTempoDesconto().getSecond();
            int minutosDescontoCondutor = condutor.getTempoDesconto().getMinute();
            int horasDescontoCondutor = condutor.getTempoDesconto().getHour();

            for (int i = 0; i < segundoDesconto; i++) {
                segundosDescontoCondutor++;
                if (segundosDescontoCondutor >= 60) {
                    segundosDescontoCondutor = 0;
                    minutosDescontoCondutor++;
                }
            }

            for (int i = 0; i < minutoDesconto; i++) {
                minutosDescontoCondutor++;
                if (minutosDescontoCondutor >= 60) {
                    minutosDescontoCondutor = 0;
                    horasDescontoCondutor++;
                }
            }
            if (horasDescontoCondutor >= 1) {
                horasDescontoCondutor--;
                horaDesconto++;
            }

        condutor.setTempoDesconto(LocalTime.of(0,minutosDescontoCondutor,segundosDescontoCondutor));
        tempoDesconto = LocalTime.of(horaDesconto,minutoDesconto,segundoDesconto);

        movimentacao.setTempo(LocalTime.of(horasTotal, minutosTotal, segundosTotal));
        movimentacao.setValorHora(configuracao.getValorHora());
        movimentacao.setValorHoraMulta(configuracao.getValorMinutoMulta().multiply(new BigDecimal(60)));
        movimentacao.setTempoDesconto(tempoDesconto);
        movimentacao.setValorDesconto(new BigDecimal(tempoDesconto.getHour()).multiply(configuracao.getValorHora()));
        movimentacao.setTempoMulta(tempoMulta);
        movimentacao.setValorMulta(new BigDecimal(tempoMulta.getHour()).multiply(new BigDecimal(60)).add(new BigDecimal(tempoMulta.getMinute()).multiply(new BigDecimal(configuracao.getValorMinutoMulta().intValue()))));
        movimentacao.setValorTotal(new BigDecimal(configuracao.getValorHora().intValue()).multiply((new BigDecimal(horaTotalMaisHoraPagaCondutor).subtract(new BigDecimal(movimentacao.getTempoMulta().getHour())))).subtract(movimentacao.getValorDesconto()).add(movimentacao.getValorMulta()));

        this.desativar(id);
        this.salvar(movimentacao);

        return movimentacao.toString();
        }
    }

