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

     /*
        {
        "id": 1,
        "cadastro": "2023-05-01T22:33:44.206545",
        "edicao": null,
        "ativo": true,
        "nome": "Pedro Henrique Vieira de Oliveira",
        "cpf": "111.111.111-11",
        "telefone": "45 998265476",
        "tempoPago": "00:00:00",
        "tempoDesconto": "00:00:00"
        }
     */

    @Autowired
    private CondutorService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id) {
        final Condutor condutor = service.buscarPorId(id);
        return condutor == null
                ? ResponseEntity.badRequest().body("Nennhum valor Encontrado")
                : ResponseEntity.ok(condutor);
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<?> listaCompleta() {
      try{
        return ResponseEntity.ok(service.listarCondutores());

      } catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
      }
    }

    @GetMapping(value = "/listarPorAtivo")
    public ResponseEntity<?> listarAtivos(){
        try {
            return ResponseEntity.ok(service.listarPorAtivos());

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    private ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        try {
            service.salvar(condutor);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value = "/{id}")
        private ResponseEntity<?> editar(
                @PathVariable("id") final Long id,
                @RequestBody final Condutor condutorNovo){
        try {
            service.editar(id,condutorNovo);
            return ResponseEntity.ok("Registro atualizado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/desativar/{id}")
    private ResponseEntity<?> desativar(
            @PathVariable("id") final Long id){
        try {
            service.desativar(id);
            return ResponseEntity.ok("Registro atualizado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/ativar/{id}")
    private ResponseEntity<?> ativar(
            @PathVariable("id") final Long id){
        try {
            service.ativar(id);
            return ResponseEntity.ok("Registro atualizado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
        private ResponseEntity<?> deletar(
            @PathVariable("id") final Long id,
            @RequestBody final Condutor condutor) {
        try {
            service.deletar(id,condutor);
            return ResponseEntity.ok("Registro deletado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}