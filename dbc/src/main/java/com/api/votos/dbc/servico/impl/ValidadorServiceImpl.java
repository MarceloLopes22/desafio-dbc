package com.api.votos.dbc.servico.impl;

import com.api.votos.dbc.enums.Status;
import com.api.votos.dbc.servico.ValidadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ValidadorServiceImpl implements ValidadorService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Status isCPFValido(String cpf) {

        Status retorno = null;

        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("https://user-info.herokuapp.com/users/"+ cpf);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<?> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    Object.class);

            if (HttpStatus.OK == response.getStatusCode()) {
                ObjectMapper mapper = new ObjectMapper();
                retorno  = mapper.convertValue(response.getBody(), Status.class);
            }

        } catch (Exception e) {
            if (HttpStatus.NOT_FOUND == ((HttpClientErrorException.NotFound)e).getStatusCode()) {
                retorno = Status.UNABLE_TO_VOTE;
            }
        }

        return retorno;
    }
}
