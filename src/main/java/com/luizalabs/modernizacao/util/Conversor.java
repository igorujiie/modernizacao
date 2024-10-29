package com.luizalabs.modernizacao.util;

import com.luizalabs.modernizacao.model.Ordem;
import com.luizalabs.modernizacao.model.Produto;
import com.luizalabs.modernizacao.model.Usuario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;


public class Conversor {

    public static void parseLine(String line, Map<Integer, Usuario> userMap) {
        int userId = Integer.parseInt(line.substring(0, 10).trim());
        String name = line.substring(10, 55).trim();
        int orderId = Integer.parseInt(line.substring(55, 65).trim());
        int productId = Integer.parseInt(line.substring(65, 75).trim());
        double value = Double.parseDouble(line.substring(75, 87).trim());
        String date = line.substring(87, 95).trim();

        Usuario user = userMap.computeIfAbsent(userId, id -> {
            Usuario newUsuario  = new Usuario();
            newUsuario .setUserId(id);
            newUsuario .setName(name);
            newUsuario .setOrders(new ArrayList<>());
            return newUsuario ;
        });

        Ordem ordem = user.getOrders().stream()
                .filter(o -> o.getOrderId() == orderId)
                .findFirst()
                .orElseGet(() -> {
                    Ordem newOrdem = new Ordem();
                    newOrdem.setOrderId(orderId);
                    newOrdem.setTotal(0.00);
                    newOrdem.setDate(date);
                    newOrdem.setProducts(new ArrayList<>());
                    user.getOrders().add(newOrdem);
                    return newOrdem;
                });

        Produto produto = new Produto();
        produto.setProductId(productId);
        produto.setValue(String.valueOf(value));
        ordem.getProducts().add(produto);

        BigDecimal total = BigDecimal.valueOf(ordem.getTotal());
        BigDecimal productValue = BigDecimal.valueOf(value);
        total = total.add(productValue).setScale(2, RoundingMode.HALF_UP);
        ordem.setTotal(total.doubleValue());
    }
}