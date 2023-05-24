package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/movimentacao")
public class  MovimentacaoController {

     /*
        {
        "id": 1,
        "cadastro": "2023-05-01T22:44:12.43815",
        "edicao": null,
        "ativo": true,
        "veiculo": { "id": 1},
        "condutor":{ "id": 1},
        "entrada": "2023-05-01T08:00:00",
        "saida": "2023-05-01T18:00:00"
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
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listaCompleta(){
        try{
            return ResponseEntity.ok(service.listarMovimentacao());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
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
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping(value = "/fechar")
    public ResponseEntity<?> fecharMovimentacao(@RequestParam("id") final Long id){
        try {
            service.fecharMovimentacao(id);
            return ResponseEntity.ok("Movimentação fechada com sucesso, aguardando finalização!");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping(value = "/finalizar")
    public ResponseEntity<?> finalizar(@RequestParam("id")final Long id){
        try{
            return ResponseEntity.ok(service.finalizarMovimentacao(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }
}
