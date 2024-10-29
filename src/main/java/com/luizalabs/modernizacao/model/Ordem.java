package com.luizalabs.modernizacao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ordem {

    private int orderId;
    private double total;
    private String date;
    private List<Produto> products;


}
