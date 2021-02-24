package com.api.votos.dbc.votoTests;

import com.api.votos.dbc.basicas.Associado;
import com.api.votos.dbc.basicas.Pauta;
import com.api.votos.dbc.basicas.Sessao;
import com.api.votos.dbc.basicas.Voto;
import com.api.votos.dbc.dto.ResponseDTO;
import com.api.votos.dbc.dto.VotoRequestDTO;
import com.api.votos.dbc.enums.Status;
import com.api.votos.dbc.enums.TipoVoto;
import com.api.votos.dbc.repositorio.VotoRepositorio;
import com.api.votos.dbc.servico.ValidadorService;
import com.api.votos.dbc.servico.impl.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VotoTestService {

    static final String MENSAGEM_ERRO_VOTO_REPETIDO = "O associado com o CPF: 50564790044 já realizou seu voto na pauta: pauta";

    static final String MENSAGEM_ERRO_CPF_INVALIDO = "O associado de CPF: 50564790044 não está habil para votar";

    static final String MENSAGEM_ERRO_PAUTA_EXPIRADA = "Não é possivel realizar mais votos nessa pauta pois encontra-se expirada.";

    @Mock
    private VotoRepositorio votoRepositorio;

    @Mock
    private ValidadorServiceImpl validadorService;

    @Mock
    private PautaServicoImpl pautaServico;

    @Mock
    private SessaoServicoImpl sessaoServico;

    @Mock
    private AssociadoServicoImpl associadoServico;

    @Mock
    private RestTemplate template;

    @Mock
    private Voto voto;

    @Mock
    private Associado associado;

    @Mock
    private Pauta pauta;

    @InjectMocks
    private VotoServicoImpl votoServico;

    @Test
    public  void testSalvarVoto() {
        when(votoServico.votar(newVotoRequestDTO())).thenReturn(newResponseDTO());
        ResponseDTO responseDTO = votoServico.votar(newVotoRequestDTO());
        assertNotNull(responseDTO);
    }

    @Test
    public  void testSalvarVotoMensagemErroVotoRepetido() {
        ResponseDTO responseDTO = new ResponseDTO();
        ArrayList<String> erros = new ArrayList<>();
        erros.add(MENSAGEM_ERRO_VOTO_REPETIDO);
        responseDTO.setErros(erros);
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);

        when(votoRepositorio.consultarVotoPorCPF("50564790044", Long.MAX_VALUE)).thenReturn(newVotoRequestDTO().getVoto());
        ResponseDTO response = votoServico.votar(newVotoRequestDTO());
        assertNotNull(response.getErros());
    }

    @Test
    public  void testSalvarVotoMensagemErroCPFInvalido() {
        ResponseDTO responseDTO = new ResponseDTO();
        ArrayList<String> erros = new ArrayList<>();
        erros.add(MENSAGEM_ERRO_CPF_INVALIDO);
        responseDTO.setErros(erros);
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);

        when(validadorService.isCPFValido("50564790044")).thenReturn(Status.UNABLE_TO_VOTE);
        ResponseDTO response = votoServico.votar(newVotoRequestDTO());
        assertNotNull(response.getErros());
    }

    @Test
    public  void testSalvarVotoMensagemErroPautaExpirada() {
        ResponseDTO responseDTO = new ResponseDTO();
        ArrayList<String> erros = new ArrayList<>();
        erros.add(MENSAGEM_ERRO_PAUTA_EXPIRADA);
        responseDTO.setErros(erros);
        responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);

        VotoRequestDTO dto = newVotoRequestDTO();
        dto.getVoto().getPauta().getSessao().setData_inicio(LocalDateTime.now().minusYears(5));
        dto.getVoto().getPauta().getSessao().setData_fim(LocalDateTime.now().minusYears(5));

        when(pautaServico.buscarPorId(Long.MAX_VALUE)).thenReturn(dto.getVoto().getPauta());
        ResponseDTO response = votoServico.votar(newVotoRequestDTO());
        assertEquals(response.getErros().get(0), MENSAGEM_ERRO_PAUTA_EXPIRADA);
    }

    @Test
    public  void testListarSessoes() {
        when(votoServico.listar()).thenReturn(votos());
        List<Voto> votos = votoServico.listar();
        assertNotEquals(Collections.EMPTY_LIST, votos);
    }

    @Test
    public  void testPesquisarVotoPorId() {
        Voto newVoto = newVotoRequestDTO().getVoto();
        when(votoRepositorio.findById(Long.MAX_VALUE)).thenReturn(Optional.of(newVoto));
        Voto voto = votoServico.buscarPorId(Long.MAX_VALUE);
        assertNotNull(voto);
    }

    private VotoRequestDTO newVotoRequestDTO() {
        return VotoRequestDTO.builder()
                .voto(Voto.builder()
                        .id(Long.MAX_VALUE)
                        .associado(Associado.builder()
                                .id(Long.MAX_VALUE)
                                .cpf("50564790044")
                                .build())
                        .tipoVoto(TipoVoto.SIM)
                        .pauta(Pauta.builder()
                                .id(Long.MAX_VALUE)
                                .descricao("pauta")
                                .sessao(Sessao.builder()
                                        .id(Long.MAX_VALUE)
                                        .data_inicio(LocalDateTime.now())
                                        .data_fim(LocalDateTime.now())
                                        .isSessaoExpirada(Boolean.FALSE)
                                        .build())
                                .build())
                        .build())
                .build();
    }

    private List<Voto> votos() {
        ArrayList<Voto> votos = new ArrayList<>();
        VotoRequestDTO votoRequestDTO = newVotoRequestDTO();
        votos.add(votoRequestDTO.getVoto());
        votos.add(votoRequestDTO.getVoto());
        return votos;
    }

    private ResponseDTO newResponseDTO() {
        return ResponseDTO.builder()
                .data(Voto.builder()
                        .id(Long.MAX_VALUE)
                        .associado(Associado.builder()
                                .id(Long.MAX_VALUE)
                                .cpf("50564790044")
                                .build())
                        .tipoVoto(TipoVoto.SIM)
                        .pauta(Pauta.builder()
                                .id(Long.MAX_VALUE)
                                .descricao("pauta")
                                .sessao(Sessao.builder()
                                        .id(Long.MAX_VALUE)
                                        .data_inicio(LocalDateTime.now())
                                        .data_fim(LocalDateTime.now())
                                        .isSessaoExpirada(Boolean.FALSE)
                                        .build())
                                .build())
                        .build())
                .httpStatus(HttpStatus.OK)
                .mensagem("Voto salvo com sucesso.")
                .build();
    }
}
