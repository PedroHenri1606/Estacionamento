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
    public ResponseEntity<?> cadastrar(@RequestBody final Configuracao configuracao){
        try {
            this.service.salvar(configuracao);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao Cadastrar Configuração");
        }
    }
    @PutMapping(value = "/{id}")
        private ResponseEntity<?> editar(
                @PathVariable("id") final Long id,
                @RequestBody final Configuracao configuracao){
        try {
            this.service.editar(id,configuracao);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
