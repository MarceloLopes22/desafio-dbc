package com.api.votos.dbc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoVoto implements Serializable {

    NA0(0, "Nao"),
    SIM(1, "Sim");

    private int key;
    private String value;

    TipoVoto(int key, final String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TipoVoto getTipoVotoBy(int chave) {
        for (TipoVoto TipoVoto : TipoVoto.values()) {
            if (TipoVoto.getKey() == chave) {
                return TipoVoto;
            }
        }
        return null;
    }

    public TipoVoto getTipoVotoBy(String valor) {
        for (TipoVoto TipoVoto : TipoVoto.values()) {
            if (TipoVoto.getValue().equalsIgnoreCase(valor)) {
                return TipoVoto;
            }
        }
        return null;
    }
}
