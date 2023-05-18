package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Marca;
import com.pedroestacionamento.projeto.service.MarcaService;
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
        try {
            final Marca marca = service.buscarPorId(id);
            return ResponseEntity.ok(marca);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> buscarTodos() {
        try {
            return ResponseEntity.ok(service.listarMarca());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @GetMapping("/listarPorAtivo")
    public ResponseEntity<?> buscarPorAtivo() {
        try {
            return ResponseEntity.ok(service.listarPorAtivos());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " +e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody final Marca marca) {
            service.salvar(marca);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");
    }

    @PutMapping(value = "/editar")
    public ResponseEntity<?> editar(@Valid @RequestParam("id") final Long id, @RequestBody final Marca marca) {
            service.editar(id,marca);
            return ResponseEntity.ok("Registro atualizado com Sucesso");
    }

    @PutMapping(value = "/desativar")
    public ResponseEntity<?> desativar(@RequestParam("id") final Long id) {
        try {
            service.desativar(id);
            return ResponseEntity.ok("Registro desativado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " +e.getMessage());
        }
    }

    @PutMapping(value = "/ativar")
    public ResponseEntity<?> ativar(@RequestParam("id") final Long id){
        try{
            service.ativar(id);
            return ResponseEntity.ok("Registro ativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " +e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar")
        public ResponseEntity<?> deletar(@RequestParam("id") final long id){

        try{
            service.deletar(id);
            return ResponseEntity.ok("Registro deletado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " +e.getMessage());
        }
    }
}
