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
        "tempoParaDesconto": "01:00:00",
        "tempoDeDesconto": "00:07:50",
        "gerarDesconto": true,
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
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PostMapping
    private ResponseEntity<?> cadastrar(@RequestBody final Configuracao configuracao){
        try {
            service.salvar(configuracao);
            return ResponseEntity.ok("Registro cadastrado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
    @PutMapping(value = "/editar")
        private ResponseEntity<?> editar(
                @RequestParam("id") final Long id,
                @RequestBody final Configuracao configuracao){
        try {
            service.editar(id,configuracao);
            return ResponseEntity.ok("Registro atualizado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
}
