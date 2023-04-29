package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Movimentacao;
import com.pedroestacionamento.projeto.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id){
        final Movimentacao movimentacao = service.buscarMovimentacaoPorId(id);
        return movimentacao == null
                ?ResponseEntity.badRequest().body("Nennhuma movimentação Encontrada")
                :ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listaCompleta(){
        try{
            return ResponseEntity.ok(service.listarMovimentacao());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao listar Movimentações");
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try {
            service.salvar(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao cadastrar movimentação");
        }
    }

    @PutMapping(value = "/{id}")
        private ResponseEntity<?> editar(
                @PathVariable("id") final Long id,
                @RequestBody final Movimentacao movimentacao){
        try {
            service.editar(id,movimentacao);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao atualizar movimentação");
        }
    }

    @DeleteMapping(value = "/{id}")
        private ResponseEntity<?> deletar(
                @PathVariable final long id,
                @RequestBody final Movimentacao movimentacao){
            service.desativarMovimentacao(id,movimentacao);
            return ResponseEntity.ok("Registro deletado com sucesso");

    }
}
