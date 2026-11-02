package Teste;
import modelo.Carrinho;
import modelo.Compra;
import modelo.Produto;
import pagamento.Debito;
import pagamento.Pix;
import repositorio.CompraRepositorio;
import repositorio.ProdutoRepositorio;
import servicos.CompraService;
import servicos.ProdutoService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// Testa a finalização da compra numa pasta temporária (não mexe em dados/).
public class TesteCompraService {

    public static void main(String[] args) throws IOException {

        // ---------- 1. Compra normal ----------
        Path pasta = criarPasta();
        ProdutoService produtos = new ProdutoService(new ProdutoRepositorio(pasta.resolve("produtos.txt")));
        CompraService compras = new CompraService(produtos, new CompraRepositorio(pasta.resolve("compras.txt")));

        Carrinho carrinho = new Carrinho();
        carrinho.adicionarProduto(produtos.buscarPorId(1), 2); // 2 x 89.90
        carrinho.adicionarProduto(produtos.buscarPorId(2), 1); // 1 x 49.90

        Compra compra = compras.finalizarCompra("Ana", carrinho, new Debito());

        System.out.println("[Compra normal]");
        verificar("subtotal = 229.70", compra.getSubtotal().compareTo(new BigDecimal("229.70")) == 0);
        verificar("desconto débito = 22.97", compra.getDesconto().compareTo(new BigDecimal("22.97")) == 0);
        verificar("valor final = 206.73", compra.getValorFinal().compareTo(new BigDecimal("206.73")) == 0);
        verificar("estoque do teclado 10 -> 8", produtos.buscarPorId(1).getEstoque() == 8);
        verificar("carrinho foi limpo", carrinho.estaVazio());
        verificar("histórico da Ana tem 1 compra", compras.historico("Ana").size() == 1);

        // ---------- 2. Mudar o preço depois não muda a compra ----------
        produtos.editar(1, "Teclado", new BigDecimal("500.00"), 8);
        CompraService recarregado = new CompraService(produtos, new CompraRepositorio(pasta.resolve("compras.txt")));

        System.out.println("\n[Preço alterado depois da compra]");
        verificar("compra continua com 89.90",
            recarregado.historico("Ana").get(0).getItens().get(0).getPrecoUnitario()
                .compareTo(new BigDecimal("89.90")) == 0);

        // ---------- 3. Estoque reduzido pelo admin depois de ir para o carrinho ----------
        Carrinho carrinho2 = new Carrinho();
        carrinho2.adicionarProduto(produtos.buscarPorId(2), 10);
        produtos.editar(2, "Mouse", new BigDecimal("49.90"), 3);

        System.out.println("\n[Estoque verificado de novo]");
        try {
            compras.finalizarCompra("Bia", carrinho2, new Pix());
            verificar("deveria dar erro", false);
        } catch (IllegalArgumentException e) {
            verificar("erro: " + e.getMessage(), true);
        }
        verificar("estoque continua 3", produtos.buscarPorId(2).getEstoque() == 3);

        // ---------- 4. Falha ao gravar a compra: estoque é devolvido ----------
        Path pasta2 = criarPasta();
        ProdutoService produtos2 = new ProdutoService(new ProdutoRepositorio(pasta2.resolve("produtos.txt")));

        // Repositório que sempre falha ao salvar (simula disco cheio)
        CompraRepositorio quebrado = new CompraRepositorio(pasta2.resolve("compras.txt")) {
            @Override
            public void salvar(List<Compra> lista) throws IOException {
                throw new IOException("disco cheio");
            }
        };
        CompraService compras2 = new CompraService(produtos2, quebrado);

        Carrinho carrinho3 = new Carrinho();
        carrinho3.adicionarProduto(produtos2.buscarPorId(1), 4);

        System.out.println("\n[Falha ao gravar a compra]");
        try {
            compras2.finalizarCompra("Caio", carrinho3, new Pix());
            verificar("deveria dar erro", false);
        } catch (IOException e) {
            verificar("erro: " + e.getMessage(), true);
        }
        verificar("estoque na memória continua 10", produtos2.buscarPorId(1).getEstoque() == 10);

        ProdutoService lidoDoArquivo = new ProdutoService(new ProdutoRepositorio(pasta2.resolve("produtos.txt")));
        verificar("estoque no ARQUIVO continua 10", lidoDoArquivo.buscarPorId(1).getEstoque() == 10);
    }

    // Cria uma pasta com Teclado (ID 1, 89.90, 10) e Mouse (ID 2, 49.90, 20)
    private static Path criarPasta() throws IOException {
        Path pasta = Files.createTempDirectory("teste");
        new ProdutoRepositorio(pasta.resolve("produtos.txt")).salvar(List.of(
            new Produto(1, "Teclado", new BigDecimal("89.90"), 10),
            new Produto(2, "Mouse", new BigDecimal("49.90"), 20)
        ));
        return pasta;
    }

    private static void verificar(String descricao, boolean ok) {
        System.out.println((ok ? "  OK      " : "  FALHOU  ") + descricao);
    }
}
