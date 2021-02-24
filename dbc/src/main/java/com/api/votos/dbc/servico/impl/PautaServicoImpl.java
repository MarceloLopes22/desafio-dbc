package com.api.votos.dbc.servico.impl;

import com.api.votos.dbc.basicas.Pauta;
import com.api.votos.dbc.basicas.Sessao;
import com.api.votos.dbc.repositorio.PautaRepositorio;
import com.api.votos.dbc.servico.PautaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PautaServicoImpl implements PautaServico {

    @Autowired
    private PautaRepositorio repositorio;

    /**
     * Salva uma pauta com uma sessão de 1 minuto padrão ou
     * Com um periodo definido
     * */
    @Override
    public Pauta salvar(Pauta pauta) {

        Sessao sessao = pauta.getSessao();
        if (Objects.isNull(sessao.getData_inicio()) && Objects.isNull(sessao.getData_fim())) {
            LocalDateTime dataHoraPadrao = LocalDateTime.now();
            sessao.setData_inicio(dataHoraPadrao);
            sessao.setData_fim(dataHoraPadrao.plusMinutes(1));
            sessao.setIsSessaoExpirada(false);
        }
        pauta = repositorio.save(pauta);
        return pauta;
    }

    /**
     * Lista todas as pautas
     * */
    @Override
    public List<Pauta> listar() {
        return repositorio.findAll();
    }

    /**
     * Consullta pauta por id
     * */
    @Override
    public Pauta buscarPorId(Long id) {
        Pauta pauta = null;
        Optional<Pauta> optionalPauta = repositorio.findById(id);

        if (optionalPauta.isPresent()) {
            pauta = optionalPauta.get();
        }
        return pauta;
    }

    @Override
    public List<Pauta> consultarPautasExpiradas() {
        List<Pauta> pautas = repositorio.consultarPautasExpiradas();
        return pautas;
    }
}
