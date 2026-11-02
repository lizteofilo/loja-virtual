package Teste;
import modelo.Carrinho;
import modelo.ItemCarrinho;
import modelo.Produto;

import java.math.BigDecimal;

public class TesteCarrinho {

    public static void main(String[] args) {

        Produto teclado = new Produto(
            1, "Teclado", new BigDecimal("89.90"), 10
        );

        Produto mouse = new Produto(
            2, "Mouse", new BigDecimal("49.90"), 20
        );

        Carrinho carrinho = new Carrinho();

        carrinho.adicionarProduto(teclado, 2);
        carrinho.adicionarProduto(mouse, 1);

        // Adicionando mais um teclado
        carrinho.adicionarProduto(teclado, 1);

        System.out.println("ITENS DO CARRINHO:");

        for (ItemCarrinho item : carrinho.listarItens()) {
            System.out.println(item);
        }

        System.out.println(
            "\nTotal: R$ " + carrinho.calcularTotal()
        );

        // Alterando a quantidade do mouse
        carrinho.alterarQuantidade(2, 3);

        System.out.println("\nCARRINHO ATUALIZADO:");

        for (ItemCarrinho item : carrinho.listarItens()) {
            System.out.println(item);
        }

        System.out.println(
            "\nTotal: R$ " + carrinho.calcularTotal()
        );
    }
}

