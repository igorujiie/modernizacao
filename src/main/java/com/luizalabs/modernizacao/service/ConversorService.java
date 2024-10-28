package com.luizalabs.modernizacao.service;

import com.luizalabs.modernizacao.model.Usuario;
import com.luizalabs.modernizacao.util.ConversorFormatoTxt;
import com.luizalabs.modernizacao.util.ConverterDadosFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversorService {

//    private final ConverterDadosFactory converterDadosFactory;
    private  ConversorFormatoTxt conversorFormatoTxt;

    @Autowired
    public ConversorService(ConversorFormatoTxt conversorFormatoTxt) {
        this.conversorFormatoTxt = conversorFormatoTxt;
    }

    public List<Usuario> process(List<String> data, String formato) {
//        ConversorDeFormatos converter = converterDadosFactory.getConverter(formato);
//        return converter.convertData(data);
        return conversorFormatoTxt.convertData(data);
    }


}
