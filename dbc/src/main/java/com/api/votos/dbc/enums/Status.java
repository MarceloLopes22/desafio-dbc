package com.api.votos.dbc.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {

    ABLE_TO_VOTE("ABLE_TO_VOTE"),
    UNABLE_TO_VOTE("UNABLE_TO_VOTE");

    @Getter
    @Setter
    private String value;

    @JsonCreator
    public static Status fromJson(@JsonProperty("status") String status) {
        Status retorno = valueOf(status);
        return retorno;
    }
}
