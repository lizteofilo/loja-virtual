package Teste;

import modelo.Produto;
import repositorio.ProdutoRepositorio;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TesteRepositorio {

    public static void main(String[] args) {
        try {
            ProdutoRepositorio repositorio = new ProdutoRepositorio();

            List<Produto> produtos = new ArrayList<>();

            produtos.add(new Produto(
                1, "Teclado", new BigDecimal("89.90"), 10
            ));

            produtos.add(new Produto(
                2, "Mouse", new BigDecimal("49.90"), 20
            ));

            //repositorio.salvar(produtos);

            //System.out.println("Produtos salvos!");

            List<Produto> carregados = repositorio.carregar();

            System.out.println("\nProdutos recuperados:");

            for (Produto produto : carregados) {
                System.out.println(produto);
            }

        } catch (IOException e) {
            System.out.println("Erro nos arquivos: " + e.getMessage());
        }
    }
}
