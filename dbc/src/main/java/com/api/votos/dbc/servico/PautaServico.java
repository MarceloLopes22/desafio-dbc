package com.api.votos.dbc.servico;

import com.api.votos.dbc.basicas.Pauta;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PautaServico {

    Pauta salvar(Pauta pauta);

    List<Pauta> listar();

    Pauta buscarPorId(Long id);

    List<Pauta> consultarPautasExpiradas();
}
