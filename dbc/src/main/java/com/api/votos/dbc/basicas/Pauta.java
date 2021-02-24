package com.api.votos.dbc.basicas;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pauta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pauta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String descricao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @Column(name = "is_pauta_enviada", columnDefinition = "boolean default false")
    private Boolean isPautaEnviada;
}
