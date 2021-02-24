package com.api.votos.dbc.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {

    private Object data;

    private String mensagem;

    private HttpStatus httpStatus;

    private List<String> erros = new ArrayList<>();
}
