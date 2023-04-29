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
            return ResponseEntity.badRequest().body("Error ao listar Modelos");
        }
    }

    @GetMapping("/listarPorAtivo")
    public ResponseEntity<?> buscarPorAtivo(){
        try{
            return ResponseEntity.ok(service.listarPorAtivo());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao listar Modelos por ativo");
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
        try {
            service.salvar(modelo);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao cadastrar modelo");
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
            return ResponseEntity.badRequest().body("Error ao atualizar modelo");
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
            return ResponseEntity.badRequest().body("Error ao deletar");
        }
    }
}
