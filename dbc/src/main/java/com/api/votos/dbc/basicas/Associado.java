package com.api.votos.dbc.basicas;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "associado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Associado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String cpf;
}
