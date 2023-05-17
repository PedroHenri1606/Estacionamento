package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.configuracao.Configuracao;
import com.pedroestacionamento.projeto.service.ConfiguracaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {

     /*
    {
        "id": 1,
        "cadastro": "2023-05-10T22:23:46.781581",
        "edicao": "2023-05-11T22:07:57.551571",
        "ativo": true,
        "valorHora": 40.00,
        "valorMinutoMulta": 1.00,
        "inicioExpediente": "08:00:00",
        "fimExpediente": "18:00:00",
        "tempoParaDesconto": "01:00:00",
        "tempoDeDesconto": "00:06:00",
        "gerarDesconto": true,
        "vagasMoto": 15,
        "vagasCarro": 50,
        "vagasVan": 5
    }
     */

    @Autowired
    private ConfiguracaoService service;

    @GetMapping("/listar")
    public ResponseEntity<?> buscarTodos(){
        try {
            return ResponseEntity.ok(service.listarConfiguracoes());

        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }

    @PostMapping
    private ResponseEntity<?> cadastrar(@Valid @RequestBody final Configuracao configuracao){
            service.salvar(configuracao);
            return ResponseEntity.ok("Registro cadastrado com sucesso!");
    }
    @PutMapping(value = "/editar")
        private ResponseEntity<?> editar(@Valid @RequestParam("id") final Long id,@RequestBody final Configuracao configuracao){
            service.editar(id,configuracao);
            return ResponseEntity.ok("Registro atualizado com sucesso!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName,errorMessage);
        });

        return errors;
    }
}
