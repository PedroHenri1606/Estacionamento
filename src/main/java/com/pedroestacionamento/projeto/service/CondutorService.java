package com.pedroestacionamento.projeto.service;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.beans.Transient;
import java.util.List;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository repository;

    public Condutor buscarPorId(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new RuntimeException(", não foi possivel localizar o condutor informado!");

        } else {
            return repository.findById(id).orElse(null);
        }
    }

    public List<Condutor> listarCondutores() {
        if (repository.findAll().isEmpty()) {
            throw new RuntimeException(", banco de dados não possui condutores cadastrados!");

        } else {
            return repository.findAll();
        }
    }

    public List<Condutor> listarPorAtivos(){
        if(repository.listarPorAtivo().isEmpty()){
            throw new RuntimeException(", banco de dados não possui condutores ativos!");

        } else {
            return repository.listarPorAtivo();
        }
    }

    public Condutor salvar(Condutor condutor) {

        if(condutor.getAtivo() == null) {
            throw new RuntimeException(", ativo não pode ser nulo, selecione uma opção");

        }else if(condutor.getNome().isEmpty()){
            throw new RuntimeException(", nome não pode estar vazio!");

        }else if(certificaCPFvalido(condutor.getCpf())) {
            throw new RuntimeException(", CPF nulo ou invalido!");

        }else if(repository.verificarCPF(condutor.getCpf())){
            throw new RuntimeException(", CPF digitado, já possui cadastro!");

        }else if(condutor.getTelefone().isEmpty()) {
            throw new RuntimeException(", telefone não pode estar vazio!");

        }else if(repository.verificarTelefone(condutor.getTelefone())) {
            throw new RuntimeException(", telefone digitado, já possui cadastro!");

        }else if(condutor.getTempoPago() == null) {
            throw new RuntimeException(", tempo pago não pode ser nulo, informe 00:00:00 caso não queira atribuir valores");

        }else if(condutor.getTempoDesconto() == null) {
            throw new RuntimeException(", tempo de desconto não pode ser nulo, informe 00:00:00 caso não queira atribuir valores");

        }else{
            return repository.save(condutor);
        }
    }

    public void desativar(Long id){
        final Condutor condutor = this.buscarPorId(id);

        if(condutor == null) {
         throw new RuntimeException(", não foi possivel localizar o condutor informado!");

        } else if(!condutor.getAtivo()) {
            throw new RuntimeException(", condutor selecionado já esta desativado!");

        } else {
            repository.desativar(id);
        }
    }

    public void ativar(Long id){
        final Condutor condutor = this.buscarPorId(id);

        if(condutor == null) {
            throw new RuntimeException(", não foi possivel localizar o condutor informado!");

        } else if(!condutor.getAtivo()){
            throw new RuntimeException(", condutor selecionado já esta ativado!");

        } else {
            repository.ativar(id);
        }
    }

    public Condutor editar(Long id, Condutor condutorNovo){
        final Condutor condutorBanco = this.buscarPorId(id);

        if(condutorBanco == null || !condutorBanco.getId().equals(condutorNovo.getId())){
            throw new RuntimeException("não foi possivel identificar o condutor informado!");
        }
            return this.salvar(condutorNovo);
    }


    public void deletar(Long id){
        final Condutor condutorBanco = this.buscarPorId(id);

        if(condutorBanco == null){
            throw new RuntimeException(", não foi possivel identificar o condutor informado!");
        }

        List<Movimentacao> movimentacoes = this.repository.buscarMovimentacaoPorCondutor(condutorBanco.getId());

        if(movimentacoes.isEmpty()){
            this.repository.deleteById(condutorBanco.getId());
        } else {
            this.repository.desativar(condutorBanco.getId());
            throw new RuntimeException("condutor possui movimentações ativas, condutor desativado!");
        }
    }

    public static boolean certificaCPFvalido(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (Exception erro) {
            return(false);
        }
    }
}
