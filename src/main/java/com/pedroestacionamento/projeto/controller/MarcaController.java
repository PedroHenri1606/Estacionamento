package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Marca;
import com.pedroestacionamento.projeto.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/marca")
public class MarcaController {

     /*
        {
        "id": 1,
        "cadastro": "2023-05-01T22:35:11.143788",
        "edicao": null,
        "ativo": true,
        "nome": "Honda"
        }
     */

    @Autowired
    private MarcaService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id) {
        final Marca marca = service.buscarMarcaPorId(id);
        return marca == null
                ? ResponseEntity.badRequest().body("Nennhuma Marca Encontrado")
                : ResponseEntity.ok(marca);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> buscarTodos() {
        try {
            return ResponseEntity.ok(service.listarMarca());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listarPorAtivo")
    public ResponseEntity<?> buscarPorAtivo() {
        try {
            return ResponseEntity.ok(service.listarPorAtivo());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca) {
        try {
            service.salvar(marca);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    private ResponseEntity<?> editar(
            @PathVariable("id") final Long id,
            @RequestBody final Marca marca) {
        try {
            service.editar(id, marca);
            return ResponseEntity.ok("Registro atualizado com Sucesso");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/desativar/{id}")
    private ResponseEntity<?> desativar(
            @PathVariable("id") final Long id) {
        try {
            service.desativar(id);
            return ResponseEntity.ok("Registro desativado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/ativar/{id}")
    private ResponseEntity<?> ativar(
            @PathVariable("id") final Long id){
        try{
            service.ativar(id);
            return ResponseEntity.ok("Registro ativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
        private ResponseEntity<?> deletar(
                @PathVariable final long id,
                @RequestBody final Marca marca){
        try{
            service.deletar(id,marca);
            return ResponseEntity.ok("Registro deletado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
