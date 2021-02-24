package com.api.votos.dbc.repositorio;

import com.api.votos.dbc.basicas.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepositorio extends JpaRepository<Associado, Long> {

    Associado findAssociadoByCpf(String cpf);
}
