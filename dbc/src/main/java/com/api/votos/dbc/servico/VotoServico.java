package com.api.votos.dbc.servico;

import com.api.votos.dbc.basicas.Voto;
import com.api.votos.dbc.dto.ResponseDTO;
import com.api.votos.dbc.dto.VotoRequestDTO;
import com.api.votos.dbc.enums.TipoVoto;

import java.util.List;

public interface VotoServico {

    ResponseDTO votar(VotoRequestDTO votoRequestDTO);

    List<Voto> listar();

    Voto buscarPorId(Long id);

    Integer contabilizarVotos(TipoVoto tipoVoto, Long pautaId);
}
