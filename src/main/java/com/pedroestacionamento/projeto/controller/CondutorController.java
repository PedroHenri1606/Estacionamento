package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    @Autowired
    private CondutorService condutorService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id) {
        final Condutor condutor = condutorService.buscarPorId(id);
        return condutor == null
                ? ResponseEntity.badRequest().body("Nennhum valor Encontrado")
                : ResponseEntity.ok(condutor);
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<?> listaCompleta() {
      try{
        return ResponseEntity.ok(condutorService.listarCondutores());

      } catch (Exception e){
          return ResponseEntity.badRequest().body("Error ao listar Condutores");
      }
    }

    @GetMapping(value = "/listarPorAtivo")
    public ResponseEntity<?> listarPorAtivo(){
        return ResponseEntity.ok(condutorService.listarPorAtivos());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        try {
            condutorService.salvar(condutor);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao cadastrar Condutor");
        }
    }
    @PutMapping(value = "/{id}")
        private ResponseEntity<?> editar(
                @PathVariable("id") final Long id,
                @RequestBody final Condutor condutorNovo){
        try {
            condutorService.editar(id,condutorNovo);
            return ResponseEntity.ok("Registro atualizado com Sucesso");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao atualizar Condutor");
        }
    }
    @DeleteMapping(value = "/{id}")
        private ResponseEntity<?> deletar(
            @PathVariable("id") final Long id,
            @RequestBody final Condutor condutor) {
        try {
            condutorService.deletar(id,condutor);
            return ResponseEntity.ok("Registro deletado com Sucesso");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao deletar Condutor");
        }
    }
}
