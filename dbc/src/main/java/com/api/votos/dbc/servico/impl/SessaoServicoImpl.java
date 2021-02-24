package com.api.votos.dbc.servico.impl;

import com.api.votos.dbc.basicas.Sessao;
import com.api.votos.dbc.repositorio.SessaoRepositorio;
import com.api.votos.dbc.servico.SessaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessaoServicoImpl implements SessaoServico {

    @Autowired
    private SessaoRepositorio repositorio;

    @Override
    public Sessao salvar(Sessao sessao) {
        return repositorio.save(sessao);
    }

    @Override
    public List<Sessao> listar() {
        return repositorio.findAll();
    }

    @Override
    public Sessao buscarPorId(Long id) {
        Sessao sessao = null;
        Optional<Sessao> optionalSessao = repositorio.findById(id);

        if (optionalSessao.isPresent()) {
            sessao = optionalSessao.get();
        }

        return sessao;
    }
}
