package com.api.votos.dbc.servico;

import com.api.votos.dbc.basicas.Sessao;

import java.util.List;

public interface SessaoServico {

    Sessao salvar(Sessao sessao);

    List<Sessao> listar();

    Sessao buscarPorId(Long id);
}
