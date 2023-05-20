package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Marca;
import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/movimentacao")
public class  MovimentacaoController {

     /*
        {
        "id": 1,
        "cadastro": "2023-05-01T22:44:12.43815",
        "edicao": null,
        "ativo": true,
        "veiculo": {
            "id": 1,
            "cadastro": "2023-05-01T22:40:13.418645",
            "edicao": null,
            "ativo": true,
            "placa": "RHT-5F18",
            "modelo": {
                "id": 1,
                "cadastro": "2023-05-01T22:38:24.311867",
                "edicao": null,
                "ativo": true,
                "nome": "XRE 300",
                "marca": {
                    "id": 1,
                    "cadastro": "2023-05-01T22:35:11.143788",
                    "edicao": null,
                    "ativo": true,
                    "nome": "Honda"
                }
            },
            "cor": "VERMELHA",
            "tipoVeiculo": "Moto",
            "ano": 2023
        },
        "condutor": {
            "id": 1,
            "cadastro": "2023-05-01T22:33:44.206545",
            "edicao": null,
            "ativo": true,
            "nome": "Pedro Henrique Vieira de Oliveira",
            "cpf": "111.111.111-11",
            "telefone": "45 998265476",
            "tempoPago": "00:00:00",
            "tempoDesconto": "00:00:00"
        },
        "entrada": "2023-05-01T08:00:00",
        "saida": "2023-05-01T18:00:00",

        "tempo": "10:00:00",  --FEITA
        "tempoDesconto": "01:00:00",  --FEITA
        "tempoMulta": "00:00:00",  --FEITA
        "valorDesconto": 40.00,  --FEITA
        "valorMulta": 0.00,  --FEITA

        "valorTotal": 280.00, --FEITA
        "valorHora": 40.00,  --FEITA
        "valorHoraMulta": 48.00  --FEITA
    }
     */

    @Autowired
    private MovimentacaoService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id){
        try {
            final Movimentacao movimentacao = service.buscarPorId(id);
            return ResponseEntity.ok(movimentacao);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listaCompleta(){
        try{
            return ResponseEntity.ok(service.listarMovimentacao());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody final Movimentacao movimentacao){
            service.salvar(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com sucesso!");
    }

    @PutMapping(value = "/editar")
    public ResponseEntity<?> editar(@Valid @RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try{
            service.editar(id,movimentacao);
            return ResponseEntity.ok("Registro editado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PutMapping(value = "/fechar")
    public ResponseEntity<?> fecharMovimentacao(@RequestParam("id") final Long id){
        try {
            service.fecharMovimentacao(id);
            return ResponseEntity.ok("Movimentação fechada com sucesso, aguardando finalização!");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PutMapping(value = "/finalizar")
    public ResponseEntity<?> finalizar(@RequestParam("id")final Long id){
        try{
            return ResponseEntity.ok(service.finalizarMovimentacao(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
}
