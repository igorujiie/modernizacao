package com.luizalabs.modernizacao.controller;

import com.luizalabs.modernizacao.model.Usuario;
import com.luizalabs.modernizacao.service.ConversorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConversorController {

    private final ConversorService conversorService;

    public ConversorController(ConversorService conversorService) {
        this.conversorService = conversorService;
    }


    @PostMapping("/teste")
    public List<Usuario> uploadFile(@RequestParam("file") MultipartFile Arquivo,
                                                      @RequestParam(value = "formato", defaultValue = "txt")String formato) throws IOException {
        List<String> linhas = lerLinhasDoArquivo(Arquivo);
        return conversorService.process(linhas, formato);
    }

    private List<String> lerLinhasDoArquivo(MultipartFile arquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()))) {
            return reader.lines().toList();
        }
    }

}
