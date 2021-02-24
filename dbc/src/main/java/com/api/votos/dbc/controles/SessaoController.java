package com.api.votos.dbc.controles;

import com.api.votos.dbc.basicas.Sessao;
import com.api.votos.dbc.servico.SessaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class SessaoController {

    @Autowired
    private SessaoServico servico;

    @PostMapping(value = "/sessao/salvar")
    public ResponseEntity<?> salvar(@RequestBody Sessao sessao) {
        Sessao sessaoResponse = this.servico.salvar(sessao);
        return new ResponseEntity(sessaoResponse, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/sessao/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Sessao sessaoResponse = this.servico.buscarPorId(id);
        return new ResponseEntity(sessaoResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/sessao/listar")
    public ResponseEntity<?> listar() {
        List<Sessao> sessaos = this.servico.listar();
        return new ResponseEntity(sessaos, HttpStatus.OK);
    }
}
