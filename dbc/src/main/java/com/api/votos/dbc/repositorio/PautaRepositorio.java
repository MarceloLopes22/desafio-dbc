package com.api.votos.dbc.repositorio;

import com.api.votos.dbc.basicas.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaRepositorio extends JpaRepository<Pauta, Long> {

    @Query(value = "select p from Pauta p\n" +
            "join Sessao s on p.sessao.id = s.id\n" +
            "where s.isSessaoExpirada = true\n" +
            " and p.isPautaEnviada = false ")
    List<Pauta> consultarPautasExpiradas();
}
