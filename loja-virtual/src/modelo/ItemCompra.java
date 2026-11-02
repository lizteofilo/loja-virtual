package modelo;

import java.math.BigDecimal;

public class ItemCompra {

    private String nomeProduto;
    private BigDecimal precoUnitario;
    private int quantidade;

    public ItemCompra(String nomeProduto, BigDecimal precoUnitario, int quantidade) {
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public String toString() {
        return nomeProduto
            + " | Quantidade: " + quantidade
            + " | Preço unitário: R$ " + precoUnitario
            + " | Subtotal: R$ " + calcularSubtotal();
    }
}
