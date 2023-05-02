package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import com.pedroestacionamento.projeto.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {

     /*
        {
        "ativo" : true,
        "valorHora": 40.00,
        "valorMinutoMulta": 0.80,
        "inicioExpediente": "08:00:00",
        "fimExpediente": "18:00:00",
        "tempoParaDesconto": "00:00:00",
        "tempoDeDesconto": "00:00:00",
        "gerarDesconto": false,
        "vagasMoto": 15,
        "vagasCarro": 50,
        "vagasVan": 5
        }
     */

    @Autowired
    private ConfiguracaoService service;

    @GetMapping("/listar")
    public ResponseEntity<?> buscarTodos(){
        try {
            return ResponseEntity.ok(service.listarConfiguracoes());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao listar Configurações");
        }
    }

    @PostMapping
    private ResponseEntity<?> cadastrar(@RequestBody final Configuracao configuracao){
        try {
            this.service.salvar(configuracao);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value = "/{id}")
        private ResponseEntity<?> editar(
                @PathVariable("id") final Long id,
                @RequestBody final Configuracao configuracao){
        try {
            this.service.editar(id,configuracao);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
