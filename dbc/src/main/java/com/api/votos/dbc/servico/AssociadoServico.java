package com.api.votos.dbc.servico;

import com.api.votos.dbc.basicas.Associado;

import java.util.List;

public interface AssociadoServico {

    Associado salvar(Associado associado);

    List<Associado> listar();

    Associado buscarPorId(Long id);

    Associado pesquisarAssociadoPorCpf(String cpf);
}
