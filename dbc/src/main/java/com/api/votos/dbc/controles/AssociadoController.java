package com.api.votos.dbc.controles;

import com.api.votos.dbc.basicas.Associado;
import com.api.votos.dbc.servico.AssociadoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class AssociadoController {

    @Autowired
    private AssociadoServico servico;

    @PostMapping(value = "/associado/salvar")
    public ResponseEntity<?> salvar(@RequestBody Associado associado) {
        Associado associadoResponse = this.servico.salvar(associado);
        return new ResponseEntity(associadoResponse, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/associado/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Associado associadoResponse = this.servico.buscarPorId(id);
        return new ResponseEntity(associadoResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/associado/listar")
    public ResponseEntity<?> listar() {
        List<Associado> associados = this.servico.listar();
        return new ResponseEntity(associados, HttpStatus.OK);
    }
}
