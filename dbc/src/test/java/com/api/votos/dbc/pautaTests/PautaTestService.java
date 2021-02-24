package com.api.votos.dbc.pautaTests;

import com.api.votos.dbc.basicas.Pauta;
import com.api.votos.dbc.basicas.Sessao;
import com.api.votos.dbc.repositorio.PautaRepositorio;
import com.api.votos.dbc.servico.impl.PautaServicoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PautaTestService {

    @Mock
    private PautaRepositorio pautaRepositorio;

    @Mock
    private RestTemplate template;

    @Mock
    private Pauta pauta;

    @Mock
    private Sessao sessao;

    @InjectMocks
    private PautaServicoImpl pautaServico;

    @Test
    public  void testSalvarPauta() {
        Pauta newPauta = newPauta();
        newPauta.setId(null);
        newPauta.getSessao().setId(null);
        when(pautaServico.salvar(newPauta)).thenReturn(newPauta());
        Pauta pauta = pautaServico.salvar(newPauta);
        assertNotNull(pauta);
    }

    @Test
    public  void testListarPautas() {
        when(pautaServico.listar()).thenReturn(pautas());
        List<Pauta> pautas = pautaServico.listar();
        assertNotEquals(Collections.EMPTY_LIST, pautas);
    }

    @Test
    public  void testPesquisarPautaPorId() {
        Pauta newPauta = newPauta();
        when(pautaRepositorio.findById(Long.MAX_VALUE)).thenReturn(Optional.of(newPauta));
        Pauta pauta = pautaServico.buscarPorId(Long.MAX_VALUE);
        assertNotNull(pauta);
    }

    private Pauta newPauta() {
        return Pauta.builder()
                .id(Long.MAX_VALUE)
                .descricao("pauta")
                .sessao(Sessao.builder()
                        .id(Long.MAX_VALUE)
                        .data_inicio(LocalDateTime.now())
                        .data_fim(LocalDateTime.now())
                        .isSessaoExpirada(Boolean.FALSE)
                        .build())
                .build();
    }

    private List<Pauta> pautas() {
        ArrayList<Pauta> pautas = new ArrayList<>();
        pautas.add(newPauta());
        pautas.add(newPauta());
        return pautas;
    }
}
