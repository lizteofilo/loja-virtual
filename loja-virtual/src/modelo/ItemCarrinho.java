package modelo;

import excecao.EstoqueInsuficienteException;

import java.math.BigDecimal;

public class ItemCarrinho {

    private Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }

        this.produto = produto;
        alterarQuantidade(quantidade);
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal calcularSubtotal() {
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }

    public void alterarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        if (quantidade > produto.getEstoque()) {
            throw new EstoqueInsuficienteException(produto.getNome(), produto.getEstoque());
        }

        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return produto.getId() + " | " + produto.getNome()
            + " | Quantidade: " + quantidade
            + " | Preço unitário: R$ " + produto.getPreco()
            + " | Subtotal: R$ " + calcularSubtotal();
    }
}
