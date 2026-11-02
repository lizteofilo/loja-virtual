package Teste;
import modelo.Compra;
import modelo.ItemCompra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TesteCompra {

    public static void main(String[] args) {

        List<ItemCompra> itens = List.of(
            new ItemCompra("Teclado", new BigDecimal("89.90"), 2),
            new ItemCompra("Mouse", new BigDecimal("49.90"), 1)
        );

        // Débito: 10% de 229.70 = 22.97 de desconto
        Compra compra = new Compra(1, LocalDateTime.now(), "Ana", itens,
            "Cartão de débito", new BigDecimal("22.97"));

        System.out.println(compra);
        System.out.println("Subtotal: R$ " + compra.getSubtotal());
        System.out.println("Desconto: R$ " + compra.getDesconto());
        System.out.println("Valor final: R$ " + compra.getValorFinal());

        System.out.println("\nItens da compra:");

        for (var item : compra.getItens()) {
            System.out.println(item);
        }
    }
}
