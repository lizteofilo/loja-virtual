package main;

import Interface.Menu;
import modelo.Carrinho;
import repositorio.CompraRepositorio;
import repositorio.ProdutoRepositorio;
import servicos.CompraService;
import servicos.ProdutoService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            ProdutoService produtoService = new ProdutoService(new ProdutoRepositorio());
            CompraService compraService = new CompraService(produtoService, new CompraRepositorio());

            new Menu(produtoService, compraService, new Carrinho()).iniciar();

        } catch (IOException e) {
            System.out.println("Erro ao acessar os arquivos: " + e.getMessage());
        }
    }
}
