package Teste;
import modelo.Produto;
import repositorio.ProdutoRepositorio;
import servicos.ProdutoService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class TesteProdutoService {

    public static void main(String[] args) {
        try {
            // Pasta temporária: não mexe em dados/ e pode rodar várias vezes.
            Path pasta = Files.createTempDirectory("teste");

            ProdutoRepositorio repositorio =
                new ProdutoRepositorio(pasta.resolve("produtos.txt"));

            ProdutoService servico =
                new ProdutoService(repositorio);

            Produto novoProduto = new Produto(
                3,
                "Monitor",
                new BigDecimal("799.90"),
                5
            );

            Produto produto2 = new Produto(
                4, 
                "Celular", 
                new BigDecimal("899.99"), 
                4);

            servico.cadastrar(novoProduto);
            servico.cadastrar(produto2);
            System.out.println("Produto cadastrado!\n" + novoProduto);
            System.out.println("Produto cadastrado!\n" + produto2);

            servico.editar(
                3,
                "Monitor Full HD",
                new BigDecimal("849.90"),
                8
            );

            System.out.println("\nProduto editado!\n" + servico.buscarPorId(3));

            servico.remover(3);
            System.out.println("\nProduto 3 removido!");

            System.out.println("\nLista final:");

            for (Produto produto : servico.listar()) {
                System.out.println(produto);
            }

            // ID duplicado deve dar erro
            servico.cadastrar(new Produto(4, "Outro", BigDecimal.ONE, 1));

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("\nErro esperado: " + e.getMessage());
        }
    }
}
