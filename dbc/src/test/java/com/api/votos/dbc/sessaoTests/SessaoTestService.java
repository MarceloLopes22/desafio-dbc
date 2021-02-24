package com.api.votos.dbc.sessaoTests;

import com.api.votos.dbc.basicas.Sessao;
import com.api.votos.dbc.repositorio.SessaoRepositorio;
import com.api.votos.dbc.servico.impl.SessaoServicoImpl;
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
public class SessaoTestService {

    @Mock
    private SessaoRepositorio sessaoRepositorio;

    @Mock
    private RestTemplate template;

    @Mock
    private Sessao sessao;

    @InjectMocks
    private SessaoServicoImpl sessaoServico;

    @Test
    public  void testSalvarSessao() {
        Sessao newSessao = newSessao();
        newSessao.setId(null);
        when(sessaoServico.salvar(newSessao)).thenReturn(newSessao());
        Sessao sessao = sessaoServico.salvar(newSessao);
        assertNotNull(sessao);
    }

    @Test
    public  void testListarSessoes() {
        when(sessaoServico.listar()).thenReturn(sessaos());
        List<Sessao> sessaos = sessaoServico.listar();
        assertNotEquals(Collections.EMPTY_LIST, sessaos);
    }

    @Test
    public  void testPesquisarSessaoPorId() {
        Sessao newSessao = newSessao();
        when(sessaoRepositorio.findById(Long.MAX_VALUE)).thenReturn(Optional.of(newSessao));
        Sessao sessao = sessaoServico.buscarPorId(Long.MAX_VALUE);
        assertNotNull(sessao);
    }

    private Sessao newSessao() {
        return Sessao.builder()
                        .id(Long.MAX_VALUE)
                        .data_inicio(LocalDateTime.now())
                        .data_fim(LocalDateTime.now())
                        .isSessaoExpirada(Boolean.FALSE)
                        .build();
    }

    private List<Sessao> sessaos() {
        ArrayList<Sessao> sessaos = new ArrayList<>();
        sessaos.add(newSessao());
        sessaos.add(newSessao());
        return sessaos;
    }
}
