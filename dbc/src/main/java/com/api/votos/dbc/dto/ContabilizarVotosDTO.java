package com.api.votos.dbc.dto;

import com.api.votos.dbc.basicas.Pauta;
import lombok.Data;

@Data
public class ContabilizarVotosDTO {

    private Pauta pauta;
    private Integer qtdVotosSim;
    private Integer qtdVotosNao;
}
