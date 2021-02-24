package com.api.votos.dbc.dto;

import com.api.votos.dbc.basicas.Voto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotoRequestDTO {
    private Voto voto;
}
