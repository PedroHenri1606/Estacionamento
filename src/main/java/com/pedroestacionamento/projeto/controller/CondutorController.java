package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.service.CondutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/condutor")
public class CondutorController {

     /*
            {
            "id": 1,
            "cadastro": "2023-05-01T22:33:44.206545",
            "edicao": null,
            "ativo": true,
            "nome": "Pedro Henrique Vieira de Oliveira",
            "cpf": "111.111.111-11",
            "telefone": "45 998265476",
            "tempoPago": "0",
            "tempoDesconto": "00:00:00"
            }
     */

    @Autowired
    private CondutorService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id) {
        try {
            final Condutor condutor = service.buscarPorId(id);
            return ResponseEntity.ok(condutor);

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<?> listaCompleta() {
      try{
        return ResponseEntity.ok(service.listarCondutores());

      } catch (Exception e){
          return ResponseEntity.badRequest().body("Error " + e.getMessage());
      }
    }

    @GetMapping(value = "/listarPorAtivo")
    public ResponseEntity<?> listarAtivos(){
        try {
            return ResponseEntity.ok(service.listarPorAtivos());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody final Condutor condutor) {
        try {
            return ResponseEntity.ok(service.salvar(condutor));

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PutMapping(value = "/editar")
    public ResponseEntity<?> editar(@Valid @RequestParam("id") final Long id, @RequestBody final Condutor condutorNovo){
        try{
            service.editar(id,condutorNovo);
            return ResponseEntity.ok("Registro editado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PutMapping(value = "/desativar")
    public ResponseEntity<?> desativar(@RequestParam("id") final Long id){
        try {
            service.desativar(id);
            return ResponseEntity.ok("Registro desativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PutMapping(value = "/ativar")
    public ResponseEntity<?> ativar(@RequestParam("id") final Long id){
        try {
            service.ativar(id);
            return ResponseEntity.ok("Registro ativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar")
        public ResponseEntity<?> deletar(@RequestParam("id") final Long id) {

        try {
            service.deletar(id);
            return ResponseEntity.ok("Registro deletado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
}