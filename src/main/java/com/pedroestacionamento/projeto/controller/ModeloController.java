package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Modelo;
import com.pedroestacionamento.projeto.service.ModeloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
        try {
            final Modelo modelo = service.buscarPorId(id);
            return ResponseEntity.ok(modelo);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listaCompleta(){
        try{
            return ResponseEntity.ok(service.listarModelo());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @GetMapping("/listarPorAtivo")
    public ResponseEntity<?> buscarPorAtivo(){
        try{
            return ResponseEntity.ok(service.listarPorAtivo());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody final Modelo modelo) {
        try {
            service.salvar(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping(value = "/editar")
    public ResponseEntity<?> editar(@Valid @RequestParam("id") final Long id, @RequestBody final Modelo modelo){
        try{
            service.editar(id,modelo);
            return ResponseEntity.ok("Registro editado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping(value = "/desativar")
    public ResponseEntity<?> desativar(@RequestParam("id") final Long id){
        try{
            service.desativar(id);
            return ResponseEntity.ok("Registro desativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping(value = "/ativar")
    public ResponseEntity<?> ativar(@RequestParam("id") final Long id){
        try{
            service.ativar(id);
            return ResponseEntity.ok("Registro ativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar")
    public ResponseEntity<?> deletar(@RequestParam final long id){
        try{
            service.deletar(id);
            return ResponseEntity.ok("Registro deletado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }
}
