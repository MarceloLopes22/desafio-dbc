package com.api.votos.dbc.repositorio;

import com.api.votos.dbc.basicas.Voto;
import com.api.votos.dbc.enums.TipoVoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto, Long> {

    @Query(value = "select vo from Voto vo\n" +
            "join Associado ass on vo.associado.id = ass.id\n" +
            "where ass.cpf = :cpf and vo.pauta.id = :pautaId")
    Voto consultarVotoPorCPF(@Param("cpf") String cpf, @Param("pautaId") Long pautaId);

    @Query(value = "select count(v.tipoVoto) from Voto v\n" +
            "where v.tipoVoto = :tipoVoto and\n" +
            "v.pauta.id = :pautaId")
    Integer contabilizarVotos(@Param("tipoVoto") TipoVoto tipoVoto,
                              @Param("pautaId") Long pautaId);
}
