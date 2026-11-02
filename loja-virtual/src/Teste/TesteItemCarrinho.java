package Teste;

import modelo.Produto;
import modelo.ItemCarrinho;

import java.math.BigDecimal;

public class TesteItemCarrinho {

    public static void main(String[] args) {

        Produto produto = new Produto(
            1,
            "Teclado",
            new BigDecimal("89.90"),
            10
        );

        ItemCarrinho item = new ItemCarrinho(produto, 10);

        System.out.println(item);
        System.out.println(
            "Subtotal: R$ " + item.calcularSubtotal()
        );
    }
}

