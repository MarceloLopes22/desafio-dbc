package com.api.votos.dbc.repositorio;

import com.api.votos.dbc.basicas.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepositorio extends JpaRepository<Sessao, Long> {
}
