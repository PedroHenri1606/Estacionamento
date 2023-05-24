package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Veiculo;
import com.pedroestacionamento.projeto.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

     /*
        {
        "id": 1,
        "cadastro": "2023-05-01T22:40:13.418645",
        "edicao": null,
        "ativo": true,
        "placa": "RHT-5F18",
        "modelo": {
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
            },
        "cor": "VERMELHA",
        "tipoVeiculo": "Moto",
        "ano": 2023
        }
     */

    @Autowired
    private VeiculoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") final Long id){
        try {
            final Veiculo veiculo1 = service.buscarPorId(id);
            return ResponseEntity.ok(veiculo1);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<?> listaCompleta() {
        try {
            return ResponseEntity.ok(service.listarVeiculos());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @GetMapping(value = "/listarPorAtivo")
    public ResponseEntity<?> listarPorAtivos(){
        try{
            return ResponseEntity.ok(service.listarVeiculoPorAtivo());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody final Veiculo veiculo) {
        try {
            service.salvar(veiculo);
            return ResponseEntity.ok("Registro cadastrado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PutMapping(value = "/editar")
        private ResponseEntity<?> editar(@Valid @RequestParam("id") final Long id, @RequestBody final Veiculo veiculo){
        try{
            service.editar(id,veiculo);
            return ResponseEntity.ok("Registro editado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping(value = "/desativar")
    private ResponseEntity<?> desativar(@RequestParam("id") final Long id){
        try{
            service.desativar(id);
            return ResponseEntity.ok("Registro desativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping(value = "/ativar")
    private ResponseEntity<?> ativar(@RequestParam("id") final Long id){
        try{
            service.ativar(id);
            return ResponseEntity.ok("Registro ativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar")
        private ResponseEntity<?> deletar(@RequestParam("id") final long id){
        try {
            service.deletar(id);
            return ResponseEntity.ok("Registro deletado com sucesso!");

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }
}
