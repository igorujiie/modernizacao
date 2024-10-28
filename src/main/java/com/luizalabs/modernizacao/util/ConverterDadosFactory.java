package com.luizalabs.modernizacao.util;

import org.springframework.stereotype.Component;

@Component
public abstract  class ConverterDadosFactory {


    public ConversorFormatoTxt getConverter(String format) {
        switch (format.toLowerCase()) {
            case "txt":
                return new ConversorFormatoTxt();
            default:
                throw new IllegalArgumentException("Formato não suportado: " + format);
        }
    }
}


