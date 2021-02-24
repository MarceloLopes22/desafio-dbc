package com.api.votos.dbc.servico.impl;

import com.api.votos.dbc.basicas.Associado;
import com.api.votos.dbc.basicas.Pauta;
import com.api.votos.dbc.basicas.Sessao;
import com.api.votos.dbc.basicas.Voto;
import com.api.votos.dbc.dto.ResponseDTO;
import com.api.votos.dbc.dto.VotoRequestDTO;
import com.api.votos.dbc.enums.Status;
import com.api.votos.dbc.enums.TipoVoto;
import com.api.votos.dbc.repositorio.VotoRepositorio;
import com.api.votos.dbc.servico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class VotoServicoImpl implements VotoServico {

    @Autowired
    private VotoRepositorio votoRepositorio;

    @Autowired
    private AssociadoServico associadoServico;

    @Autowired
    private PautaServico pautaServico;

    @Autowired
    private ValidadorService validadorService;

    @Autowired
    private SessaoServico sessaoServico;

    /**
     * Metodo résponsavel em persistir os votos
     * */
    @Override
    public ResponseDTO votar(VotoRequestDTO votoRequestDTO) {
        ResponseDTO retorno = new ResponseDTO();
        ResponseDTO responseDTO = validarDatosVoto(retorno, votoRequestDTO);
        if (responseDTO.getErros() != null && !responseDTO.getErros().isEmpty()) {
            responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            return responseDTO;
        }
        Voto voto = votoRequestDTO.getVoto();
        Associado associado = voto.getAssociado();
        Pauta pauta = voto.getPauta();

        //Consulta a pauta
        pauta = pautaServico.buscarPorId(pauta.getId());
        if (Objects.nonNull(pauta)) {
            voto.setPauta(pauta);
        } else {
            pauta = pautaServico.salvar(pauta);
            voto.setPauta(pauta);
        }

        //Consulta o associado
        associado = associadoServico.pesquisarAssociadoPorCpf(associado.getCpf());
        if (Objects.isNull(associado)) {
            associado = associadoServico.salvar(votoRequestDTO
                    .getVoto().getAssociado());
            voto.setAssociado(associado);
        } else {
            voto.setAssociado(associado);
        }

        //Salva o voto
        voto = votoRepositorio.save(voto);

        retorno.setData(voto);
        retorno.setHttpStatus(HttpStatus.CREATED);
        retorno.setMensagem("Voto salvo com sucesso.");

        return retorno;
    }

    /**
     * Metodo résponsavel em validar os dados dos votos
     * */
    public ResponseDTO validarDatosVoto(ResponseDTO retorno, VotoRequestDTO votoRequestDTO) {
        //Verifica se associado já votou
        Voto voto = votoRequestDTO.getVoto();
        Associado associado = voto.getAssociado();
        Pauta pauta = voto.getPauta();

        Voto votoPorCPF = votoRepositorio.consultarVotoPorCPF(associado.getCpf(), pauta.getId());
        if (votoPorCPF != null) {
            retorno.getErros().add("O associado com o CPF: " + associado.getCpf() + " já realizou seu voto na pauta: " + pauta.getDescricao());
        }
        //Verifica se o cpf está habil para votar.
        Status status = validadorService.isCPFValido(associado.getCpf());
        if (Status.UNABLE_TO_VOTE == status) {
            retorno.getErros().add("O associado de CPF: " +  associado.getCpf() + " não está habil para votar");
        }

        //Valida se a sessão está expirada.
        LocalDateTime dataHoraAtual = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        Pauta pautaConsultada = pautaServico.buscarPorId(pauta.getId());

        if (Objects.nonNull(pautaConsultada) && Objects.nonNull(pautaConsultada.getSessao())) {

            Sessao sessao = pautaConsultada.getSessao();

            boolean isSessaoExpirada = dataHoraAtual.isAfter(sessao.getData_inicio())
                    &&  dataHoraAtual.isAfter(sessao.getData_fim());

            if (isSessaoExpirada) {
                retorno.getErros().add("Não é possivel realizar mais votos nessa pauta pois encontra-se expirada.");
                sessao.setIsSessaoExpirada(true);
                sessaoServico.salvar(sessao);
            }
        }
        return  retorno;
    }

    /**
     * Metodo résponsavel em listar os votos
     * */
    @Override
    public List<Voto> listar() {
        return votoRepositorio.findAll();
    }

    /**
     * Metodo résponsavel em consultar um voto
     * */
    @Override
    public Voto buscarPorId(Long id) {
        Voto voto = null;
        Optional<Voto> optionalVoto = votoRepositorio.findById(id);

        if (optionalVoto.isPresent()) {
            voto = optionalVoto.get();
        }

        return voto;
    }

    @Override
    public Integer contabilizarVotos(TipoVoto tipoVoto, Long pautaId) {
        Integer qtdVotos = votoRepositorio.contabilizarVotos(tipoVoto, pautaId);
        return qtdVotos;
    }
}
