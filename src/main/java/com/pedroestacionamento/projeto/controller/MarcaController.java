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

    @Autowired
    private MarcaService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id){
        final Marca marca = service.buscarMarcaPorId(id);
        return marca == null
                ?ResponseEntity.badRequest().body("Nennhuma Marca Encontrado")
                :ResponseEntity.ok(marca);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> buscarTodos() {
        try{
            return ResponseEntity.ok(service.listarMarca());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao listar Marcas");
        }
    }

    @GetMapping("/listarPorAtivo")
    public ResponseEntity<?> buscarPorAtivo(){
        try{
            return ResponseEntity.ok(service.listarPorAtivo());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao listar Marcas somente por ativos");
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try {
            service.salvar(marca);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao cadastrar Marca");
        }
    }

    @PutMapping(value = "/{id}")
        private ResponseEntity<?> editar(
                @PathVariable("id") final Long id,
                @RequestBody final Marca marca){
        try{
            service.editar(id,marca);
            return ResponseEntity.ok("Registro atualizado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao atualizar Marca");
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
            return ResponseEntity.badRequest().body("Erro ao deletar");
        }
    }
}
