package com.api.votos.dbc.basicas;

import com.api.votos.dbc.enums.TipoVoto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "voto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_voto")
    private TipoVoto tipoVoto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pauta_id", insertable = true, updatable = true)
    private Pauta pauta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "associado_id")
    private Associado associado;

}
