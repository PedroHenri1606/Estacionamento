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
            return ResponseEntity.badRequest().body("Error ao listar Veiculos");
        }
    }

    @GetMapping(value = "/listarPorAtivo")
    public ResponseEntity<?> listarPorAtivos(){
        try{
            return ResponseEntity.ok(service.listarVeiculoPorAtivo());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error ao listar Veiculos ativos");
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        try {
            service.salvar(veiculo);
            return ResponseEntity.ok("Registro cadastrado com Sucesso");

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error ao Cadastrar Veiculo");
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

            return ResponseEntity.badRequest().body("Error ao atualizar Veiculo");

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
            return ResponseEntity.badRequest().body("Error ao Deletar Veiculo");
        }
    }
}
