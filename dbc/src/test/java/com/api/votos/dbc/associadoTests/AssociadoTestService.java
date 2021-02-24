package com.api.votos.dbc.associadoTests;

import com.api.votos.dbc.basicas.Associado;
import com.api.votos.dbc.repositorio.AssociadoRepositorio;
import com.api.votos.dbc.servico.impl.AssociadoServicoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AssociadoTestService {

    @Mock
    private AssociadoRepositorio associadoRepositorio;

    @Mock
    private RestTemplate template;

    @Mock
    private Associado associado;

    @InjectMocks
    private AssociadoServicoImpl associadoServico;

    @Test
    public  void testSalvarAssociado() {
        when(associadoServico.salvar(associado)).thenReturn(newAssociado());
        Associado associado = associadoServico.salvar(this.associado);
        assertNotNull(associado);
    }

    @Test
    public  void testListarAssociados() {
        when(associadoServico.listar()).thenReturn(associados());
        List<Associado> associados = associadoServico.listar();
        assertNotEquals(Collections.EMPTY_LIST, associados);
    }

    @Test
    public  void testPesquisarAssociadoPorId() {
        Associado newAssociado = newAssociado();
        when(associadoRepositorio.findById(Long.MAX_VALUE)).thenReturn(Optional.of(newAssociado));
        Associado associado = associadoServico.buscarPorId(Long.MAX_VALUE);
        assertNotNull(associado);
    }

    @Test
    public  void testPesquisarAssociadoPorCPF() {
        when(associadoServico.pesquisarAssociadoPorCpf(anyString())).thenReturn(newAssociado());
        Associado associado = associadoServico.pesquisarAssociadoPorCpf(anyString());
        assertNotNull(associado);
    }

    private Associado newAssociado() {
        return Associado.builder()
                .id(Long.MAX_VALUE)
                .cpf("50564790044")
                .build();
    }

    private List<Associado> associados() {
        ArrayList<Associado> associados = new ArrayList<>();
        associados.add(newAssociado());
        associados.add(newAssociado());
        return associados;
    }
}
