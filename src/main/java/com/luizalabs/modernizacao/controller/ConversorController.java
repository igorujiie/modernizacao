package com.luizalabs.modernizacao.controller;

import com.luizalabs.modernizacao.model.Ordem;
import com.luizalabs.modernizacao.model.Produto;
import com.luizalabs.modernizacao.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.*;

import static com.luizalabs.modernizacao.util.Conversor.parseLine;
import static com.luizalabs.modernizacao.util.FormatarData.formatDate;

@RestController
@RequestMapping("/api")
public class ConversorController {

     @PostMapping("/upload")
    public ResponseEntity<List<Usuario>> uploadFile(@RequestParam("file") MultipartFile file) {
        List<Usuario> userList = new ArrayList<>();
        Map<Integer, Usuario> userMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line, userMap);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

         userList.addAll(userMap.values());
         userList.forEach(user -> {
             user.getOrders().sort(Comparator.comparingInt(Ordem::getOrderId));
             user.getOrders().forEach(ordem -> {
                 ordem.getProducts().sort(Comparator.comparingInt(Produto::getProductId));
                 String formattedDate = formatDate(ordem.getDate());
                 ordem.setDate(formattedDate);
             });
         });
         return ResponseEntity.ok(userList);
     }



}
