package Teste;

import modelo.Produto;
import java.math.BigDecimal;

public class TesteProduto {

    public static void main(String[] args) {
        Produto produto = new Produto(
            1,
            "Teclado",
            new BigDecimal("89.90"),
            10
        );

        System.out.println(produto);

        produto.retirarEstoque(2);

        System.out.println("Após retirar 2 unidades:");
        System.out.println(produto);
    }
}

