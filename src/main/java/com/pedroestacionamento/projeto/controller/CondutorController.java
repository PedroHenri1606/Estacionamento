package com.pedroestacionamento.projeto.controller;

import com.pedroestacionamento.projeto.entity.Condutor;
import com.pedroestacionamento.projeto.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/condutores")
public class CondutorController {

    @Autowired
    private CondutorService service;

    @GetMapping
    public ResponseEntity<List> findAll(){
        List<Condutor> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

}
