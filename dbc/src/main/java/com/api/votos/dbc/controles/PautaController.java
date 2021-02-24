package com.api.votos.dbc.controles;

import com.api.votos.dbc.basicas.Pauta;
import com.api.votos.dbc.servico.PautaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class PautaController {

    @Autowired
    private PautaServico servico;

    @PostMapping(value = "/pauta/salvar")
    public ResponseEntity<?> salvar(@RequestBody Pauta pauta) {
        Pauta pautaResponse = this.servico.salvar(pauta);
        return new ResponseEntity(pautaResponse, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/pauta/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Pauta pautaResponse = this.servico.buscarPorId(id);
        return new ResponseEntity(pautaResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/pauta/listar")
    public ResponseEntity<?> listar() {
        List<Pauta> pautas = this.servico.listar();
        return new ResponseEntity(pautas, HttpStatus.OK);
    }
}
