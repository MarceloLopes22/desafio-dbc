package com.api.votos.dbc.controles;

import com.api.votos.dbc.basicas.Voto;
import com.api.votos.dbc.dto.ResponseDTO;
import com.api.votos.dbc.dto.VotoRequestDTO;
import com.api.votos.dbc.servico.VotoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class VotoController {

    @Autowired
    private VotoServico servico;

    @PostMapping(value = "/voto/votar")
    public ResponseEntity<?> votar(@RequestBody VotoRequestDTO votoRequestDTO) {
        ResponseDTO retorno = this.servico.votar(votoRequestDTO);
        return new ResponseEntity(retorno, retorno.getHttpStatus());
    }

    @RequestMapping(value = "/voto/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Voto votoResponse = this.servico.buscarPorId(id);
        return new ResponseEntity(votoResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/voto/listar")
    public ResponseEntity<?> listar() {
        List<Voto> votos = this.servico.listar();
        return new ResponseEntity(votos, HttpStatus.OK);
    }
}
