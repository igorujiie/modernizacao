package com.luizalabs.modernizacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizalabs.modernizacao.model.Order;
import com.luizalabs.modernizacao.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo  não pode ser vazio");
        }

        try {
            List<Usuario> users = parseFile(file);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(users);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

    private List<Usuario> parseFile(MultipartFile file) throws IOException {
        List<Usuario> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(file.getInputStream(), StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\s+");

                String name = data[1];
                String orderId = data[2];
                double total = Double.parseDouble(data[3]);
                String date = data[4];

                LocalDate orderDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));

                Usuario user = new Usuario();
                user.setName(name);

                Order order = new Order();
                order.setOrderId(orderId);
                order.setTotal(String.valueOf(total));
                order.setDate(String.valueOf(orderDate));
                user.getOrders().add(order);

                users.add(user);
            }
        }
        return users;
    }
}
