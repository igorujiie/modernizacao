package com.luizalabs.modernizacao.util;

import com.luizalabs.modernizacao.model.Order;
import com.luizalabs.modernizacao.model.Produto;
import com.luizalabs.modernizacao.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConversorFormatoTxt  {

    public List<Usuario> convertData(List<String> rawData) {
        List<Usuario> users = new ArrayList<>();

        for (String line : rawData) {
            int userId = Integer.parseInt(line.substring(0, 10).trim());
            String name = line.substring(10, 44).trim();
            String orderId = line.substring(44, 54).trim();
            int productId = Integer.parseInt(line.substring(54, 64).trim());
            String value = line.substring(64, 74).trim();
            String date = formatarStringData(line.substring(74, 82).trim());

            Produto product = new Produto(productId, value);
            Order order = new Order(orderId, value, date, List.of(product));
            Usuario user = new Usuario(userId, name, List.of(order));

            users.add(user);
        }

        return users;
    }

    private String formatarStringData(String dateStr) {
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(4, 6);
        String day = dateStr.substring(6, 8);
        return year + "-" + month + "-" + day;
    }
}

