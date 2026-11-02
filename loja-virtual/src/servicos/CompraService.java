package servicos;

import excecao.EstoqueInsuficienteException;
import excecao.ProdutoNaoEncontradoException;
import modelo.Carrinho;
import modelo.Compra;
import modelo.ItemCarrinho;
import modelo.ItemCompra;
import modelo.Produto;
import pagamento.Pagamento;
import repositorio.CompraRepositorio;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompraService {

    private ProdutoService produtoService;
    private CompraRepositorio repositorio;
    private List<Compra> compras;

    public CompraService(ProdutoService produtoService, CompraRepositorio repositorio)
            throws IOException {
        this.produtoService = produtoService;
        this.repositorio = repositorio;
        this.compras = repositorio.carregar();
    }

    public Compra finalizarCompra(String comprador, Carrinho carrinho, Pagamento pagamento)
            throws IOException {
        if (carrinho.estaVazio()) {
            throw new IllegalArgumentException("O carrinho está vazio.");
        }

        if (comprador.contains(";") || comprador.contains(",") || comprador.contains("|")) {
            throw new IllegalArgumentException("O nome do comprador não pode conter ; , |");
        }

        List<ItemCarrinho> itens = carrinho.listarItens();
        for (ItemCarrinho item : itens) {
            Produto produto = item.getProduto();

            if (!produtoService.existe(produto.getId())) {
                throw new ProdutoNaoEncontradoException(
                    produto.getNome() + " não está mais à venda. Remova-o do carrinho."
                );
            }

            if (item.getQuantidade() > produto.getEstoque()) {
                throw new EstoqueInsuficienteException(produto.getNome(), produto.getEstoque());
            }
        }

        BigDecimal subtotal = carrinho.calcularTotal();
        BigDecimal valorFinal = pagamento.calcularValorFinal(subtotal);
        BigDecimal desconto = pagamento.calcularDesconto(subtotal);

        // 5. Processar o pagamento
        if (!pagamento.pagar(valorFinal)) {
            throw new IllegalArgumentException("Pagamento não aprovado.");
        }

        List<ItemCompra> itensCompra = new ArrayList<>();

        for (ItemCarrinho item : itens) {
            itensCompra.add(new ItemCompra(
                item.getProduto().getNome(),
                item.getProduto().getPreco(),
                item.getQuantidade()
            ));
        }

        Compra compra = new Compra(proximoId(), LocalDateTime.now().withSecond(0).withNano(0),
            comprador, itensCompra, pagamento.getNome(), desconto);

        produtoService.baixarEstoque(itens);

        List<Compra> novaLista = new ArrayList<>(compras);
        novaLista.add(compra);

        try {
            repositorio.salvar(novaLista);
        } catch (IOException e) {
            produtoService.devolverEstoque(itens);
            throw new IOException("Não foi possível registrar a compra. "
                + "O estoque foi devolvido e a compra foi cancelada.");
        }

        compras = novaLista;
        carrinho.limpar();
        return compra;
    }

    public List<Compra> listarCompras() {
        return new ArrayList<>(compras);
    }

    public List<Compra> historico(String comprador) {
        List<Compra> resultado = new ArrayList<>();

        for (Compra c : compras) {
            if (c.getComprador().equalsIgnoreCase(comprador)) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    public BigDecimal totalGasto(String comprador) {
        BigDecimal total = new BigDecimal("0.00");

        for (Compra c : historico(comprador)) {
            total = total.add(c.getValorFinal());
        }

        return total;
    }

    private int proximoId() {
        int maior = 0;

        for (Compra c : compras) {
            maior = Math.max(maior, c.getId());
        }

        return maior + 1;
    }
}
