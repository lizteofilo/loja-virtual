package Teste;
import modelo.Compra;
import modelo.ItemCompra;
import repositorio.CompraRepositorio;

import java.math.BigDecimal;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class TesteCompraRepositorio {

    public static void main(String[] args) {

        try {
            // Pasta temporária: não apaga o histórico real em dados/compras.txt
            Path arquivo = Files.createTempDirectory("teste").resolve("compras.txt");

            CompraRepositorio repositorio =
                new CompraRepositorio(arquivo);

            List<ItemCompra> itens = List.of(
                new ItemCompra("Teclado", new BigDecimal("89.90"), 2),
                new ItemCompra("Mouse", new BigDecimal("49.90"), 1)
            );

            // Pix: 229.70 x 0.95 = 218.22, então o desconto é 11.48
            Compra compra = new Compra(1, LocalDateTime.now().withSecond(0).withNano(0),
                "Maria", itens, "Pix", new BigDecimal("11.48"));

            repositorio.salvar(List.of(compra));

            System.out.println("Compra salva! Conteúdo do arquivo:");
            System.out.println(Files.readString(arquivo));

            System.out.println("Compras recuperadas:");

            for (Compra c : repositorio.carregar()) {

                System.out.println(c);

                for (ItemCompra item : c.getItens()) {
                    System.out.println(item);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
