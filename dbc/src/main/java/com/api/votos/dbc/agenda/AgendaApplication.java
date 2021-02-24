package com.api.votos.dbc.agenda;

import com.api.votos.dbc.basicas.Pauta;
import com.api.votos.dbc.enums.TipoVoto;
import com.api.votos.dbc.rabbitMq.VotoQueueSender;
import com.api.votos.dbc.servico.PautaServico;
import com.api.votos.dbc.servico.VotoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class AgendaApplication {

    private final long MINUT = 1000 * 60;

    @Autowired
    private PautaServico pautaServico;

    @Autowired
    private VotoServico votoServico;

    @Autowired
    private VotoQueueSender sender;

    @Scheduled(fixedDelay = MINUT)
    public void enviarResultadosParaFila() {
        List<Pauta> pautasExpiradas = pautaServico.consultarPautasExpiradas();
        pautasExpiradas.forEach(pauta -> {
            Integer qtdVotosSim = votoServico.contabilizarVotos(TipoVoto.SIM, pauta.getId());
            Integer qtdVotosNao = votoServico.contabilizarVotos(TipoVoto.NA0, pauta.getId());
            String mensagem = "A pauta: " + pauta.getDescricao() + " teve a quantidade de votos positivo: " + qtdVotosSim +
                    " e a quantidade de votos negativo foi: " + qtdVotosNao;
            sender.enviar(mensagem);
            System.out.println("votação encerrada, mensagem enviada: " + mensagem);
            pauta.setIsPautaEnviada(true);
            pautaServico.salvar(pauta);
        });
    }
}
