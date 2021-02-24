package com.api.votos.dbc.servico;

import com.api.votos.dbc.enums.Status;

public interface ValidadorService {

    Status isCPFValido(String cpf);
}
