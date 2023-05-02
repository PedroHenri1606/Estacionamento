package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Modelo;
import com.pedroestacionamento.projeto.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

     /*
        {
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
        }
     */

    @Autowired
    private ModeloService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id){
        final Modelo modelo = service.buscarModeloPorId(id);
        return modelo == null
                ?ResponseEntity.badRequest().body("Nennhum modelo Encontrado")
                :ResponseEntity.ok(modelo);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listaCompleta(){
        try{
            return ResponseEntity.ok(service.listarModelo());

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listarPorAtivo")
    public ResponseEntity<?> buscarPorAtivo(){
        try{
            return ResponseEntity.ok(service.listarPorAtivo());

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
        try {
            service.salvar(modelo);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
        private ResponseEntity<?> editar(
                @PathVariable("id") final Long id,
                @RequestBody final Modelo modelo){
        try {
            service.editar(id,modelo);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "desativar/{id}")
    private ResponseEntity<?> desativar(
            @PathVariable("id") final Long id){
        try{
            service.desativar(id);
            return ResponseEntity.ok("Registro desativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "ativar/{id}")
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
                @RequestBody final Modelo modelo){
        try{
            service.deletar(id,modelo);
            return ResponseEntity.ok("Registro deletado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
