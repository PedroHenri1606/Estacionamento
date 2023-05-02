package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Veiculo;
import com.pedroestacionamento.projeto.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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
        final Veiculo veiculo = service.buscarVeiculoPorId(id);
        return veiculo == null
                ?ResponseEntity.badRequest().body("Nennhum valor Encontrado")
                :ResponseEntity.ok(veiculo);
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<?> listaCompleta() {
        try {
            return ResponseEntity.ok(service.listarVeiculos());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/listarPorAtivo")
    public ResponseEntity<?> listarPorAtivos(){
        try{
            return ResponseEntity.ok(service.listarVeiculoPorAtivo());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        try {
            service.salvar(veiculo);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
        private ResponseEntity<?> editar(
                @PathVariable("id") final Long id,
                @RequestBody final Veiculo veiculo){
        try {
            service.editar(id,veiculo);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/desativar/{id}")
    private ResponseEntity<?> desativar(
            @PathVariable("id") final Long id){
        try{
            service.desativar(id);
            return ResponseEntity.ok("Registro desativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/ativar/{id}")
    private ResponseEntity<?> ativar(
            @PathVariable("id") final Long id){
        try{
            service.ativar(id);
            return ResponseEntity.ok("Registro desativado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
        private ResponseEntity<?> deletar(
                @PathVariable("id") final long id,
                @RequestBody final Veiculo veiculo){
        try {
            service.deletar(id,veiculo);
            return ResponseEntity.ok("Registro deletado com Sucesso");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
